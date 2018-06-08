package com.henninghall.date_picker;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        return new Date((long)date);
    }

}
