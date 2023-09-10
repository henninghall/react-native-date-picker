package com.henninghall.date_picker;


import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactPropGroup;


import java.lang.reflect.Method;
import java.util.Map;

public class DatePickerManagerImpl {

  static final String NAME = "RNDatePicker";
  static final int SCROLL = 1;

  public String getName() {
    return NAME;
  }

  public static PickerView createViewInstance(ThemedReactContext context) {
    return new PickerView(new LinearLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
    ));
  }

  public static void setProps(PickerView view, int index, Dynamic value, Class<? extends com.henninghall.date_picker.DatePickerManager> aClass) {
    updateProp("setProps", view, index, value, aClass);
  }

  public static void setStyle(PickerView view, int index, Dynamic value, Class<? extends com.henninghall.date_picker.DatePickerManager> aClass) {
    updateProp("setStyle", view, index, value, aClass);
  }

  public static Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("scroll", SCROLL);
  }

  protected static void onAfterUpdateTransaction(PickerView pickerView) {
     try{
       pickerView.update();
     }
     catch (Exception e){
       e.printStackTrace();
     }
  }

  public static void receiveCommand(final PickerView view, int commandId, final ReadableArray args) {
    if (commandId == SCROLL) {
      int wheelIndex = args.getInt(0);
      int scrollTimes = args.getInt(1);
      view.scroll(wheelIndex, scrollTimes);
    }
  }



  public static void updateProp(String methodName, PickerView view, int index, Dynamic value, Class<? extends com.henninghall.date_picker.DatePickerManager> aClass){
    String[] propNames = getMethodAnnotation(methodName, aClass).names();
    String propName = propNames[index];
    view.updateProp(propName, value);
  }

  private static ReactPropGroup getMethodAnnotation(String methodName, Class<? extends com.henninghall.date_picker.DatePickerManager> aClass) {
    Method[] methods = aClass.getMethods();
    Method method = null;
    for (Method m : methods) {
      if (m.getName().equals(methodName))
        method = m;
    }
    return method.getAnnotation(ReactPropGroup.class);
  }

}


