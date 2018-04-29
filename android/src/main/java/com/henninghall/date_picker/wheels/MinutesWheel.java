package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.R;
import com.henninghall.date_picker.WheelChangeListener;

import java.util.Calendar;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class MinutesWheel extends Wheel {

    public MinutesWheel(NumberPickerView p, WheelChangeListener listener, Locale locale){
        super(p, listener, locale);
    }

    @Override
    void init() {
        Calendar cal = Calendar.getInstance();
        for(int i=0; i<60; i++) {

            values.add(format.format(cal.getTime()));
            displayValues.add(format.format(cal.getTime()));

            cal.add(Calendar.MINUTE, 1);
        }
        picker.setDisplayedValues(values.toArray(new String[0]));
        picker.setMinValue(0);
        picker.setMaxValue(59);
    }

    @Override
    boolean visible() {
        return true;
    }

    @Override
    public String getFormatTemplate() {
        return "mm";
    }


}
