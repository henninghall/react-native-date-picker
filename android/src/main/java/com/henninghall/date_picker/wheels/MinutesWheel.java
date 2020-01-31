package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Settings;

import java.util.ArrayList;
import java.util.Calendar;

public class MinutesWheel extends Wheel {

    public MinutesWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }

    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        ArrayList<String> values = new ArrayList<>();

        cal.set(Calendar.MINUTE, 0);
        for(int i=0; i<60; i = i + pickerView.minuteInterval) {
            values.add(format.format(cal.getTime()));
            cal.add(Calendar.MINUTE, pickerView.minuteInterval);
        }

        return values;
    }

    @Override
    public boolean visible() {
        return pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return "mm";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Settings.usesAmPm() ? Paint.Align.RIGHT: Paint.Align.LEFT;
    }

}
