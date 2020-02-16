package com.henninghall.date_picker;


import java.util.Calendar;
import java.util.TimeZone;

public class DateBoundary {
    private Calendar date;

    DateBoundary(TimeZone timezone, String date) {
        if(date == null) return;
        Calendar cal = Utils.isoToCalendar(date, timezone);
        this.date = Utils.getTruncatedCalendarOrNull(cal);
    }

    protected Calendar get() {
        return this.date;
    }
}
