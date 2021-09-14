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
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.Wheel;
import com.henninghall.date_picker.pickers.Picker;

import java.util.Locale;
import java.util.List;

public class Accessibility {
    private final static AccessibilityManager systemManager =
            (AccessibilityManager) DatePickerPackage.context
                    .getApplicationContext()
                    .getSystemService(Context.ACCESSIBILITY_SERVICE);

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

    public static void startAccessibilityDelegate(Picker picker, Locale locale) {
        final Picker fPicker = picker;
        final Locale fLocale =locale;
        final View view = picker.getView();

        view.setAccessibilityDelegate(
                new View.AccessibilityDelegate(){
                    @Override
                    public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                        super.onPopulateAccessibilityEvent(host, event);
                        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                            // Screen reader reads the content description when focused on each picker wheel
                            Accessibility.updateContentDescription(fPicker, fLocale);
                        }
                    }
                }
        );
    }

    public static class SetAccessibilityDelegate implements WheelFunction {

        private final Locale locale;

        public SetAccessibilityDelegate(Locale locale) {
            this.locale = locale;
        }

        @Override
        public void apply(Wheel wheel) {
            final Picker picker = wheel.picker;
            final View view = wheel.picker.getView();

            view.setAccessibilityDelegate(
                    new View.AccessibilityDelegate(){
                        @Override
                        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                            super.onPopulateAccessibilityEvent(host, event);
                            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                                // Screen reader reads the content description when focused on each picker wheel
                                Accessibility.updateContentDescription(picker, locale);
                            }
                        }
                    }
            );
        }
    }

    private final State state;
    private final Wheels wheels;

    public Accessibility(State state, Wheels wheels){
        this.state = state;
        this.wheels = wheels;
    }

    /**
     * DEL
     */
    public void update(Wheel picker){
        String tagName = picker.picker.getView().getTag().toString();
        String selectedDateString = getAccessibleTextForSelectedDate();
        String descriptionPrefix = Utils.getLocalisedStringFromResources(state.getLocale(), "selected_"+tagName+"_description");
        String descriptionPostFix = Utils.getLocalisedStringFromResources(state.getLocale(), "selected_value_description");

        picker.picker.getView().setContentDescription(descriptionPrefix + ", "+ descriptionPostFix + " "+ selectedDateString);
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
        final String[] displayValues = picker.getDisplayedValues();

        final String displayValue = displayValues[value];

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

    public static void announceSelectedPickerValue(Picker picker, int newValue, Locale locale) {
        final String tagName = picker.getView().getTag().toString();
        final String selectedDisplayValue = pickerValueToDisplayedValue(picker, newValue);
        final String label = getSelectedContentDescriptionLabel(tagName, locale);
        announce(label + ": " + selectedDisplayValue);
    }

    private static String getContentDescriptionLabel(String tagName, Locale locale) {
        // TODO: create static class property locale used here
        // Ex add private final static string locale with set
        return Utils.getLocalisedStringFromResources(locale, tagName + "_description");
    }

    private static String getSelectedContentDescriptionLabel(String tagName, Locale locale) {
        // TODO: create static class property locale used here
        // Ex add private final static string locale with set
        return Utils.getLocalisedStringFromResources(locale, "selected_" + tagName + "_description");
    }

    public static String getContentDescription(Picker picker, Locale locale) {
        final String tagName = picker.getView().getTag().toString();
        final int currentValue = picker.getValue();
        final String currentDisplayValue = pickerValueToDisplayedValue(picker, currentValue);
        final String label = getContentDescriptionLabel(tagName, locale);

        return currentDisplayValue + ", " + label;
    }

    public static void updateContentDescription(Picker picker, Locale locale){
        final String nextContentDescription = getContentDescription(picker, locale);
        picker.getView().setContentDescription(nextContentDescription);
    }

    private String getAccessibleTextForSelectedDate() {
        String accessibleText;
        switch(state.getMode()) {
            case date:
                accessibleText = wheels.getDateString();
                break;
            case time:
                accessibleText = wheels.getTimeString();
                break;
            default:
                // default is dateTime
                String timePrefix = Utils.getLocalisedStringFromResources(state.getLocale(), "time_tag");
                String hourPrefix = Utils.getLocalisedStringFromResources(state.getLocale(), "hour_tag");
                String minutesPrefix = Utils.getLocalisedStringFromResources(state.getLocale(), "minutes_tag");
                accessibleText = wheels.getAccessibleDateTimeString(timePrefix, hourPrefix, minutesPrefix);
                break;
        }
        return accessibleText;
    }

}
