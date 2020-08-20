package com.henninghall.date_picker;


import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.henninghall.date_picker.models.WheelType;

import net.time4j.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    public static boolean usesAmPm(){
        return !DateFormat.is24HourFormat(DatePickerManager.context);
    }

    public static String printToday(Locale locale) {
        return PrettyTime.of(locale).printToday();
    }

    public static Calendar isoToCalendar(String dateString, TimeZone timeZone)  {
        if(dateString == null) return null;
        try {
            Calendar calendar = Calendar.getInstance(timeZone);
            calendar.setTime(getIsoUTCFormat().parse(dateString));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToIso(Calendar date) {
        return getIsoUTCFormat().format(date.getTime());
    }

    public static boolean isToday(Calendar cal){
        return DateUtils.isToday(cal.getTimeInMillis());
    }

    public static Calendar getTruncatedCalendarOrNull(Calendar cal) {
        try {
            return org.apache.commons.lang3.time.DateUtils.truncate(cal, Calendar.MINUTE);
        } catch (Exception e){
            return null;
        }
    }

    private static SimpleDateFormat getIsoUTCFormat(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return format;
    }

    public static ArrayList<String> splitOnSpace(String value){
        String[] array = value.split("\\s+");
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, array);
        return arrayList;
    }

    public static String capitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static WheelType patternCharToWheelType(char patternChar) throws Exception {
        switch (patternChar){
            case 'y': return WheelType.YEAR;
            case 'M': return WheelType.MONTH;
            case 'd': return WheelType.DATE;
            case 'h':
            case 'H':
                return WheelType.HOUR;
            case 'm': return WheelType.MINUTE;
            case 'a': return WheelType.AM_PM;
            default: throw new Exception("Invalid pattern char: " + patternChar);
        }
    }
}
