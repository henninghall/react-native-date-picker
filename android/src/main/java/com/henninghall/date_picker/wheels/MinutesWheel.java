package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.PickerView;
import java.util.Calendar;
import java.util.Date;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class MinutesWheel extends Wheel {

    public MinutesWheel(NumberPickerView minutePicker, PickerView pickerView) {
        super(minutePicker, pickerView);
    }

    @Override
    void init() {
        Calendar cal = pickerView.getInitialDate();

        for(int i=0; i<60; i = i + pickerView.minuteInterval) {
            values.add(format.format(cal.getTime()));
            displayValues.add(format.format(cal.getTime()));
            cal.add(Calendar.MINUTE, pickerView.minuteInterval);
        }

        picker.setMinValue(0);
        picker.setMaxValue(0);
        picker.setDisplayedValues(values.toArray(new String[0]));
        picker.setMaxValue(displayValues.size() - 1);
    }

    @Override
    public boolean visible() {
        return true;
    }

    @Override
    public String getFormatTemplate() {
        return "mm";
    }

}
