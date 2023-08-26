package com.henninghall.date_picker;

import androidx.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatePickerPackage extends TurboReactPackage implements ReactPackage {
    public static ReactApplicationContext context;

    @Nullable
    @Override
    public NativeModule getModule(String name, ReactApplicationContext reactContext) {
        if (name.equals(DatePickerModule.NAME)) {
            context = reactContext;
            return new DatePickerModule(reactContext);
        } else {
            return null;
        }
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return () -> {
            final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
            moduleInfos.put(
                    DatePickerModule.NAME,
                    new ReactModuleInfo(
                            DatePickerModule.NAME,
                            DatePickerModule.NAME,
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

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new DatePickerModule(reactContext));
        return modules;
    }

}