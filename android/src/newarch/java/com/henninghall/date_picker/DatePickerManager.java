package com.henninghall.date_picker;


import com.facebook.react.bridge.Dynamic;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.IdProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.TimezoneOffsetInMinutesProp;
import com.henninghall.date_picker.props.VariantProp;

import java.util.Map;

public class DatePickerManager extends SimpleViewManager<PickerView> {

    @Override
    public String getName() {
        return DatePickerManagerImpl.NAME;
    }

    @Override
    public PickerView createViewInstance(ThemedReactContext context) {
        return DatePickerManagerImpl.createViewInstance(context);
    }

    @ReactPropGroup(names = { DateProp.name, ModeProp.name, LocaleProp.name, MaximumDateProp.name,
            MinimumDateProp.name, FadeToColorProp.name, TextColorProp.name, TimezoneOffsetInMinutesProp.name, MinuteIntervalProp.name,
            VariantProp.name, DividerHeightProp.name, Is24hourSourceProp.name, IdProp.name
    })
    public void setProps(PickerView view, int index, Dynamic value) {
        DatePickerManagerImpl.updateProp("setProps", view, index, value, getClass());
    }

    @ReactPropGroup(names = {"height"}, customType = "Style")
    public void setStyle(PickerView view, int index, Dynamic value) {
        DatePickerManagerImpl.updateProp("setStyle", view, index, value, getClass());
    }

    @Override
    public Map<String, Integer> getCommandsMap() {
        return DatePickerManagerImpl.getCommandsMap();
    }

    @Override
    protected void onAfterUpdateTransaction(PickerView pickerView) {
        super.onAfterUpdateTransaction(pickerView);
        DatePickerManagerImpl.onAfterUpdateTransaction(pickerView);
    }




}


