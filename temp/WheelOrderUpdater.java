package com.henninghall.date_picker;

import com.henninghall.date_picker.wheels.*;
import java.util.*;
import android.widget.*;
import android.os.*;
import android.view.*;

public class WheelOrderUpdater
{
    private final PickerView pickerView;
    
    WheelOrderUpdater(final PickerView v) {
        this.pickerView = v;
    }
    
    public void update(final Locale locale, final Mode mode) {
        if (mode != Mode.date) {
            return;
        }
        final String ymdPattern = Utils.localeToYmdPattern(locale);
        final ArrayList<Wheel> wheelOrder = this.ymdPatternToWheelOrder(ymdPattern);
        this.placeWheelRightOf(wheelOrder.get(0), wheelOrder.get(1));
        this.placeWheelRightOf(wheelOrder.get(1), wheelOrder.get(2));
    }
    
    private void placeWheelRightOf(final Wheel leftWheel, final Wheel rightWheel) {
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, Utils.getWheelHeight((View)this.pickerView));
        params.addRule(1, leftWheel.id);
        if (Build.VERSION.SDK_INT >= 17) {
            params.addRule(17, leftWheel.id);
        }
        rightWheel.picker.setLayoutParams((ViewGroup.LayoutParams)params);
    }
    
    private ArrayList<Wheel> ymdPatternToWheelOrder(final String ymdPattern) {
        final String[] parts = ymdPattern.split("/");
        final ArrayList<Wheel> wheelList = new ArrayList<Wheel>();
        for (final String s : parts) {
            switch (s.charAt(0)) {
                case 'y': {
                    wheelList.add(this.pickerView.yearWheel);
                }
                case 'M': {
                    wheelList.add(this.pickerView.monthWheel);
                }
                case 'd': {
                    wheelList.add(this.pickerView.dateWheel);
                    break;
                }
            }
        }
        return wheelList;
    }
}

