package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.R;
import com.henninghall.date_picker.WheelChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class DayWheel extends Wheel {

    public DayWheel(NumberPickerView p, WheelChangeListener listener, Locale locale){
        super(p, listener, locale);
    }

    @Override
    void init() {

        int min = 0;
        int max = 10000; // bug
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5000);


        for(int i=0; i<=(max-min); i++){
            values.add(format.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }


        ArrayList<String> displayValues = new ArrayList<>();
        for (String s : values) displayValues.add(s.substring(3));
        picker.setDisplayedValues(displayValues.toArray(new String[0]));

        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setValue(5000);

    }

    @Override
    public String getFormatTemplate() {
        return "yy EEE d MMM";
    }


}
