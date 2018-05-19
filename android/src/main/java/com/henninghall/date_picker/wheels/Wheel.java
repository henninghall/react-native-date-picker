package com.henninghall.date_picker.wheels;

import android.view.View;

import com.henninghall.date_picker.WheelChangeListener;

import org.apache.commons.lang3.LocaleUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public abstract class Wheel {

    abstract void init();
    abstract boolean visible();
    abstract String getFormatTemplate();

    ArrayList<String> values;
    ArrayList<String> displayValues;
    NumberPickerView picker;
    Locale locale;
    SimpleDateFormat format;
    SimpleDateFormat displayFormat;



    Wheel(NumberPickerView picker, final WheelChangeListener listener, Locale locale){
        this.locale = locale;
        this.picker = picker;

        refresh();

        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                listener.onChange();
            }
        });

    }

    public String getValue() {
        if(!visible()) return "";
        return values.get(picker.getValue());
    }

    public void setValue(Date date) {
        int index = values.indexOf(format.format(date));
        if(index > -1) {

            // Set value directly during initializing
            // After init, always smooth scroll to value
            if(picker.getValue() == 0) picker.setValue(index);
            else picker.smoothScrollToValue(index);
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        refresh();
    }

    private void refresh() {
        this.displayFormat = new SimpleDateFormat(getFormatTemplate(), locale);
        this.format = new SimpleDateFormat(getFormatTemplate(), LocaleUtils.toLocale("en_US"));
        this.values = new ArrayList<>();
        this.displayValues= new ArrayList<>();

        if (visible()) {
            add();
            init();
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
