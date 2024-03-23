package com.henninghall.date_picker.pickers;

import android.graphics.Paint;
import android.view.View;

public interface Picker {
    void setTextAlign(Paint.Align align);
    void smoothScrollToValue(int value);
    void smoothScrollToValue(int value, boolean needRespond);
    void setMaxValue(int value);
    void setMinValue(int value);
    int getMaxValue();
    boolean getWrapSelectorWheel();
    void setDisplayedValues(String[] value);
    String[] getDisplayedValues();
    int getValue();
    void setValue(int value);
    void setTextColor(String value);
    void setOnValueChangeListenerInScrolling(Picker.OnValueChangeListenerInScrolling listener);
    void setOnValueChangedListener(Picker.OnValueChangeListener onValueChangeListener);
    View getView();
    void setVisibility(int visibility);
    void setWrapSelectorWheel(boolean wrapSelectorWheel);
    boolean isSpinning();
    void setDividerColor(String color);
    interface OnValueChangeListenerInScrolling {
        void onValueChangeInScrolling(Picker picker, int oldVal, int newVal);
    }

    interface OnValueChangeListener {
        void onValueChange();
        void onSpinnerStateChange();
    }
}
