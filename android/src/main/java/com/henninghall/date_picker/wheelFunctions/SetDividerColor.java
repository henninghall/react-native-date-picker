package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.Wheel;

public class SetDividerColor implements WheelFunction {
    private final String color;

    public SetDividerColor(String color) {
        this.color = color;
    }

    @Override
    public void apply(Wheel wheel) {
        wheel.setDividerColor(color);
    }
}
