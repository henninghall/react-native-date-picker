package com.henninghall.date_picker.wheels;

import android.view.ViewManager;

import com.henninghall.date_picker.WheelChangeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public abstract class Wheel {

    abstract void init();
    abstract String getFormatTemplate();

    ArrayList<String> values;
    NumberPickerView picker;
    Locale locale;
    SimpleDateFormat format;
    Calendar cal = Calendar.getInstance();


    Wheel(NumberPickerView picker, final WheelChangeListener listener, Locale locale){
        this.locale = locale;
        this.picker = picker;
        this.values = new ArrayList<>();
        this.format = new SimpleDateFormat(getFormatTemplate(), locale);
        refresh();

        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                listener.onChange();
            }
        });

    }

    public String getValue() {
        return values.get(picker.getValue());
    }

    public void setValue(Date date) {
        int index = values.indexOf(format.format(date));
        if(index > -1) picker.setValue(index);
    }

    public void setLocale(Locale locale) {
        this.locale =locale;
        refresh();
    }

    private void refresh() {
        this.format = new SimpleDateFormat(getFormatTemplate(), locale);
        init();
    }

}
