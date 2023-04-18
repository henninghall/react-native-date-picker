package com.henninghall.date_picker;


import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.VariantProp;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.TimezoneOffsetInMinutesProp;


import java.lang.reflect.Method;
import java.util.Map;

public class DatePickerManager extends SimpleViewManager<PickerView>  {

  private static final String REACT_CLASS = "DatePickerManager";
  private static final int SCROLL = 1;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public PickerView createViewInstance(ThemedReactContext context) {
    return new PickerView(new LinearLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
    ));
  }

  @ReactPropGroup(names = { DateProp.name, ModeProp.name, LocaleProp.name, MaximumDateProp.name,
          MinimumDateProp.name, FadeToColorProp.name, TextColorProp.name, TimezoneOffsetInMinutesProp.name, MinuteIntervalProp.name,
          VariantProp.name, DividerHeightProp.name, Is24hourSourceProp.name
  })
  public void setProps(PickerView view, int index, Dynamic value) {
    updateProp("setProps", view, index, value);
  }

  @ReactPropGroup(names = {"height"}, customType = "Style")
  public void setStyle(PickerView view, int index, Dynamic value) {
    updateProp("setStyle", view, index, value);
  }

  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("scroll", SCROLL);
  }

  @Override
  protected void onAfterUpdateTransaction(PickerView pickerView) {
   super.onAfterUpdateTransaction(pickerView);
     try{
       pickerView.update();
     }
     catch (Exception e){
       e.printStackTrace();
     }
  }

  public void receiveCommand(final PickerView view, int command, final ReadableArray args) {
    if (command == SCROLL) {
      int wheelIndex = args.getInt(0);
      int scrollTimes = args.getInt(1);
      view.scroll(wheelIndex, scrollTimes);
    }
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

  private void updateProp(String methodName, PickerView view, int index, Dynamic value){
    String[] propNames = getMethodAnnotation(methodName).names();
    String propName = propNames[index];
    view.updateProp(propName, value);
  }

  private ReactPropGroup getMethodAnnotation(String methodName) {
    Method[] methods = this.getClass().getMethods();
    Method method = null;
    for (Method m : methods) {
      if (m.getName().equals(methodName))
        method = m;
    }
    return method.getAnnotation(ReactPropGroup.class);
  }

}


