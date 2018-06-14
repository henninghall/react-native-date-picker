package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

import java.util.Date;

public class SetDate implements WheelFunction {

    private Date date;

    public SetDate(Date date) {
        this.date = date;
    }

    @Override
    public void apply(Wheel wheel) {
        wheel.setValue(date);
    }
}


