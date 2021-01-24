package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.models.Mode;

import java.util.ArrayList;
import java.util.Calendar;


public class AmPmWheel extends Wheel {

    public AmPmWheel(final Picker picker, State state) {
        super(picker, state);
    }

    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        // Getting the hours from a date that will never have daylight saving to be sure
        // cal.add() will work properly.
        cal.set(2000,0,0,0,0,0);
        ArrayList<String> values = new ArrayList<>();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        values.add(format.format(cal.getTime()));

        cal.add(Calendar.HOUR_OF_DAY, 12);
        values.add(format.format(cal.getTime()));
        return values;
    }

    @Override
    public boolean visible() {
        return state.derived.usesAmPm() && state.getMode() != Mode.date;
    }

    @Override
    public boolean wrapSelectorWheel() {
        return false;
    }

    @Override
    public String getFormatPattern() {
        return state.derived.usesAmPm() ? " a " : "";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

}
