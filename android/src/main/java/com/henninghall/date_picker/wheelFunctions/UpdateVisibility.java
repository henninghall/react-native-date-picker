package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

public class UpdateVisibility implements WheelFunction {

    @Override
    public void apply(Wheel wheel) {
       wheel.updateVisibility();
    }
}


