package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.HourDisplayBugWorkaround;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;

import java.util.ArrayList;
import java.util.Calendar;

public class HourWheel extends Wheel {

    private final HourDisplayBugWorkaround hourDisplayAdjustment;

    public HourWheel(Picker picker, State id) {
        super(picker, id);
        this.hourDisplayAdjustment = new HourDisplayBugWorkaround(state);
    }

    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        // Getting the hours from a date that doesn't have daylight saving to be sure
        // cal.add() will work properly.
        cal.set(2000,0,0,0,0,0);

        ArrayList<String> values = new ArrayList<>();
        int numberOfHours = state.derived.usesAmPm() ? 12 : 24;

        for(int i=0; i<numberOfHours; i++) {
            values.add(format.format(cal.getTime()));
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        return values;
    }

    @Override
    public String toDisplayValue(String value) {
        return hourDisplayAdjustment.adjustValueIfNecessary(value);
    }

    @Override
    public boolean visible() {
        return state.getMode() != Mode.date;
    }

    @Override
    public boolean wrapSelectorWheel() {
        return true;
    }

    @Override
    public String getFormatPattern() {
        return state.derived.usesAmPm() ? "h": "HH";
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

}
