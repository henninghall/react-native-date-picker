package com.henninghall.date_picker;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class DatePickerModule extends ReactContextBaseJavaModule {

    private final DatePickerModuleImpl module;

    DatePickerModule(ReactApplicationContext context) {
        super(context);
        module = new DatePickerModuleImpl(context);
    }

    @ReactMethod
    public void addListener(String eventName) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    public void openPicker(ReadableMap props){
        module.openPicker(props);
    }

    @Override
    public String getName() {
        return DatePickerModuleImpl.NAME;
    }
}