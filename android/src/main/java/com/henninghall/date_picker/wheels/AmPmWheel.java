package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.WheelChangeListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class AmPmWheel extends Wheel {

    public AmPmWheel(NumberPickerView p, WheelChangeListener listener, Locale locale){
        super(p, listener, locale);
    }

    @Override
    void init() {

        Calendar cal = Calendar.getInstance();
        displayValues.add(displayFormat.format(cal.getTime()));
        values.add(format.format(cal.getTime()));

        cal.add(Calendar.HOUR_OF_DAY, 12);
        displayValues.add(displayFormat.format(cal.getTime()));
        values.add(format.format(cal.getTime()));

        picker.setDisplayedValues(displayValues.toArray(new String[0]));

        picker.setMinValue(0);
        picker.setMaxValue(1);
        picker.setValue(0);
    }

    @Override
    boolean visible() {
        return Utils.usesAmPm(locale);
    }

    @Override
    public String getFormatTemplate() {
        return Utils.usesAmPm(locale) ? " a " : "";
    }


}
