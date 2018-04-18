package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.WheelChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class AmPmWheel extends Wheel {

    public AmPmWheel(NumberPickerView p, WheelChangeListener listener, Locale locale){
        super(p, listener, locale);
    }

    @Override
    void init() {
        values.add("AM");
        values.add("PM");

        picker.setDisplayedValues(values.toArray(new String[0]));

        picker.setMinValue(0);
        picker.setMaxValue(1);
        picker.setValue(0);
    }

    @Override
    public String getFormatTemplate() {
        return Utils.usesAmPm(locale) ? "a" : "";
    }


}
