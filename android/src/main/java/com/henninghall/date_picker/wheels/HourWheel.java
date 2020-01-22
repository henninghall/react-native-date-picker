package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Settings;
import java.util.Calendar;

public class HourWheel extends Wheel {

    public HourWheel(PickerView pickerView, int id) {
        super(pickerView, id);
    }

    @Override
    void init() {
        int numberOfHours = Settings.usesAmPm() ? 12 : 24;
        Calendar cal = pickerView.getInitialDate();

        for(int i=0; i<numberOfHours; i++) {
            values.add(format.format(cal.getTime()));
            displayValues.add(format.format(cal.getTime()));
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        picker.setDisplayedValues(values.toArray(new String[0]),true);
        picker.setMinValue(0);
        picker.setMaxValue(numberOfHours - 1);
    }

    @Override
    public boolean visible() {
        return pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatTemplate() {
        return Settings.usesAmPm() ? "h": "HH";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

}
