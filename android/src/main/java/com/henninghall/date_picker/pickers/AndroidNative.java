package com.henninghall.date_picker.pickers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.Method;

public class AndroidNative extends NumberPicker implements Picker {

    public AndroidNative(Context context) {
        super(context);
    }

    public AndroidNative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidNative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextAlign(Paint.Align align) {
    }

    @Override
    public void smoothScrollToValue(int value, boolean needRespond) {
        smoothScrollToValue(value);
    }

    @Override
    public void setNormalTextColor(int value) {

    }

    @Override
    public void setSelectedTextColor(int value) {

    }

    @Override
    public void setShownCount(int count) {
        // always 3 date rows -> nothing needs to be done here
    }

    @Override
    public View getView() {
        return this;
    }


    @Override
    public void smoothScrollToValue(final int value) {
        Handler handler = new Handler();
        final AndroidNative self = this;

        handler.postDelayed(new Runnable() {
            public void run() {
                int currentValue = self.getValue();
                if (value == currentValue) return;
                int shortestScrollOption = getShortestScrollOption(currentValue, value);
                final int moves = Math.abs(shortestScrollOption);
                for (int i = 0; i < moves; i++) {
                    // need some delay between each scroll step to make sure it scrolls to correct value
                    changeValueByOne(shortestScrollOption > 0, i * 100);
                }
            }
            // since the SCROLL_STATE_IDLE event is dispatched before the wheel actually has stopped
            // an extra delay has to be added before starting to scroll to correct value
        }, 500);
    }

    private int getShortestScrollOption(int currentValue, int value){
        final int maxValue = getMaxValue();
        int option1 = value - currentValue;
        int option2 = maxValue + 1 - Math.abs(option1);
        if(getWrapSelectorWheel()){
            return Math.abs(option1) < Math.abs(option2) ? option1 : option2;
        }
        if (currentValue + option1 > maxValue) return option2;
        if (currentValue + option1 < 0) return option2;
        return option1;
    }

    @SuppressLint("PrivateApi")
    private void changeValueByOne(final NumberPicker higherPicker, final boolean increment) {
        try {
            Method method = getClass().getSuperclass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(higherPicker, increment);
        } catch (Exception  e) {
            e.printStackTrace();
            // make step without animation if failed to use reflection method
            setValue((getValue() + (increment ? 1 : -1)) % getMaxValue());
        }
    }

    private void changeValueByOne(final boolean increment, int ms) {
        Handler handler = new Handler();
        final AndroidNative self = this;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeValueByOne(self, increment);
            }
        }, ms);
    }


    @Override
    public void setOnValueChangeListenerInScrolling(final OnValueChangeListenerInScrolling listener) {
        final Picker self = this;
        super.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int from, int to) {
                listener.onValueChangeInScrolling(self, from, to);
            }
        });
    }

    @Override
    public void setOnValueChangedListener(final Picker.OnValueChangeListener listener) {
        super.setOnScrollListener(new OnScrollListener() {
            int previousState = SCROLL_STATE_IDLE;

            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int state) {
                boolean stoppedScrolling = previousState != SCROLL_STATE_IDLE && state == SCROLL_STATE_IDLE;
                if (stoppedScrolling) {
                    listener.onValueChange();
                }
                previousState = state;
            }
        });
    }

}
