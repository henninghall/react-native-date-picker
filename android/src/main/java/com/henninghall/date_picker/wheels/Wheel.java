package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import android.view.View;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import com.henninghall.date_picker.PickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public abstract class Wheel {

    private final Wheel self;
    public final int id;
    public PickerView pickerView;
    private Calendar userSetValue;

    public abstract boolean visible();
    public abstract Paint.Align getTextAlign();
    public abstract String getFormatPattern();
    public abstract ArrayList<String> getValues();

    public String toDisplayValue(String value) {
        return value;
    }

    ArrayList<String> values = new ArrayList<>();
    public NumberPickerView picker;
    public SimpleDateFormat format;

    public Wheel(final PickerView pickerView, final int id) {
        this.id = id;
        this.self = this;
        this.pickerView = pickerView;
        this.picker = (NumberPickerView) pickerView.findViewById(id);
        this.format = new SimpleDateFormat(getFormatPattern(), pickerView.locale);
        picker.setTextAlign(getTextAlign());
        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                pickerView.getListener().onChange(self);
            }
        });
    }

    private int getIndexOfDate(Calendar date){
        format.setTimeZone(pickerView.timeZone);
        return values.indexOf(format.format(date.getTime()));
    }

    public void animateToDate(Calendar date) {
        picker.smoothScrollToValue(getIndexOfDate(date));
    }

    public String getValue() {
        if(!visible()) return format.format(userSetValue.getTime());
        return getValueAtIndex(getIndex());
    }

    private int getIndex() {
        return picker.getValue();
    }

    public String getValueAtIndex(int index) {
        return values.get(index);
    }

    public void setValue(Calendar date) {
        format.setTimeZone(pickerView.timeZone);
        this.userSetValue = date;
        int index = getIndexOfDate(date);

        if(index > -1) {
            // Set value directly during initializing. After init, always smooth scroll to value
            if(picker.getValue() == 0) picker.setValue(index);
            else picker.smoothScrollToValue(index);
        }
    }

    public void refresh() {
        this.format = new SimpleDateFormat(getFormatPattern(), pickerView.locale);
        if (!this.visible()) return;
        init();
    }

    private String[] getDisplayValues(ArrayList<String> values){
        ArrayList<String> displayValues = new ArrayList<>();
        for (String value: values) {
            displayValues.add(this.toDisplayValue(value));
        }
        return displayValues.toArray(new String[0]);
    }

    private void init(){
        picker.setMinValue(0);
        picker.setMaxValue(0);
        values = getValues();
        picker.setDisplayedValues(getDisplayValues(values));
        picker.setMaxValue(values.size() -1);
    }

    public void updateVisibility(){
        int visibility = visible() ? View.VISIBLE: View.GONE;
        picker.setVisibility(visibility);
    }

    private SimpleDateFormat getFormat(Locale locale) {
        return new SimpleDateFormat(this.getFormatPattern(), locale);
    }

    String getLocaleString(Calendar cal) {
        return getString(cal, this.pickerView.locale);
    }

    private String getString(Calendar cal, Locale locale){
        return getFormat(locale).format(cal.getTime());
    }

}
