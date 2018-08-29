package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;

import java.util.Calendar;

public class YearWheel extends Wheel
{
    private int defaultStartYear;
    private int defaultEndYear;

    public YearWheel(final PickerView pickerView, final int id) {
        super(pickerView, id);
        this.defaultStartYear = 0;
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

        picker.setDisplayedValues(displayValues.toArray(new String[0]));
        picker.setMinValue(0);
        picker.setMaxValue(max);
    }

    private int getEndYear() {
        if (this.pickerView.maxDate == null) {
            return this.defaultEndYear;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTime(this.pickerView.maxDate);
        return cal.get(Calendar.YEAR);
    }

    private int getStartYear() {
        if (this.pickerView.minDate != null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(this.pickerView.minDate);
            return cal.get(Calendar.YEAR);
        }
        return this.defaultStartYear;
    }

    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }

    @Override
    public String getFormatTemplate() {
        return "y";
    }
}

