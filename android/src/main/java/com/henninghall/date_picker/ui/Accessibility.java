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
    private final static AccessibilityManager systemManager =
            (AccessibilityManager) DatePickerPackage.context
                    .getApplicationContext()
                    .getSystemService(Context.ACCESSIBILITY_SERVICE);

    private static Locale mLocale = Locale.getDefault();

    public static void setLocale(Locale nextLocale) {
        mLocale = nextLocale;
    }

    public static Locale getLocale() {
        return mLocale;
    }

    /**
      When TalkBack is active, user can use one finger to explore the screen
      and set focus to elements. Then user can proceed to use second finger
      to scroll contents of focused element.
      When there's multiple pickers next to each other horizontally,
      it's easy to accidentally scroll more than one picker at a time.
      Following code aims to fix this issue.
    */
    public static boolean shouldAllowScroll(View view){
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

        view.setAccessibilityDelegate(
                new View.AccessibilityDelegate(){
                    @Override
                    public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                        super.onPopulateAccessibilityEvent(host, event);
                        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                            // Screen reader reads the content description when focused on each picker wheel
                            Accessibility.updateContentDescription(fPicker);
                        }
                    }

                    @Override
                    public boolean performAccessibilityAction(View host, int action, Bundle args) {
                        int currentValue;
                        switch (action) {
                            case AccessibilityNodeInfo.ACTION_SCROLL_FORWARD:
                                // Increase value when pressing hardware volume up
                                currentValue = fPicker.getValue();
                                fPicker.smoothScrollToValue(currentValue - 1);
                                break;
                            case AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD:
                                // Decrease value when pressing hardware volume down
                                currentValue = fPicker.getValue();
                                fPicker.smoothScrollToValue(currentValue + 1);
                                break;
                        }

                        return super.performAccessibilityAction(host, action, args);
                    }
                }
        );
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
        List<AccessibilityServiceInfo> enabledServices =
                systemManager.getEnabledAccessibilityServiceList(type);

        return enabledServices != null && enabledServices.size() > 0;
    }

    /**
     * Read a message out loud when spoken feedback is active
     */
    public static void announce(String message) {
        if (systemManager == null || !isSpokenFeedbackEnabled()) {
            return;
          }

          AccessibilityEvent event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT);
          event.getText().add(message);
          systemManager.sendAccessibilityEvent(event);
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
     * Read Picker displayed value.
     * For TalkBack to read dates etc. correctly, make sure they are in localised format.
     */
    public static void announcePickerValue(Picker picker, int newValue) {
        announce(pickerValueToDisplayedValue(picker, newValue));
    }

    public static void announceSelectedPickerValue(Picker picker, int newValue) {
        String tagName = picker.getView().getTag().toString();
        String selectedDisplayValue = pickerValueToDisplayedValue(picker, newValue);
        String label = getSelectedContentDescriptionLabel(tagName);
        announce(label + ": " + selectedDisplayValue);
    }

    private static String getContentDescriptionLabel(String tagName) {
        return Utils.getLocalisedStringFromResources(Accessibility.getLocale(), tagName + "_description");
    }

    private static String getSelectedContentDescriptionLabel(String tagName) {
        return Utils.getLocalisedStringFromResources(Accessibility.getLocale(), "selected_" + tagName + "_description");
    }

    public static String getContentDescription(Picker picker) {
        String tagName = picker.getView().getTag().toString();
        int currentValue = picker.getValue();
        String currentDisplayValue = pickerValueToDisplayedValue(picker, currentValue);
        String label = getContentDescriptionLabel(tagName);

        return currentDisplayValue + ", " + label;
    }

    public static void updateContentDescription(Picker picker){
        String nextContentDescription = getContentDescription(picker);
        picker.getView().setContentDescription(nextContentDescription);
    }

    /**
     * Sets the view with associated with given info to behave like a seekBar (slider) when said view receives accessibility focus
     */
    public static void setRoleToSlider(AccessibilityNodeInfo info) {
        // Sets "accessibility role" of the control - affects how the element is read by TalkBack
        info.setClassName("android.widget.SeekBar");

        // info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);

        if (Build.VERSION.SDK_INT >= 21) {
            AccessibilityNodeInfo.AccessibilityAction increaseValue = new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD, "Increase value");
            AccessibilityNodeInfo.AccessibilityAction decreaseValue = new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD, "Decrease value");

            info.addAction(increaseValue);
            info.addAction(decreaseValue);

        } else {
            info.addAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            info.addAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
        }
    }
}
