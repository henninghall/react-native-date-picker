package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;

import java.util.ArrayList;
import java.util.Calendar;

public class YearWheel extends Wheel
{
    private int defaultStartYear;
    private int defaultEndYear;

    public YearWheel(final PickerView pickerView, final int id) {
        super(pickerView, id);
        this.defaultStartYear = 1900;
        this.defaultEndYear = 2100;
    }

    @Override
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        final int startYear = getStartYear();
        final int endYear = getEndYear();
        int max = endYear - startYear;

        cal.set(Calendar.YEAR, startYear);

        for (int i = 0; i <= max; ++i) {
            values.add(getLocaleString(cal));
            cal.add(Calendar.YEAR, 1);
        }

        return values;
    }

    private int getEndYear() {
        if (this.pickerView.getMaximumDate() == null) {
            return this.defaultEndYear;
        }
        return this.pickerView.getMaximumDate().get(Calendar.YEAR);
    }

    private int getStartYear() {
        if (this.pickerView.getMinimumDate() == null) {
            return this.defaultStartYear;
        }
        return this.pickerView.getMinimumDate().get(Calendar.YEAR);
    }

    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

    @Override
    public String getFormatPattern() {
        return LocaleUtils.getPatternIncluding("y", pickerView.locale);
    }

}

