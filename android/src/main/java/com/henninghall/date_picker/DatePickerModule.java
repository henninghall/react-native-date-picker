package com.henninghall.date_picker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henninghall.date_picker.Emitter;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import net.time4j.android.ApplicationStarter;

import java.util.Objects;

public class DatePickerModule extends ReactContextBaseJavaModule {

    DatePickerModule(ReactApplicationContext context) {
        super(context);
        ApplicationStarter.initialize(context, false); // false = no need to prefetch on time data background tread
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
    public void openPicker(ReadableMap props, Callback onConfirm, Callback onCancel){
        PickerView picker = createPicker(props);
        AlertDialog dialog = createDialog(props, picker, onConfirm, onCancel);
        dialog.show();
    }

    private AlertDialog createDialog(
            ReadableMap props, final PickerView picker, final Callback onConfirm, final Callback onCancel) {
        String title = props.getString("title");
        String confirmText = props.getString("confirmText");
        String neutralText = props.getString("neutralText");
        final String cancelText = props.getString("cancelText");
        final View pickerWithMargin = withTopMargin(picker);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DatePickerPackage.context.getCurrentActivity())
                .setTitle(title)
                .setCancelable(true)
                .setView(pickerWithMargin)
                .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onConfirm.invoke(picker.getDate());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onCancel.invoke();
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        onCancel.invoke();
                    }
                });

        if(neutralText != null) {
            dialogBuilder.setNeutralButton(neutralText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    WritableMap params = Arguments.createMap();
                    params.putString("eventProperty", "neutralButton");

                    Emitter.sendEvent("nativeButtonPress", params);
                    dialog.dismiss();
                }
            });
        }

        return dialogBuilder.create();
    }

    private PickerView createPicker(ReadableMap props){
        int height = 180;
        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                Utils.toDp(height));
        PickerView picker = new PickerView(rootLayoutParams);
        ReadableMapKeySetIterator iterator = props.keySetIterator();
        while(iterator.hasNextKey()){
            String key = iterator.nextKey();
            Dynamic value = props.getDynamic(key);
            if(!key.equals("style")){
                try{
                    picker.updateProp(key, value);
                } catch (Exception e){
                    // ignore invalid prop
                }
            }
        }
        picker.update();
        return picker;
    }

    private View withTopMargin(PickerView view) {
        LinearLayout linearLayout = new LinearLayout(DatePickerPackage.context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.addView(view);
        linearLayout.setPadding(0, Utils.toDp(20),0,0);
        return linearLayout;
    }

    @Override
    public String getName() {
        return "RNDatePicker";
    }
}