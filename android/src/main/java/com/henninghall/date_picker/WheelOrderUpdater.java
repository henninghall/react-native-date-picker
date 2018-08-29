package com.henninghall.date_picker;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.henninghall.date_picker.wheels.Wheel;

import java.util.ArrayList;
import java.util.Locale;

public class WheelOrderUpdater
{
    private final PickerView pickerView;
    private String ymdPattern = "";

    WheelOrderUpdater(final PickerView v) {
        this.pickerView = v;
    }
    
    public void update(final Locale locale, final Mode mode) {

        if (mode != Mode.date) {
            return;
        }
        String lastYmdPattern = ymdPattern;
        ymdPattern = Utils.localeToYmdPattern(locale);
        if(lastYmdPattern.equals(ymdPattern)) return;
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
                    break;
                }
                case 'M': {
                    wheelList.add(this.pickerView.monthWheel);
                    break;
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

