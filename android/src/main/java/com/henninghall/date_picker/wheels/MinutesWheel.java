package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.models.Mode;

import java.util.ArrayList;
import java.util.Calendar;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class MinutesWheel extends Wheel {

    public MinutesWheel(NumberPickerView picker, State id) {
        super(picker, id);
    }

    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        ArrayList<String> values = new ArrayList<>();

        cal.set(Calendar.MINUTE, 0);
        for(int i=0; i<60; i = i + state.getMinuteInterval()) {
            values.add(format.format(cal.getTime()));
            cal.add(Calendar.MINUTE, state.getMinuteInterval());
        }

        return values;
    }

    @Override
    public boolean visible() {
        return state.getMode() != Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return "mm";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Utils.usesAmPm() ? Paint.Align.RIGHT: Paint.Align.LEFT;
    }

}
