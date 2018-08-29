package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

public class Refresh implements WheelFunction {

    private final boolean keepOldValue;

    public Refresh() {
        this.keepOldValue = true;
    }

    public Refresh(boolean keepOldValue){
        this.keepOldValue = keepOldValue;
    }

    @Override
    public void apply(Wheel wheel) {
        wheel.refresh(keepOldValue);
    }
}


