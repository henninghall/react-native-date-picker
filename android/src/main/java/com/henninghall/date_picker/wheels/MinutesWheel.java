package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Settings;

import java.util.Calendar;

public class MinutesWheel extends Wheel {

    public MinutesWheel(PickerView pickerView, int id) {
        super(pickerView, id);
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
        return pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatTemplate() {
        return "mm";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Settings.usesAmPm() ? Paint.Align.RIGHT: Paint.Align.LEFT;
    }

}
