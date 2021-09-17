package com.henninghall.date_picker.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.accessibilityservice.AccessibilityServiceInfo;

import com.henninghall.date_picker.DatePickerPackage;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.pickers.Picker;

import java.util.Locale;
import java.util.List;

import android.view.accessibility.AccessibilityNodeInfo;

public class Accessibility {
    private final static AccessibilityManager systemManager = (AccessibilityManager) DatePickerPackage.context
            .getApplicationContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

    private static Locale mLocale = Locale.getDefault();

    public static void setLocale(Locale nextLocale) {
        mLocale = nextLocale;
    }

    public static Locale getLocale() {
        return mLocale;
    }

    /**
     * When TalkBack is active, user can use one finger to explore the screen and
     * set focus to elements. Then user can proceed to use second finger to scroll
     * contents of focused element. When there's multiple pickers next to each other
     * horizontally, it's easy to accidentally scroll more than one picker at a
     * time. Following code aims to fix this issue.
     */
    public static boolean shouldAllowScroll(View view) {
        // If TalkBack isn't active, always proceed without suppressing touch events
        if (!systemManager.isTouchExplorationEnabled()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return view.isAccessibilityFocused();
        }
        return false;
    }

    /**
     * Enable capturing of accessibility events for a Picker
     */
    public static void startAccessibilityDelegate(Picker picker) {
        final Picker fPicker = picker;
        final View view = picker.getView();

        view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                int currentValue = fPicker.getValue();

                // Capture system automagic accessibility actions or custom actions created & sent manually via accessibility events
                switch (action) {
                    case AccessibilityNodeInfo.ACTION_SCROLL_FORWARD:
                        if (!fPicker.isSpinning()) {
                            // Increase value when pressing hardware volume up (or scrolling by other means)
                            fPicker.smoothScrollToValue(currentValue - 1);
                        }
                        break;
                    case AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD:
                        if (!fPicker.isSpinning()) {
                            // Decrease value when pressing hardware volume down (or scrolling by other means)
                            fPicker.smoothScrollToValue(currentValue + 1);
                        }
                        break;
                }

                return super.performAccessibilityAction(host, action, args);
            }
        });
    }

    public static boolean isAccessibilityEnabled() {
        if (systemManager == null) {
            return false;
        }

        return systemManager.isEnabled();
    }

    /**
     * Checks if the accessibility service responsible of spoken feedback is active
     */
    public static boolean isSpokenFeedbackEnabled() {
        return hasAccessibilityFeatureTypeEnabled(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
    }

    /**
     * Get a list of accessibility services currently active
     */
    private static boolean hasAccessibilityFeatureTypeEnabled(int type) {
        List<AccessibilityServiceInfo> enabledServices = systemManager.getEnabledAccessibilityServiceList(type);

        return enabledServices != null && enabledServices.size() > 0;
    }

    /**
     * Read a message out loud when spoken feedback is active
     *
     * After announcing has been requested from TalkBack it can't be interrupted.
     * For example, when values change in rapid succession, all changes are
     * announced instead of just announcing the last change. Hence it's recommended
     * usually not to announce. Instead notify TalkBack about changes by sending
     * accessibility events or using accessibilityLiveRegion.
     */
    public static void announce(String message, View host) {
        if (!isAccessibilityEnabled() || !isSpokenFeedbackEnabled()) {
            return;
        }

        host.announceForAccessibility(message);
    }

    /**
     * Get Picker displayValue from value.
     */
    private static String pickerValueToDisplayedValue(Picker picker, int value) {
        String[] displayValues = picker.getDisplayedValues();
        String displayValue = displayValues[value];

        if (displayValue != null) {
            return displayValue;
        }

        return String.valueOf(value);
    }

    /**
     * Tell TalkBack a value has changed
     */
    public static void sendValueChangedEvent(Picker picker, int newValue) {
        AccessibilityEvent event = buildEvent(picker.getView(), AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
        String message = pickerValueToDisplayedValue(picker, newValue);
        event.getText().add(message);

        sendEvent(event);
    }

    private static String getContentDescriptionLabel(String tagName) {
        return Utils.getLocalisedStringFromResources(Accessibility.getLocale(), tagName + "_description");
    }

    public static String getContentDescription(Picker picker) {
        String tagName = picker.getView().getTag().toString();
        int currentValue = picker.getValue();
        String currentDisplayValue = pickerValueToDisplayedValue(picker, currentValue);
        String label = getContentDescriptionLabel(tagName);

        return currentDisplayValue + ", " + label;
    }

    public static AccessibilityEvent buildEvent(View host, int eventType) {
        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        event.setClassName(host.getClass().getName());
        event.setPackageName(host.getContext().getPackageName());

        return event;
    }

    public static void sendEvent(AccessibilityEvent event) {
        if (systemManager == null || !systemManager.isEnabled()) {
            return;
        }
        systemManager.sendAccessibilityEvent(event);
    }

    /**
     * Sets the view associated with given AccessibilityNodeInfo to behave like a seekBar / (slider)
     * when said view receives accessibility focus
     */
    public static void setRoleToSlider(Picker picker, AccessibilityNodeInfo info) {
        // Sets "accessibility role" of the control - affects how the element is read by TalkBack
        info.setClassName("android.widget.SeekBar");
        info.setScrollable(true);
        // Set the "accessibility label" read by spoken feedback
        info.setContentDescription(getContentDescription(picker));

        // Inform that we want to send and receive scrolling-related actions
        // Enables us to handle special accessibility-only scroll-events created by TalkBack
        if (Build.VERSION.SDK_INT >= 21) {
            AccessibilityNodeInfo.AccessibilityAction increaseValue = new AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_SCROLL_FORWARD, "Increase value");
            AccessibilityNodeInfo.AccessibilityAction decreaseValue = new AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD, "Decrease value");

            info.addAction(increaseValue);
            info.addAction(decreaseValue);

        } else {
            info.addAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            info.addAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
        }
    }
}
