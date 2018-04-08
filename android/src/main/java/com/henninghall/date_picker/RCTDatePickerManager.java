package com.henninghall.date_picker;

import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.image.ReactImageView;

import java.util.Date;
import java.util.Map;

import static java.security.AccessController.getContext;


public class RCTDatePickerManager extends SimpleViewManager<View>  {

  public static final String REACT_CLASS = "RCTDatePickerManager";
  public static ThemedReactContext context;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public PickerView createViewInstance(ThemedReactContext reactContext) {
    RCTDatePickerManager.context = reactContext;
    return new PickerView();
  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, @Nullable double date) {
       view.setDate(new Date((long)date));
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

}


