package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Settings;

import java.util.ArrayList;
import java.util.Calendar;

public class HourWheel extends Wheel {

    public HourWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }


    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        ArrayList<String> values = new ArrayList<>();
        int numberOfHours = Settings.usesAmPm() ? 12 : 24;

        for(int i=0; i<numberOfHours; i++) {
            values.add(format.format(cal.getTime()));
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        return values;
    }

    @Override
    public boolean visible() {
        return pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return Settings.usesAmPm() ? "h": "HH";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

}
