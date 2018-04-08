package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.WheelChangeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public abstract class Wheel {

    abstract void init();

    private String formatTemplate;
    ArrayList<String> values;
    NumberPickerView picker;
    Locale locale;
    SimpleDateFormat format;
    Calendar cal = Calendar.getInstance();


    Wheel(NumberPickerView picker, final WheelChangeListener listener, String formatTemplate, Locale locale){
        this.formatTemplate = formatTemplate;
        this.locale = locale;
        this.picker = picker;
        values = new ArrayList<>();
        format = new SimpleDateFormat(formatTemplate, locale);

        init();
        picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                listener.onChange();
            }
        });

    }

    public String getFormatTemplate() {
        return formatTemplate;
    }

    public String getValue() {
        return values.get(picker.getValue());
    }

    public void setValue(Date date) {
        picker.setValue(values.indexOf(format.format(date)));
    }

    public void updateLocale(Locale locale) {
        this.locale = locale;
        init();
    }

}
