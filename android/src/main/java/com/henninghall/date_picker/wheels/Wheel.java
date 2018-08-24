package com.henninghall.date_picker.wheels;

import android.view.View;
import com.henninghall.date_picker.PickerView;
import org.apache.commons.lang3.LocaleUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public abstract class Wheel {

    private final Wheel self;
    public PickerView pickerView;
    abstract void init();
    public abstract boolean visible();
    abstract String getFormatTemplate();

    ArrayList<String> values;
    ArrayList<String> displayValues;
    public NumberPickerView picker;
    public SimpleDateFormat format;
    SimpleDateFormat displayFormat;

    public Wheel(NumberPickerView picker, final PickerView pickerView) {
        this.self = this;
        this.pickerView = pickerView;
        this.picker = picker;
        refresh(false);
        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                pickerView.getListener().onChange(self);
            }
        });
    }

    public int getIndexOfDate(Date date){
        return values.indexOf(format.format(date));
    }

    public void animateToDate(Date date) {
        picker.smoothScrollToValue(getIndexOfDate(date));
    }

    public String getValue() {
        if(!visible()) return "";
        return getValueAtIndex(getIndex());
    }

    public int getIndex() {
        return picker.getValue();
    }

    public String getValueAtIndex(int index) {
        return values.get(index);
    }

    public void setValue(Date date) {
        int index = getIndexOfDate(date);
        if(index > -1) {

            // Set value directly during initializing
            // After init, always smooth scroll to value
            if(picker.getValue() == 0) picker.setValue(index);
            else picker.smoothScrollToValue(index);
        }
    }

    public void refresh(boolean keepOldValue) {
        this.displayFormat = new SimpleDateFormat(getFormatTemplate(), pickerView.locale);
        this.format = new SimpleDateFormat(getFormatTemplate(), LocaleUtils.toLocale("en_US"));
        this.values = new ArrayList<>();
        this.displayValues= new ArrayList<>();
        int oldValue = picker.getValue();

        if (visible()) {
            add();
            init();
            if(keepOldValue) picker.setValue(oldValue);

        }
        else remove();

    }

    private void remove() {
        picker.setVisibility(View.GONE);
    }
    private void add() {
        picker.setVisibility(View.VISIBLE);
    }

}
