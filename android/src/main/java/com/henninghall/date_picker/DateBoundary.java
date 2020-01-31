package com.henninghall.date_picker;

import java.util.Calendar;

public class DateBoundary {
    private Calendar date;

    DateBoundary(PickerView pickerView, String date) {
        if(date == null) return;
        Calendar cal = Utils.isoToCalendar(date, pickerView.timeZone);
        this.date = Utils.getTruncatedCalendarOrNull(cal);
    }

    protected Calendar get() {
        return this.date;
    }
}
