package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.*;
import cn.carbswang.android.numberpickerview.library.*;
import java.text.*;
import java.util.*;
import org.apache.commons.lang3.*;

public abstract class Wheel
{
    private final Wheel self;
    public PickerView pickerView;
    private String userSetValue;
    ArrayList<String> values;
    ArrayList<String> displayValues;
    public NumberPickerView picker;
    public SimpleDateFormat format;
    SimpleDateFormat displayFormat;
    
    abstract void init();
    
    public abstract boolean visible();
    
    abstract String getFormatTemplate();
    
    public Wheel(final NumberPickerView picker, final PickerView pickerView) {
        this.self = this;
        this.pickerView = pickerView;
        this.picker = picker;
        this.refresh(false);
        picker.setOnValueChangedListener((NumberPickerView.OnValueChangeListener)new NumberPickerView.OnValueChangeListener() {
            public void onValueChange(final NumberPickerView picker, final int oldVal, final int newVal) {
                pickerView.getListener().onChange(Wheel.this.self);
            }
        });
    }
    
    public int getIndexOfDate(final Date date) {
        return this.values.indexOf(this.format.format(date));
    }
    
    public void animateToDate(final Date date) {
        this.picker.smoothScrollToValue(this.getIndexOfDate(date));
    }
    
    public String getValue() {
        if (!this.visible()) {
            return this.userSetValue;
        }
        return this.getValueAtIndex(this.getIndex());
    }
    
    public int getIndex() {
        return this.picker.getValue();
    }
    
    public String getValueAtIndex(final int index) {
        return this.values.get(index);
    }
    
    public void setValue(final Date date) {
        this.userSetValue = this.format.format(date);
        final int index = this.getIndexOfDate(date);
        if (index > -1) {
            if (this.picker.getValue() == 0) {
                this.picker.setValue(index);
            }
            else {
                this.picker.smoothScrollToValue(index);
            }
        }
    }
    
    public void refresh(final boolean keepOldValue) {
        this.displayFormat = new SimpleDateFormat(this.getFormatTemplate(), this.pickerView.locale);
        this.format = new SimpleDateFormat(this.getFormatTemplate(), LocaleUtils.toLocale("en_US"));
        this.values = new ArrayList<String>();
        this.displayValues = new ArrayList<String>();
        final int oldValue = this.picker.getValue();
        if (this.visible()) {
            this.add();
            this.init();
            if (keepOldValue) {
                this.picker.setValue(oldValue);
            }
        }
        else {
            this.remove();
        }
    }
    
    private void remove() {
        this.picker.setVisibility(8);
    }
    
    private void add() {
        this.picker.setVisibility(0);
    }
}

