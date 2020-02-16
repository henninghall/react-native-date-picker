package com.henninghall.date_picker.ui;

import com.henninghall.date_picker.wheels.Wheel;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class WheelScroller {

    public void scroll(Wheel wheel, int scrollTimes) {
        NumberPickerView picker = wheel.picker;
        int currentIndex = picker.getValue();
        int maxValue = picker.getMaxValue();
        boolean isWrapping = picker.getWrapSelectorWheel();
        int nextValue = currentIndex + scrollTimes;
        if(nextValue <= maxValue || isWrapping) {
            picker.smoothScrollToValue(nextValue % (maxValue + 1));
        }
    }
}
