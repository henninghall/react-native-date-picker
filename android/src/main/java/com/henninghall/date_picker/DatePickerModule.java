package com.henninghall.date_picker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import net.time4j.android.ApplicationStarter;

public class DatePickerModule extends ReactContextBaseJavaModule {

    private String lastDate;

    DatePickerModule(ReactApplicationContext context) {
        super(context);
        ApplicationStarter.initialize(context,   false); // false = no need to prefetch on time data background tread
    }

    @ReactMethod
    public void openPicker(ReadableMap props){
        PickerView picker = createPicker(props);
        AlertDialog dialog = createDialog(picker);
        dialog.show();
    }

    private AlertDialog createDialog (final PickerView view) {
        AlertDialog dialog = new AlertDialog.Builder(DatePickerPackage.context.getCurrentActivity())
                .setTitle("Select date")
                .setCancelable(true)
                .setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Emitter.onConfirm(view.getDate());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Emitter.onCancel();
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Emitter.onCancel();
                    }
                })
                .create();
        return dialog;
    }

    private PickerView createPicker(ReadableMap props){
        int height = 180;
        int heightPx = (int) (height * DatePickerPackage.context.getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                heightPx);
        PickerView picker = new PickerView(rootLayoutParams);
        ReadableMapKeySetIterator iterator = props.keySetIterator();
        while(iterator.hasNextKey()){
            String key = iterator.nextKey();
            Dynamic value = props.getDynamic(key);
            if(!key.equals("style")){
                picker.updateProp(key, value);
            }
        }
        picker.update();
        return picker;
    }

    @Override
    public String getName() {
        return "RNDatePicker";
    }
}