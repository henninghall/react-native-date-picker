package com.henninghall.date_picker;

import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Date;
import java.util.Locale;
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
    return new PickerView();
  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, @Nullable double date) {
       view.setDate(new Date((long)date));
  }

  @ReactProp(name = "locale")
  public void setLocale(PickerView view, @Nullable String locale) {
    view.setLocale(LocaleUtils.toLocale(locale.replace('-','_')));
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

}


