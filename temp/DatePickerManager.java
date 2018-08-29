package com.henninghall.date_picker;

import android.view.*;
import com.facebook.react.uimanager.*;
import net.time4j.android.*;
import android.content.*;
import android.support.annotation.*;
import com.facebook.react.uimanager.annotations.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.facebook.react.common.*;

public class DatePickerManager extends SimpleViewManager<View>
{
    public static final String REACT_CLASS = "DatePickerManager";
    public static ThemedReactContext context;
    
    public String getName() {
        return "DatePickerManager";
    }
    
    public PickerView createViewInstance(final ThemedReactContext reactContext) {
        ApplicationStarter.initialize((Context)(DatePickerManager.context = reactContext), true);
        return new PickerView();
    }
    
    @ReactProp(name = "mode")
    public void setMode(final PickerView view, @Nullable final String mode) {
        try {
            view.setMode(Mode.valueOf(mode));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid mode. Valid modes: 'datetime', 'date', 'time'");
        }
    }
    
    @ReactProp(name = "date")
    public void setDate(final PickerView view, @Nullable final double date) {
        view.setDate(Utils.unixToDate(date));
    }
    
    @ReactProp(name = "locale")
    public void setLocale(final PickerView view, @Nullable final String locale) {
        view.setLocale(LocaleUtils.toLocale(locale.replace('-', '_')));
        view.requestLayout();
    }
    
    @ReactProp(name = "minimumDate")
    public void setMinimumDate(final PickerView view, @Nullable final double date) {
        view.setMinimumDate(Utils.unixToDate(date));
    }
    
    @ReactProp(name = "maximumDate")
    public void setMaximumDate(final PickerView view, @Nullable final double date) {
        view.setMaximumDate(Utils.unixToDate(date));
    }
    
    @ReactProp(name = "fadeToColor")
    public void setFadeToColor(final PickerView view, @Nullable final String color) {
        view.style.setFadeToColor(color);
    }
    
    @ReactProp(name = "textColor")
    public void setTextColor(final PickerView view, @Nullable final String color) {
        view.style.setTextColor(color);
    }
    
    @ReactProp(name = "minuteInterval")
    public void setMinuteInterval(final PickerView view, @Nullable final int interval) throws Exception {
        if (interval < 0 || interval > 59) {
            throw new Exception("Minute interval out of bounds");
        }
        if (interval > 1) {
            view.setMinuteInterval(interval);
        }
    }
    
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder().put((Object)"dateChange", (Object)MapBuilder.of((Object)"phasedRegistrationNames", (Object)MapBuilder.of((Object)"bubbled", (Object)"onChange"))).build();
    }
}

