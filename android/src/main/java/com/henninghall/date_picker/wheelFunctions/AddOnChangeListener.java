package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.ui.WheelChangeListener;
import com.henninghall.date_picker.wheels.Wheel;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class AddOnChangeListener implements WheelFunction {

    private final WheelChangeListener onChangeListener;

    public AddOnChangeListener(WheelChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    @Override
    public void apply(final Wheel wheel) {
        wheel.picker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                onChangeListener.onChange(wheel);
            }
        });
    }
}


