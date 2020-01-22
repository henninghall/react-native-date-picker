package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Settings;
import com.henninghall.date_picker.WheelPosition;

import java.util.Calendar;


public class AmPmWheel extends Wheel {

    public AmPmWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }

    @Override
    void init() {

        Calendar cal = pickerView.getInitialDate();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        displayValues.add(displayFormat.format(cal.getTime()));
        values.add(format.format(cal.getTime()));

        cal.add(Calendar.HOUR_OF_DAY, 12);
        displayValues.add(displayFormat.format(cal.getTime()));
        values.add(format.format(cal.getTime()));

        picker.setDisplayedValues(displayValues.toArray(new String[0]));

        picker.setMinValue(0);
        picker.setMaxValue(1);
    }

    @Override
    public boolean visible() {
        return Settings.usesAmPm() && pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatTemplate() {
        return Settings.usesAmPm() ? " a " : "";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }

}
