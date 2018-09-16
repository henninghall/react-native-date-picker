package com.henninghall.date_picker.wheels;

import android.view.View;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.R;

import org.apache.commons.lang3.LocaleUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public abstract class Wheel {

    private final Wheel self;
    public final int id;
    public PickerView pickerView;
    private String userSetValue;

    abstract void init();
    public abstract boolean visible();
    public abstract String getFormatTemplate();

    ArrayList<String> values;
    ArrayList<String> displayValues;
    public NumberPickerView picker;
    public SimpleDateFormat format;
    SimpleDateFormat displayFormat;

    public Wheel(final PickerView pickerView, final int id) {
        this.id = id;
        this.self = this;
        this.pickerView = pickerView;
        this.picker = pickerView.findViewById(id);
        clearValues();
        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                pickerView.getListener().onChange(self);
            }
        });
    }

    private void clearValues(){
        this.displayFormat = new SimpleDateFormat(getFormatTemplate(), pickerView.locale);
        this.format = new SimpleDateFormat(getFormatTemplate(), LocaleUtils.toLocale("en_US"));
        this.values = new ArrayList<>();
        this.displayValues= new ArrayList<>();
    }

    public int getIndexOfDate(Date date){
        return values.indexOf(format.format(date));
    }

    public void animateToDate(Date date) {
        picker.smoothScrollToValue(getIndexOfDate(date));
    }

    public String getValue() {
        if(!visible()) return userSetValue;
        return getValueAtIndex(getIndex());
    }

    public int getIndex() {
        return picker.getValue();
    }

    public String getValueAtIndex(int index) {
        return values.get(index);
    }

    public void setValue(Date date) {
        this.userSetValue = format.format(date);
        int index = getIndexOfDate(date);

        if(index > -1) {
            // Set value directly during initializing. After init, always smooth scroll to value
            if(picker.getValue() == 0) picker.setValue(index);
            else picker.smoothScrollToValue(index);
        }
    }

    public void refresh() {
        clearValues();
        init();
    }

    public void updateVisibility(){
        int visibility = visible() ? View.VISIBLE: View.GONE;
        picker.setVisibility(visibility);
    }

}
