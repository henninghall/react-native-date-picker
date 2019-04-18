package com.henninghall.date_picker;


import android.text.format.DateFormat;

public class Settings {

    public static boolean usesAmPm (){
        return !DateFormat.is24HourFormat(DatePickerManager.context);
    }

}
