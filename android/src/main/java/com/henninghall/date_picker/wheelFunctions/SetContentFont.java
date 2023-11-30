package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

public class SetContentFont implements WheelFunction {
    private final String fontPath;

    public SetContentFont(String fontPath) {
        this.fontPath = fontPath;
    }

    @Override
    public void apply(Wheel wheel) {
        wheel.picker.setContentFont(fontPath);
    }
}
