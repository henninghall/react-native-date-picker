package com.henninghall.date_picker;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatePickerPackage implements ReactPackage {
    public static ReactApplicationContext context;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        context = reactContext;
        return Arrays.<NativeModule>asList(
                new DatePickerModule(reactContext)
        );
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        context = reactContext;
        return Arrays.<ViewManager> asList(
                new DatePickerManager()
        );
    }

}