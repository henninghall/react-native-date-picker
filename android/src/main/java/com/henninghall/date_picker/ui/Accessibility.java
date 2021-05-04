package com.henninghall.date_picker.ui;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.Wheel;

import java.util.Locale;

public class Accessibility {

    public static class SetAccessibilityDelegate implements WheelFunction {

        private final Locale locale;

        public SetAccessibilityDelegate(Locale locale) {
            this.locale = locale;
        }

        @Override
        public void apply(Wheel wheel) {
            final View view = wheel.picker.getView();
            view.setAccessibilityDelegate(
                    new View.AccessibilityDelegate(){
                        @Override
                        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                            super.onPopulateAccessibilityEvent(host, event);
                            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                                String resourceKey = view.getTag().toString()+"_description";
                                String localeTag =  Utils.getLocalisedStringFromResources(locale, resourceKey);
                                // Screen reader reads the content description when focused on each picker wheel
                                view.setContentDescription(localeTag);
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

    public void update(Wheel picker){
        String tagName = picker.picker.getView().getTag().toString();
        String selectedDateString = getAccessibleTextForSelectedDate();
        String descriptionPrefix = Utils.getLocalisedStringFromResources(state.getLocale(), "selected_"+tagName+"_description");
        String descriptionPostFix = Utils.getLocalisedStringFromResources(state.getLocale(), "selected_value_description");

        picker.picker.getView().setContentDescription(descriptionPrefix + ", "+ descriptionPostFix + " "+ selectedDateString);
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
