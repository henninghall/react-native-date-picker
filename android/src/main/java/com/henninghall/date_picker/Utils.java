package com.henninghall.date_picker;


import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static boolean usesAmPm(Locale locale) {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.FULL, locale);
        return df instanceof SimpleDateFormat && ((SimpleDateFormat) df).toPattern().contains("a");
    }

    public static String printToday(Locale locale) {
        return UnitPatterns.of(locale).getTodayWord();
    }

    public static Date unixToDate(double date) {
        return date > 0 ? new Date((long)date) : null;
    }

    public static int getWheelHeight(View pickerView) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, pickerView.getResources().getDisplayMetrics());
    }

    public static String localeToYmdPattern(Locale locale) {
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        String pattern = ((SimpleDateFormat)formatter).toLocalizedPattern();
        pattern = pattern.replaceAll("\\[", "");
        pattern = pattern.replaceAll("]", "");
        pattern = pattern.replaceAll(" ", "");
        pattern = pattern.replaceAll("[.]", "/");
        pattern = pattern.replaceAll("-", "/");
        return pattern;
    }

    public static boolean isToday(Calendar cal){
        return DateUtils.isToday(cal.getTimeInMillis());
    }

    public static Date getTruncatedDateOrNull(Date date) {
        try {
            return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.MINUTE);
        } catch (Exception e){
            return null;
        }
    }
}
