package com.henninghall.date_picker.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;

public class FadingOverlay {

    private final GradientDrawable gradientTop;
    private final GradientDrawable gradientBottom;
    private final State state;

    FadingOverlay(State state, View rootView) {
        this.state = state;
        ImageView overlayTop = (ImageView) rootView.findViewById(R.id.overlay_top);
        ImageView overlayBottom = (ImageView) rootView.findViewById(R.id.overlay_bottom);
        gradientTop =  (GradientDrawable) overlayTop.getDrawable();
        gradientBottom =  (GradientDrawable) overlayBottom.getDrawable();
    }

    void updateColor(){
        String color = state.getFadeToColor();
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
