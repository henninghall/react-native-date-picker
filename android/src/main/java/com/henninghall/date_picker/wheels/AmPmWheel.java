package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Settings;

import java.util.ArrayList;
import java.util.Calendar;


public class AmPmWheel extends Wheel {

    public AmPmWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }

    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        ArrayList<String> values = new ArrayList<>();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        values.add(format.format(cal.getTime()));

        cal.add(Calendar.HOUR_OF_DAY, 12);
        values.add(format.format(cal.getTime()));
        return values;
    }

    @Override
    public boolean visible() {
        return Settings.usesAmPm() && pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return Settings.usesAmPm() ? " a " : "";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }

}
