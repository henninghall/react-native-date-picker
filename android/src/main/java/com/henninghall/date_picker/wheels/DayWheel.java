package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import android.text.TextUtils;

import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DayWheel extends Wheel {

    private String todayValue;

    public DayWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }
    private static int defaultNumberOfDays = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);

    @Override
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        Calendar cal = getStartCal();
        Calendar endCal = getEndCal();

        while (!cal.after(endCal)){
            String value = getValueFormat(cal);
            values.add(value);
            if(Utils.isToday(cal)) todayValue = value;
            cal.add(Calendar.DATE, 1);
        }

        return values;
    }

    private Calendar getStartCal(){
        Calendar cal;
        Calendar max = pickerView.getMaximumDate();
        Calendar min = pickerView.getMinimumDate();
        if (min != null) {
            cal = (Calendar) min.clone();
            resetToMidnight(cal);
        } else if (max != null) {
            cal = (Calendar) max.clone();
            resetToMidnight(cal);
            cal.add(Calendar.DATE, -cal.getActualMaximum(Calendar.DAY_OF_YEAR) / 2);
        } else {
            cal = (Calendar) pickerView.getInitialDate().clone();
            cal.add(Calendar.DATE, -defaultNumberOfDays / 2);
        }
        return cal;
    }

    private Calendar getEndCal(){
        Calendar cal;
        Calendar max = pickerView.getMaximumDate();
        Calendar min = pickerView.getMinimumDate();
        if (max != null) {
            cal = (Calendar) max.clone();
            resetToMidnight(cal);
        } else if (min != null) {
            cal = (Calendar) min.clone();
            resetToMidnight(cal);
            cal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_YEAR) / 2);
        } else {
            cal = (Calendar) pickerView.getInitialDate().clone();
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

    private String getValueFormat(Calendar cal){
        return format.format(cal.getTime());
    }

    @Override
    public boolean visible() {
        return pickerView.mode == Mode.datetime;
    }


    @Override
    public String getFormatPattern() {
        return LocaleUtils.getDatePattern(pickerView.locale)
                .replace("EEEE", "EEE")
                .replace("MMMM", "MMM");
    }

    @Override
    public String toDisplayValue(String value) {
        if (value.equals(todayValue)) {
            return toTodayString(value);
        }
        return removeYear(value);
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

    private String toTodayString(String value) {
        String todayString = Utils.printToday(pickerView.locale);
        boolean shouldBeCapitalized = Character.isUpperCase(value.charAt(0));
        return shouldBeCapitalized
                ? Utils.capitalize(todayString)
                : todayString;
    }

    private String removeYear(String value) {
        ArrayList<String> pieces = Utils.splitOnSpace(value);
        pieces.remove(LocaleUtils.getFullPatternPos("y", pickerView.locale));
        return TextUtils.join(" ", pieces);
    }

}
