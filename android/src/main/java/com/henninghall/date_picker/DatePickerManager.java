package com.henninghall.date_picker;

import android.support.annotation.Nullable;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import net.time4j.android.ApplicationStarter;
import org.apache.commons.lang3.LocaleUtils;

import java.util.Map;
import java.util.TimeZone;

public class DatePickerManager extends SimpleViewManager<PickerView>  {

  public static final String REACT_CLASS = "DatePickerManager";
  public static ThemedReactContext context;
  private String date;
  private String minimumDate;
  private String maximumDate;
  private boolean utc;

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
    Mode m;
    try {
      m = Mode.valueOf(mode);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid mode. Valid modes: 'datetime', 'date', 'time'");
    }
    view.setMode(m);
  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, @Nullable String date) {
      this.date = date;
  }

  @ReactProp(name = "locale")
  public void setLocale(PickerView view, @Nullable String locale) {
    view.setLocale(LocaleUtils.toLocale(locale.replace('-','_')));
  }

  @ReactProp(name = "minimumDate")
  public void setMinimumDate(PickerView view, @Nullable String date) {
    this.minimumDate = minimumDate;
  }

  @ReactProp(name = "maximumDate")
  public void setMaximumDate(PickerView view, @Nullable String date) {
    this.maximumDate = maximumDate;
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
    view.setMinuteInterval(interval);
  }

  @ReactProp(name = "utc")
  public void setUtc(PickerView view, @Nullable boolean utc) throws Exception {
    this.utc = utc;
  }



  @Override
  protected void onAfterUpdateTransaction(PickerView view) {
    super.onAfterUpdateTransaction(view);

    TimeZone timeZone = utc ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault();

    view.updateDisplayValuesIfNeeded();
    view.setTimeZone(timeZone);
    view.setDate(Utils.isoToCalendar(date, timeZone));
    view.setMinimumDate(Utils.isoToCalendar(minimumDate, timeZone));
    view.setMaximumDate(Utils.isoToCalendar(maximumDate, timeZone));
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

}


