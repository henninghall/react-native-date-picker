package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import java.util.*;
import com.henninghall.date_picker.*;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.wheelFunctions.HorizontalPadding;

public class MonthWheel extends Wheel
{
    public MonthWheel(final Picker picker, final State id) {
        super(picker, id);
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
        return state.getMode() == Mode.date;
    }

    @Override
    public boolean wrapSelectorWheel() {
        return true;
    }

    @Override
    public String getFormatPattern() {
        return "LLLL";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }

    @Override
    public int getHorizontalPadding() {
        return 1;
    }


}