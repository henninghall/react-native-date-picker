package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.WheelChangeListener;

import java.util.Calendar;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class DayWheel extends Wheel {

    public DayWheel(NumberPickerView dayPicker, PickerView pickerView) {
        super(dayPicker, pickerView);
    }

    @Override
    void init() {

        int min = 0;
        int max = 10000; // bug
        Calendar cal = pickerView.getInitialDate();
        cal.add(Calendar.DAY_OF_MONTH, -max/2);


        for(int i=0; i<=(max-min); i++){
            values.add(format.format(cal.getTime()));

            // Print "today" if date is today
            if(i == max/2){
                String todayString = Utils.printToday(pickerView.locale);
                String todayStringCapitalized = todayString .substring(0, 1).toUpperCase() + todayString.substring(1);
                displayValues.add(todayStringCapitalized);
            }
            else displayValues.add(displayFormat.format(cal.getTime()).substring(3));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        picker.setDisplayedValues(displayValues.toArray(new String[0]));

        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setValue(5000);
    }

    @Override
    public boolean visible() {
        return pickerView.mode == Mode.datetime;
    }

    @Override
    public String getFormatTemplate() {
        return "yy EEE d MMM";
    }


}
