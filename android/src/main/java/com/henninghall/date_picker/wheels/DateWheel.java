package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.henninghall.date_picker.*;

public class DateWheel extends Wheel
{
    public DateWheel(final PickerView pickerView, final int id) {
        super(pickerView, id);
    }


    @Override
    public ArrayList<String> getValues() {
        Calendar cal = Calendar.getInstance();
        ArrayList<String> values = new ArrayList<>();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 0);
        final int maxDate = 31;
        final int minDate = 1;
        for (int i = minDate; i <= maxDate; ++i) {
            values.add(getLocaleString(cal));
            cal.add(Calendar.DATE, 1);
        }
        return values;
    }

    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }

    @Override
    public String getFormatPattern() {
        return LocaleUtils.getPatternIncluding("d", pickerView.locale);
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

}
