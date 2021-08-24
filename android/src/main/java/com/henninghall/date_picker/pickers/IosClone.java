package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import android.content.Context;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class IosClone extends NumberPickerView implements Picker {

    private AccessibilityManager mAccessibilityManager;

    private void initAccessibilityManager(Context context) {
        Context appContext = context.getApplicationContext();
        mAccessibilityManager = (AccessibilityManager) appContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public IosClone(Context context) {
        super(context);
        initAccessibilityManager(context);
    }

    public IosClone(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAccessibilityManager(context);
    }

    public IosClone(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAccessibilityManager(context);
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
        final Picker self = this;
        super.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                listener.onValueChangeInScrolling(self, oldVal, newVal);
            }
        });
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
        /*
            When TalkBack is active, user can use one finger to explore the screen
            and set focus to elements. Then user can proceed to use second finger
            to scroll contents of focused element.
            When there's multiple pickers next to each other horizontally,
            it's easy to accidentally scroll more than one picker at a time.
            Following code aims to fix this issue.
        */

        // If TalkBack isn't active, always proceed without suppressing touch events
        if (!mAccessibilityManager.isTouchExplorationEnabled()) {
            super.onTouchEvent(event);
            return true;
        }

        if (this.isAccessibilityFocused()) {
            super.onTouchEvent(event);
            return true;
        }

        return false;
    }
}
