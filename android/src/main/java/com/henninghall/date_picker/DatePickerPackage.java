package com.henninghall.date_picker;

import androidx.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.henninghall.date_picker.DatePickerModule;
import com.henninghall.date_picker.DatePickerManager;

public class DatePickerPackage extends TurboReactPackage implements ReactPackage {
    public static ReactApplicationContext context;

    @Nullable
    @Override
    public NativeModule getModule(String name, ReactApplicationContext reactContext) {
        if (name.equals(DatePickerModuleImpl.NAME)) {
            context = reactContext;
            return new DatePickerModule(reactContext);
        } else {
            return null;
        }
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return new ReactModuleInfoProvider() {
            final boolean isTurboModule = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
            public Map<String, ReactModuleInfo> getReactModuleInfos() {
                final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
                moduleInfos.put(
                        DatePickerModuleImpl.NAME,
                        new ReactModuleInfo(
                                DatePickerModuleImpl.NAME,
                                DatePickerModuleImpl.NAME,
                                false, // canOverrideExistingModule
                                false, // needsEagerInit
                                true, // hasConstants
                                false, // isCxxModule
                                isTurboModule
                        ));
                return moduleInfos;
            }
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