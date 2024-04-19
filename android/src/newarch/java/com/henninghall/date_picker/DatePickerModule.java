package com.henninghall.date_picker;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

import net.time4j.android.ApplicationStarter;

public class DatePickerModule extends NativeRNDatePickerSpec {

    private final DatePickerModuleImpl module;

    DatePickerModule(ReactApplicationContext context) {
        super(context);
        ApplicationStarter.initialize(context, false); // false = no need to prefetch on time data background tread
        module = new DatePickerModuleImpl(context);
    }

    @Override
    public void addListener(String eventName) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @Override
    public void removeListeners(double type) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @Override
    public void openPicker(ReadableMap props){
        module.openPicker(props);
    }

    @Override
    public void closePicker(){
        module.closePicker();
    }

    @Override
    @NonNull
    public String getName() {
        return DatePickerModuleImpl.NAME;
    }
}