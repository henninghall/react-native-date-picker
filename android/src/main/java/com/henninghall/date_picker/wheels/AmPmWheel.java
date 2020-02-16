package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.models.Mode;

import java.util.ArrayList;
import java.util.Calendar;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class AmPmWheel extends Wheel {

    public AmPmWheel(final NumberPickerView picker, State state) {
        super(picker, state);
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
        return Utils.usesAmPm() && state.getMode() != Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return Utils.usesAmPm() ? " a " : "";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }

}
