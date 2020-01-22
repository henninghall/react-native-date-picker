package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;

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
    void init() {
        final int startYear = getStartYear();
        final int endYear = getEndYear() ;
        int max = endYear - startYear;

        for (int i = 0; i <= max; ++i) {
            values.add(String.valueOf(startYear + i));
            displayValues.add(String.valueOf(startYear + i));
        }

        picker.setMaxValue(0);
        picker.setDisplayedValues(displayValues.toArray(new String[0]));
        picker.setMinValue(0);
        picker.setMaxValue(max);
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
    public String getDisplayFormatTemplate() {
        return "y";
    }

    @Override
    public String getFormatTemplate() {
        return "y";
    }


}

