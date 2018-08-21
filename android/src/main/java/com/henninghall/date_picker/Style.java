package com.henninghall.date_picker;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.facebook.react.bridge.ReadableMap;


class Style {
    private final GradientDrawable gradient;

    public Style(PickerView pickerView) {
        ImageView overlayTop = (ImageView) pickerView.findViewById(R.id.overlay_top);
        this.gradient =  (GradientDrawable) overlayTop.getDrawable();
    }

    public void set(ReadableMap style) {
        String color = style.getString("backgroundColor");
        if(color != null && color.length() == 7) {
            int startColor = Color.parseColor("#FF"+ color.substring(1));
            int endColor = Color.parseColor("#00" + color.substring(1));
            gradient.setColors(new int[] {startColor, endColor});
        }
    }
}
