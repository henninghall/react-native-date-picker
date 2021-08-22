package com.henninghall.date_picker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    public static ReactApplicationContext context;

    DatePickerModule(ReactApplicationContext context) {
        super(context);
        ApplicationStarter.initialize(context,   false); // false = no need to prefetch on time data background tread
        DatePickerModule.context = context;
    }

    @ReactMethod
    public void openPicker(ReadableMap props){
        PickerView picker = createPicker(props);
        AlertDialog dialog = createDialog(picker);
        dialog.show();
    }

    private AlertDialog createDialog (View view) {
        return new AlertDialog.Builder(context.getCurrentActivity())
                .setTitle("Your title")
                .setMessage("Click yes to exit!")
                .setCancelable(true)
                .setView(view)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create();
    }

    private PickerView createPicker(ReadableMap props){
        PickerView picker = new PickerView();
        picker.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
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