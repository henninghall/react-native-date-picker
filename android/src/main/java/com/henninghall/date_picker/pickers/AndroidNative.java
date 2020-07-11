package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;

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
    public void smoothScrollToValue(int value) {
        setValue(value);
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
                if (stoppedScrolling) listener.onValueChange();
                previousState = state;
            }
        });
    }


    @Override
    public void setShownCount(int count) {
        // always 3 date rows -> nothing needs to be done here
    }

    @Override
    public View getView() {
        return this;
    }
}
