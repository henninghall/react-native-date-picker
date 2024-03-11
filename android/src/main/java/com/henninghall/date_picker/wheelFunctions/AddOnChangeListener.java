package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.ui.WheelChangeListener;
import com.henninghall.date_picker.wheels.Wheel;


public class AddOnChangeListener implements WheelFunction {

    private final WheelChangeListener onChangeListener;

    public AddOnChangeListener(WheelChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    @Override
    public void apply(final Wheel wheel) {
        wheel.picker.setOnValueChangedListener(new Picker.OnValueChangeListener() {
            @Override
            public void onValueChange() {
                onChangeListener.onChange(wheel);
            }

            @Override
            public void onSpinnerStateChange() {
                onChangeListener.onStateChange(wheel);
            }
        });
    }
}


