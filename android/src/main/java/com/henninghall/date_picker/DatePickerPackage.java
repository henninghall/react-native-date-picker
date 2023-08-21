package com.henninghall.date_picker;

import androidx.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatePickerPackage extends TurboReactPackage implements ReactPackage {
    public static ReactApplicationContext context;

    @Override
    public NativeModule getModule(String s, ReactApplicationContext reactContext) {
        context = reactContext;
        return new DatePickerModule(reactContext);
    }


    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
              return () -> {
                      final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
                      moduleInfos.put(
                                      DatePickerManager.NAME,
                                      new ReactModuleInfo(
                                                      DatePickerManager.NAME,
                                                      DatePickerManager.NAME,
                                                      false, // canOverrideExistingModule
                                                      false, // needsEagerInit
                                                      true, // hasConstants
                                                      false, // isCxxModule
                                                      true // isTurboModule
                                              ));
                      return moduleInfos;
                  };
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        context = reactContext;
        return Arrays.<ViewManager> asList(
                new DatePickerManager()
        );
    }

}