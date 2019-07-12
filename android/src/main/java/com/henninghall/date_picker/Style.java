package com.henninghall.date_picker;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.henninghall.date_picker.wheelFunctions.SetShowCount;
import com.henninghall.date_picker.wheelFunctions.TextColor;


class Style {
    private static int DP_PER_SHOW_SHOW_COUNT = 35;

    private final GradientDrawable gradientBottom;
    private final GradientDrawable gradientTop;
    private final PickerView pickerView;

    public Style(PickerView pickerView) {
        this.pickerView = pickerView;
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

    public void setTextColor(String color) {
        this.pickerView.applyOnAllWheels(new TextColor(color));
    }

    public void setHeight(int height) {
        int showCount = height / DP_PER_SHOW_SHOW_COUNT;
        int oddShowCount = showCount % 2 == 0 ? showCount + 1 : showCount;
        pickerView.applyOnAllWheels(new SetShowCount(oddShowCount));
        pickerView.setShownCountOnEmptyWheels(oddShowCount);
    }

    private boolean validColor(String color){
        return color != null && color.length() == 7;
    }

}
