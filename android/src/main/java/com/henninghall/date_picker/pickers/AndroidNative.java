package com.henninghall.date_picker.pickers;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;

public class AndroidNative extends NumberPicker implements Picker {

    private Picker.OnValueChangeListener onValueChangedListener;
    private int state = SCROLL_STATE_IDLE;
    private OnValueChangeListenerInScrolling listenerInScrolling;

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
    public void setNormalTextColor(int color) {
        // Not needed for this picker. It auto fades the color
    }

    @Override
    public void setSelectedTextColor(int color) {
        try {
            Field selectorWheelPaintField = getClass().getSuperclass()
                    .getDeclaredField("mSelectorWheelPaint");
            selectorWheelPaintField.setAccessible(true);
            ((Paint) selectorWheelPaintField.get(this)).setColor(color);
        } catch (NoSuchFieldException e) {
            Log.w("setSelectedTextColor", e);
        } catch (IllegalAccessException e) {
            Log.w("setSelectedTextColor", e);
        } catch (IllegalArgumentException e) {
            Log.w("setSelectedTextColor", e);
        }

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof EditText)
                ((EditText) child).setTextColor(color);
        }
        invalidate();
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
    public void setDividerHeight(int height) {
        // not supported
    }
    
    @Override
    public void setItemPaddingHorizontal(int padding) {
        // Not needed for this picker
    }

    @Override
    public boolean isSpinning() {
        return state == SCROLL_STATE_FLING;
    }

    @Override
    public void smoothScrollToValue(final int value) {
        final AndroidNative self = this;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                int currentValue = self.getValue();
                if (value == currentValue) return;
                int shortestScrollOption = getShortestScrollOption(currentValue, value);
                final int moves = Math.abs(shortestScrollOption);
                for (int i = 0; i < moves; i++) {
                    // need some delay between each scroll step to make sure it scrolls to correct value
                    changeValueByOne(shortestScrollOption > 0, i * 100, i == moves - 1);
                }
            }
            // since the SCROLL_STATE_IDLE event is dispatched before the wheel actually has stopped
            // an extra delay has to be added before starting to scroll to correct value
        }, 500);
    }

    private int getShortestScrollOption(int currentValue, int value) {
        final int maxValue = getMaxValue();
        int option1 = value - currentValue;
        int option2 = maxValue + 1 - Math.abs(option1);
        if (getWrapSelectorWheel()) {
            return Math.abs(option1) < Math.abs(option2) ? option1 : option2;
        }
        if (currentValue + option1 > maxValue) return option2;
        if (currentValue + option1 < 0) return option2;
        return option1;
    }

    private void changeValueByOne(final NumberPicker higherPicker, final boolean increment) {
        boolean success = false;
        try {
            Method method = getClass().getSuperclass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(higherPicker, increment);
            success = true;
        } catch (final NoSuchMethodException e) {
            Log.w("changeValueByOne", e);
        } catch (final IllegalArgumentException e) {
            Log.w("changeValueByOne", e);
        } catch (final IllegalAccessException e) {
            Log.w("changeValueByOne", e);
        } catch (final InvocationTargetException e) {
            Log.w("changeValueByOne", e);
        } finally {
            if (!success) {
                // make step without animation if failed to use reflection method
                setValue((getValue() + (increment ? 1 : -1)) % getMaxValue());
            }
        }
    }

    private void changeValueByOne(final boolean increment, final int ms, final boolean isLast) {
        final AndroidNative self = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changeValueByOne(self, increment);
                if (isLast) {
                    sendEventIn500ms();
                }
            }
        }, ms);
    }

    @Override
    public void setOnValueChangeListenerInScrolling(final OnValueChangeListenerInScrolling listener) {
        listenerInScrolling = listener;
    }

    @Override
    public void setOnValueChangedListener(final Picker.OnValueChangeListener listener) {
        this.onValueChangedListener = listener;
        final Picker self = this;

        super.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                if(listenerInScrolling != null) {
                    listenerInScrolling.onValueChangeInScrolling(self, oldVal, newVal);
                }
                // onValueChange is triggered also during scrolling. Since we don't want
                // to send event during scrolling we make sure wheel is still. This particular
                // case happens when wheel is tapped, not scrolled.
                if(state == SCROLL_STATE_IDLE) {
                  sendEventIn500ms();
                }
            }
        });

        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int nextState) {
               sendEventIfStopped(nextState);
               state = nextState;
            }
        });
    }

    private void sendEventIfStopped(int nextState){
        boolean stoppedScrolling = state != SCROLL_STATE_IDLE && nextState == SCROLL_STATE_IDLE;
        if (stoppedScrolling) {
            sendEventIn500ms();
        }
    }

    private void sendEventIn500ms(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onValueChangedListener.onValueChange();
            }
            // the delay make sure the wheel has stopped before sending the value change event
        }, 500);
    }

}
