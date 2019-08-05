package com.henninghall.date_picker;

import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;

import net.time4j.android.ApplicationStarter;
import org.apache.commons.lang3.LocaleUtils;

import java.util.Map;
import java.util.TimeZone;

public class DatePickerManager extends SimpleViewManager<PickerView>  {

  public static final String REACT_CLASS = "DatePickerManager";
  public static ThemedReactContext context;
  private String date;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public PickerView createViewInstance(ThemedReactContext reactContext) {
    DatePickerManager.context = reactContext;
    ApplicationStarter.initialize(reactContext, false); // false = no need to prefetch on time data background tread
    return new PickerView();
  }

  @ReactProp(name = "mode")
  public void setMode(PickerView view, String mode) {
    Mode m;
    try {
      m = Mode.valueOf(mode);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid mode. Valid modes: 'datetime', 'date', 'time'");
    }
    view.setMode(m);
  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, String date) {
    this.date = date;
  }

  @ReactProp(name = "locale")
  public void setLocale(PickerView view, String locale) {
    view.setLocale(LocaleUtils.toLocale(locale.replace('-','_')));
  }

  @ReactProp(name = "minimumDate")
  public void setMinimumDate(PickerView view, String date) {
    view.setMinimumDate(date);
  }

  @ReactProp(name = "maximumDate")
  public void setMaximumDate(PickerView view, String date) {
    view.setMaximumDate(date);
  }

  @ReactProp(name = "fadeToColor")
  public void setFadeToColor(PickerView view, String color) {
    view.style.setFadeToColor(color);
  }

  @ReactProp(name = "textColor")
  public void setTextColor(PickerView view, String color) {
    view.style.setTextColor(color);
  }

  @ReactProp(name = "minuteInterval")
  public void setMinuteInterval(PickerView view,  int interval) throws Exception {
    if (interval < 0 || interval > 59) throw new Exception("Minute interval out of bounds");
    view.setMinuteInterval(interval);
  }

  @ReactProp(name = "utc")
  public void setUtc(PickerView view,  boolean utc) throws Exception {
    TimeZone timeZone = utc ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault();
    view.setTimeZone(timeZone);
  }

  @ReactPropGroup(names = {"height", "width"}, customType = "Style")
  public void setStyle(PickerView view, int index, Integer style) {
    if(index == 0) view.style.setHeight(style);
  }

  @Override
  protected void onAfterUpdateTransaction(PickerView view) {
   super.onAfterUpdateTransaction(view);
    // updateDisplayValuesIfNeeded() refreshes
    // which options are available. Should happen before updating the selected date.
    view.updateDisplayValuesIfNeeded();
    view.setDate(date);
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

}


