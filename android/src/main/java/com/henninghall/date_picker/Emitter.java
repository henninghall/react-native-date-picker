package com.henninghall.date_picker;

import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Calendar;

public class Emitter {

    private static RCTEventEmitter eventEmitter(){
        return DatePickerPackage.context.getJSModule(RCTEventEmitter.class);
    }

    private static DeviceEventManagerModule.RCTDeviceEventEmitter deviceEventEmitter(){
        return DatePickerPackage.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    public static void onDateChange(Calendar date, String displayValueString) {
        WritableMap event = Arguments.createMap();
        String dateString = Utils.dateToIso(date);
        event.putString("date", dateString);
        event.putString("dateString", displayValueString);
        // TODO: Handle multiple pickers on same screen
        deviceEventEmitter().emit("dateChange", event);
    }
    public static void onConfirm(String date) {
        WritableMap event = Arguments.createMap();
        event.putString("date", date);
        // TODO: Handle multiple pickers on same screen
        deviceEventEmitter().emit("onConfirm", event);
    }

    public static void onCancel() {
        WritableMap event = Arguments.createMap();
        // TODO: Handle multiple pickers on same screen
        deviceEventEmitter().emit("onCancel", event);
    }



}
