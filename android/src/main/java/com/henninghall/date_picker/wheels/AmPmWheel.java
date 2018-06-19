package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.Mode;
import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.Utils;
import java.util.Calendar;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class AmPmWheel extends Wheel {

    public AmPmWheel(NumberPickerView ampmPicker, PickerView pickerView) {
        super(ampmPicker, pickerView);
    }

    @Override
    void init() {

        Calendar cal = pickerView.getInitialDate();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        displayValues.add(displayFormat.format(cal.getTime()));
        values.add(format.format(cal.getTime()));

        cal.add(Calendar.HOUR_OF_DAY, 12);
        displayValues.add(displayFormat.format(cal.getTime()));
        values.add(format.format(cal.getTime()));

        picker.setDisplayedValues(displayValues.toArray(new String[0]));

        picker.setMinValue(0);
        picker.setMaxValue(1);
    }

    @Override
    public boolean visible() {
        return Utils.usesAmPm(pickerView.locale) && pickerView.mode != Mode.date;
    }

    @Override
    public String getFormatTemplate() {
        return Utils.usesAmPm(pickerView.locale) ? " a " : "";
    }

}
