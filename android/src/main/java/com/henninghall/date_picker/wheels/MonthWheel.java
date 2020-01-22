package com.henninghall.date_picker.wheels;

import android.graphics.Paint;

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
        for (int i = min; i <= max; ++i) {
            this.values.add(getUsString(cal));
            this.displayValues.add(getLocaleString(cal));
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

    @Override
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }

    private String getUsString(Calendar cal) {
        return getString(cal, Locale.US);
    }

    private String getLocaleString(Calendar cal) {
        return getString(cal, this.pickerView.locale);
    }

    private String getString(Calendar cal, Locale locale){
        return getFormat(locale).format(cal.getTime());
    }

    private SimpleDateFormat getFormat(Locale locale) {
        return new SimpleDateFormat(this.getFormatTemplate(), locale);
    }
}