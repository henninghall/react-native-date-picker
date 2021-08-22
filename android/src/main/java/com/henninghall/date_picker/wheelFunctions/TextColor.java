package com.henninghall.date_picker.wheelFunctions;

import android.graphics.Color;

import com.henninghall.date_picker.wheels.Wheel;

public class TextColor implements WheelFunction {

    private final String color;

    public TextColor(String color) {
        this.color = color;
    }

    @Override
    public void apply(Wheel wheel) {
        wheel.picker.setTextColor(color);
    }
}


