package com.henninghall.date_picker.ui;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.accessibilityservice.AccessibilityServiceInfo;

import com.henninghall.date_picker.DatePickerPackage;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.wheels.Wheel;
import com.henninghall.date_picker.pickers.Picker;

import java.util.Locale;
import java.util.List;

public class Accessibility {
    private final static AccessibilityManager systemManager =
            (AccessibilityManager) DatePickerPackage.context
                    .getApplicationContext()
                    .getSystemService(Context.ACCESSIBILITY_SERVICE);

    private static Locale locale = Locale.getDefault();

    public static setLocale(Locale nextLocale) {
        this.locale = nextLocale;
    }

    public static Locale getLocale() {
        return this.locale;
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
                            Accessibility.updateContentDescription(fPicker, Accessibility.getLocale());
                        }
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
        String label = getSelectedContentDescriptionLabel(tagName, Accessibility.getLocale());
        announce(label + ": " + selectedDisplayValue);
    }

    private static String getContentDescriptionLabel(String tagName) {
        // TODO: create static class property locale used here
        // Ex add private final static string locale with set
        return Utils.getLocalisedStringFromResources(Accessibility.getLocale(), tagName + "_description");
    }

    private static String getSelectedContentDescriptionLabel(String tagName) {
        // TODO: create static class property locale used here
        // Ex add private final static string locale with set
        return Utils.getLocalisedStringFromResources(Accessibility.getLocale(), "selected_" + tagName + "_description");
    }

    public static String getContentDescription(Picker picker) {
        String tagName = picker.getView().getTag().toString();
        int currentValue = picker.getValue();
        String currentDisplayValue = pickerValueToDisplayedValue(picker, currentValue);
        String label = getContentDescriptionLabel(tagName, Accessibility.getLocale());

        return currentDisplayValue + ", " + label;
    }

    public static void updateContentDescription(Picker picker){
        String nextContentDescription = getContentDescription(picker, Accessibility.getLocale());
        picker.getView().setContentDescription(nextContentDescription);
    }
}
