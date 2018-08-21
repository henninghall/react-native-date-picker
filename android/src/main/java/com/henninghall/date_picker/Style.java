package com.henninghall.date_picker;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;


class Style {
    private final GradientDrawable gradientBottom;
    private final GradientDrawable gradientTop;

    public Style(PickerView pickerView) {
        ImageView overlayTop = (ImageView) pickerView.findViewById(R.id.overlay_top);
        ImageView overlayBottom = (ImageView) pickerView.findViewById(R.id.overlay_bottom);
        this.gradientTop =  (GradientDrawable) overlayTop.getDrawable();
        this.gradientBottom =  (GradientDrawable) overlayBottom.getDrawable();
    }

    public void setFadeToColor(String color) {
        int alpha = validColor(color) ? 255 : 0;
        gradientTop.setAlpha(alpha);
        gradientBottom.setAlpha(alpha);
        if(validColor(color)) {
            int startColor = Color.parseColor("#FF"+ color.substring(1));
            int endColor = Color.parseColor("#00" + color.substring(1));
            gradientTop.setColors(new int[] {startColor, endColor});
            gradientBottom.setColors(new int[] {startColor, endColor});
        }
    }

    private boolean validColor(String color){
        return color != null && color.length() == 7;
    }

}
