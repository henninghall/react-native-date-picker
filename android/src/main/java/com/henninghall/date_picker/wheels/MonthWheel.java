package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import java.util.*;
import com.henninghall.date_picker.*;

public class MonthWheel extends Wheel
{
    public MonthWheel(final PickerView pickerView, final int id) {
        super(pickerView, id);
    }


    @Override
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MONTH, 0);
        for (int i = 0; i <= 11; ++i) {
            values.add(getLocaleString(cal));
            cal.add(Calendar.MONTH, 1);
        }
        return values;
    }

    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return "LLLL";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }


}