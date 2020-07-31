package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

import java.util.Calendar;

public class HorizontalPadding implements WheelFunction {

    @Override
    public void apply(Wheel wheel) {
        wheel.setHorizontalPadding();
    }
}


