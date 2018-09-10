package com.henninghall.date_picker.wheels;

import java.text.*;
import java.util.*;
import com.henninghall.date_picker.*;

public class MonthWheel extends Wheel
{
    public MonthWheel(final PickerView pickerView, final int id) {
        super(pickerView, id);
    }
    
    @Override
    void init() {
        final int min = 0;
        final int max = 11;
        final Calendar cal = this.pickerView.getInitialDate();
        final SimpleDateFormat format = new SimpleDateFormat(this.getFormatTemplate(), this.pickerView.locale);
        for (int i = min; i <= max; ++i) {
            this.values.add(format.format(cal.getTime()));
            this.displayValues.add(format.format(cal.getTime()));
            cal.add(Calendar.MONTH, 1);
        }
        this.picker.setDisplayedValues(this.displayValues.toArray(new String[0]));
        this.picker.setMinValue(min);
        this.picker.setMaxValue(max);
    }
    
    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }

    @Override
    public String getFormatTemplate() {
        return "LLLL";
    }
}