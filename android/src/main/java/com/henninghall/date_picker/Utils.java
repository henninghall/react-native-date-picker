package com.henninghall.date_picker;


import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.View;

import net.time4j.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

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

    public static float dpToPixels(int dp, Context context){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics()
        );
    }

}
