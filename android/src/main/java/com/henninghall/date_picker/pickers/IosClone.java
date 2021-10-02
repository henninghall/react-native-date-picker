package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

import com.henninghall.date_picker.ui.Accessibility;


public class IosClone extends NumberPickerView implements Picker {
    private Picker.OnValueChangeListenerInScrolling mOnValueChangeListenerInScrolling;

    public IosClone(Context context) {
        super(context);
        init();
    }

    public IosClone(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IosClone(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initAccessibility();
        initSetOnValueChangeListenerInScrolling();
    }

    private void initAccessibility() {
        Accessibility.startAccessibilityDelegate(this);
    }

    private void initSetOnValueChangeListenerInScrolling() {
        final Picker self = this;

        super.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                Accessibility.sendValueChangedEvent(self, newVal);

                if (mOnValueChangeListenerInScrolling != null) {
                    mOnValueChangeListenerInScrolling.onValueChangeInScrolling(self, oldVal, newVal);
                }
            }
        });
    }

    @Override
    public void setTextColor(String color) {
        int fullColor= Color.parseColor(color);
        int fadedColor = Color.parseColor("#70"+ color.substring(1));
        setNormalTextColor(fadedColor);
        setSelectedTextColor(fullColor);
    }

    @Override
    public void setOnValueChangeListenerInScrolling(final Picker.OnValueChangeListenerInScrolling listener) {
        this.mOnValueChangeListenerInScrolling = listener;
    }

    @Override
    public void setOnValueChangedListener(final Picker.OnValueChangeListener listener) {
        super.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                listener.onValueChange();
            }
        });
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isSpinning() {
        return super.isScrolling();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(Accessibility.shouldAllowScroll(this)){
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        // Set the accessibility properties of this view
        Accessibility.setRoleToSlider(this, info);
    }
}
