package com.henninghall.date_picker.ui;

import android.view.View;

import com.henninghall.date_picker.Emitter;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class WheelChangeListenerImpl implements WheelChangeListener {

    private final Wheels wheels;
    private final State state;
    private final UIManager uiManager;
    private final View rootView;

    WheelChangeListenerImpl(Wheels wheels, State state, UIManager uiManager, View rootView) {
        this.wheels = wheels;
        this.uiManager = uiManager;
        this.state = state;
        this.rootView = rootView;
    }

    private SimpleDateFormat getDateFormat(){
        TimeZone timeZone = state.getTimeZone();
        SimpleDateFormat dateFormat = uiManager.getDateFormat();
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    @Override
    public void onChange(Wheel picker) {
        if(wheels.hasSpinningWheel()) return;

        if(!dateExists()){
            Calendar closestExistingDate = getClosestExistingDateInPast();
            if(closestExistingDate != null) {
                uiManager.animateToDate(closestExistingDate);
            }
            return;
        }

        Calendar selectedDate = getSelectedDate();
        if(selectedDate == null) return;

        Calendar minDate = state.getMinimumDate();
        if (minDate != null && selectedDate.before(minDate)) {
            uiManager.animateToDate(minDate);
            return;
        }

        Calendar maxDate = state.getMaximumDate();
        if (maxDate != null && selectedDate.after(maxDate)) {
            uiManager.animateToDate(maxDate);
            return;
        }

        String displayData = uiManager.getDisplayValueString();

        uiManager.updateLastSelectedDate(selectedDate);
        Emitter.onDateChange(selectedDate, displayData, state.getId(), rootView);
    }

    // Example: Jan 1 returns true, April 31 returns false.
    private boolean dateExists(){
        SimpleDateFormat dateFormat = getDateFormat();
        String toParse = wheels.getDateTimeString();
        try {
            dateFormat.setLenient(false); // disallow parsing invalid dates
            dateFormat.parse(toParse);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private Calendar getSelectedDate(){
        SimpleDateFormat dateFormat = getDateFormat();
        String toParse = wheels.getDateTimeString();
        TimeZone timeZone = state.getTimeZone();
        Calendar date = Calendar.getInstance(timeZone);
        try {
            dateFormat.setLenient(true); // allow parsing invalid dates
            date.setTime(dateFormat.parse(toParse));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Calendar getClosestExistingDateInPast(){
        SimpleDateFormat dateFormat = getDateFormat();
        dateFormat.setLenient(false); // disallow parsing invalid dates

        int maxDaysInPastToCheck = 10;
        for (int i = 0; i < maxDaysInPastToCheck; i++){
            try {
                String toParse = wheels.getDateTimeString(i);
                Calendar calendar = Calendar.getInstance(state.getTimeZone());
                calendar.setTime(dateFormat.parse(toParse));
                return calendar;
            } catch (ParseException ignored) {
                // continue checking if exception (which means invalid date)
            }
        }
        return null;
    }

}
