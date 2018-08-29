package com.henninghall.date_picker.wheels;

import java.util.*;
import com.henninghall.date_picker.*;

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
        final int startYear = this.getStartYear();
        final int endYear = this.getEndYear();
        for (int i = startYear; i <= endYear; ++i) {
            this.values.add(String.valueOf(i));
            this.displayValues.add(String.valueOf(i));
        }
        this.picker.setDisplayedValues((String[])this.displayValues.toArray(new String[0]));
        final int year = Calendar.getInstance().get(1);
        this.picker.setMinValue(startYear);
        this.picker.setMaxValue(endYear);
    }
    
    private int getEndYear() {
        if (this.pickerView.maxDate == null) {
            return this.defaultEndYear;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTime(this.pickerView.maxDate);
        return cal.get(1);
    }
    
    private int getStartYear() {
        if (this.pickerView.minDate != null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(this.pickerView.minDate);
            return cal.get(1);
        }
        return this.defaultStartYear;
    }
    
    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }
    
    public String getFormatTemplate() {
        return "y";
    }
}

