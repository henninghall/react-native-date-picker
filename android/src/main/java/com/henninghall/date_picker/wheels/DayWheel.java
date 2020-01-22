package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DayWheel extends Wheel {

    public DayWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }
    private static int defaultNumberOfDays = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);

    @Override
    void init() {
        Calendar cal = getStartCal();
        Calendar endCal = getEndCal();

        while (!cal.after(endCal)){
            values.add(getValueFormat(cal));
            displayValues.add(getDisplayValue(cal));
            cal.add(Calendar.DATE, 1);
        }

        picker.setMaxValue(0);
        picker.setDisplayedValues(displayValues.toArray(new String[0]));
        picker.setMinValue(0);
        picker.setMaxValue(displayValues.size() - 1);
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

    private boolean isSameDay(Calendar c1, Calendar c2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", pickerView.locale);
        return (sdf.format(c1.getTime()).equals(sdf.format(c2.getTime())));
    }

    private String getDisplayValue(Calendar cal){
        return Utils.isToday(cal) ? getTodayString() : getDateString(cal);
    }

    private String getValueFormat(Calendar cal){
        return format.format(cal.getTime());
    }

    private String getDateString(Calendar cal){
        return displayFormat.format(cal.getTime()).substring(3);
    }

    private String getTodayString(){
        String todayString = Utils.printToday(pickerView.locale);
        return capitalize(todayString);
    }

    private String capitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    @Override
    public boolean visible() {
        return pickerView.mode == Mode.datetime;
    }

    @Override
    public String getFormatTemplate() {
        String locale = pickerView.locale.getLanguage();
        if(locale.equals("ko"))
            return "yy MMM d일 (EEE)";
        if(locale.equals("ja") || locale.contains("zh"))
            return "yy MMMd日 EEE";
        if(Utils.monthNameBeforeMonthDate(pickerView.locale)){
            return "yy EEE MMM d";
        }
        else return "yy EEE d MMM";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

}
