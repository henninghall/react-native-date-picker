package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

public class SetDividerHeight implements WheelFunction {
    private final int height;

    public SetDividerHeight(int height) {
        this.height = height;
    }

    @Override
    public void apply(Wheel wheel) {
        wheel.picker.setDividerHeight(height);
    }
}
