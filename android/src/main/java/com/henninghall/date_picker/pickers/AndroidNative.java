package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

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
        // TODO
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
    public void setOnValueChangeListenerInScrolling(OnValueChangeListenerInScrolling listener) {

    }

    @Override
    public void setOnValueChangedListener(final Picker.OnValueChangeListener listener) {
        super.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int from, int to) {
                listener.onValueChange();
            }
        });
    }


    @Override
    public void setShownCount(int count) {
    }

    @Override
    public View getView() {
        return this;
    }
}
