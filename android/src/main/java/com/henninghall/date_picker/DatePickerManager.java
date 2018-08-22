package com.henninghall.date_picker;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import net.time4j.android.ApplicationStarter;
import org.apache.commons.lang3.LocaleUtils;

import java.util.Map;

public class DatePickerManager extends SimpleViewManager<View>  {

  public static final String REACT_CLASS = "DatePickerManager";
  public static ThemedReactContext context;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public PickerView createViewInstance(ThemedReactContext reactContext) {
    DatePickerManager.context = reactContext;
    ApplicationStarter.initialize(reactContext, true); // with prefetch on background thread
    return new PickerView();
  }

  @ReactProp(name = "mode")
  public void setMode(PickerView view, @Nullable String mode) {
    try {
      view.setMode(Mode.valueOf(mode));
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid mode. Valid modes: 'datetime', 'date', 'time'");
    }

  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, @Nullable double date) {
       view.setDate(Utils.unixToDate(date));
  }

  @ReactProp(name = "locale")
  public void setLocale(PickerView view, @Nullable String locale) {
    view.setLocale(LocaleUtils.toLocale(locale.replace('-','_')));
    view.requestLayout();
  }

  @ReactProp(name = "minimumDate")
  public void setMinimumDate(PickerView view, @Nullable double date) {
    view.setMinimumDate(Utils.unixToDate(date));
  }

  @ReactProp(name = "maximumDate")
  public void setMaximumDate(PickerView view, @Nullable double date) {
    view.setMaximumDate(Utils.unixToDate(date));
  }

  @ReactProp(name = "fadeToColor")
  public void setFadeToColor(PickerView view, @Nullable String color) {
    view.style.setFadeToColor(color);
  }

  @ReactProp(name = "textColor")
  public void setTextColor(PickerView view, @Nullable String color) {
    view.style.setTextColor(color);
  }

  @ReactProp(name = "minuteInterval")
  public void setMinuteInterval(PickerView view, @Nullable int interval) throws Exception {
    if (interval < 0 || interval > 59) throw new Exception("Minute interval out of bounds");
    if (interval > 1) view.setMinuteInterval(interval);
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

}


