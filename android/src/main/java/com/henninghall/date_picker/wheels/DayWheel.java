package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class DayWheel extends Wheel {

    private String todayValue;
    private static int defaultNumberOfDays = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
    private HashMap<String, String> displayValues;

    public DayWheel(Picker picker, State state) {
        super(picker, state);
    }

    @Override
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        displayValues = new HashMap<>();

        Calendar cal = getStartCal();
        Calendar endCal = getEndCal();

        while (!cal.after(endCal)){
            String value = getValue(cal);
            values.add(value);
            displayValues.put(value, getDisplayValue(cal));
            if(Utils.isToday(cal)) todayValue = value;
            cal.add(Calendar.DATE, 1);
        }

        return values;
    }

    private Calendar getStartCal(){
        Calendar cal;
        Calendar max = state.getMaximumDate();
        Calendar min = state.getMinimumDate();
        if (min != null) {
            cal = (Calendar) min.clone();
            resetToMidnight(cal);
        } else if (max != null) {
            cal = (Calendar) max.clone();
            resetToMidnight(cal);
            cal.add(Calendar.DATE, -cal.getActualMaximum(Calendar.DAY_OF_YEAR) / 2);
        } else {
            cal = (Calendar) getInitialDate().clone();
            cal.add(Calendar.DATE, -defaultNumberOfDays / 2);
        }
        return cal;
    }

    private Calendar getEndCal(){
        Calendar cal;
        Calendar max = state.getMaximumDate();
        Calendar min = state.getMinimumDate();
        if (max != null) {
            cal = (Calendar) max.clone();
            resetToMidnight(cal);
        } else if (min != null) {
            cal = (Calendar) min.clone();
            resetToMidnight(cal);
            cal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_YEAR) / 2);
        } else {
            cal = (Calendar) getInitialDate().clone();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, defaultNumberOfDays / 2);
        }
        return cal;
    }

    private void resetToMidnight(Calendar cal){
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    private String getValue(Calendar cal){
        return format.format(cal.getTime());
    }

    private String getDisplayValue(Calendar cal){
        return getDisplayValueFormat().format(cal.getTime());
    }

    private String getDisplayValueFormatPattern(){
        return LocaleUtils.getDay(state.getLocaleLanguageTag());
    }

    private SimpleDateFormat getDisplayValueFormat(){
        return new SimpleDateFormat(getDisplayValueFormatPattern(), state.getLocale());
    }

    @Override
    public boolean visible() {
        return state.getMode() == Mode.datetime;
    }

    @Override
    public boolean wrapSelectorWheel() {
        return false;
    }


    @Override
    public String getFormatPattern() {
       return LocaleUtils.getDatePattern(state.getLocale())
               .replace("EEEE", "EEE")
               .replace("MMMM", "MMM");
    }

    @Override
    public String toDisplayValue(String value) {
        if (value.equals(todayValue)) {
            return toTodayString(value);
        }
        return displayValues.get(value);
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

    private String toTodayString(String value) {
        String todayString = Utils.printToday(state.getLocale());
        boolean shouldBeCapitalized = Character.isUpperCase(value.charAt(0));
        return shouldBeCapitalized
                ? Utils.capitalize(todayString)
                : todayString;
    }

    // Rounding cal to closest minute interval
    private Calendar getInitialDate() {
        Calendar cal = Calendar.getInstance();
        int minuteInterval = state.getMinuteInterval();
        if(minuteInterval <= 1) return cal;
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", state.getLocale());
        int exactMinute = Integer.valueOf(minuteFormat.format(cal.getTime()));
        int diffSinceLastInterval = exactMinute % minuteInterval;
        int diffAhead = minuteInterval - diffSinceLastInterval;
        int diffBehind= -diffSinceLastInterval;
        boolean closerToPrevious = minuteInterval / 2 > diffSinceLastInterval;
        int diffToExactValue = closerToPrevious ? diffBehind : diffAhead;
        cal.add(Calendar.MINUTE, diffToExactValue);
        return (Calendar) cal.clone();
    }

}
