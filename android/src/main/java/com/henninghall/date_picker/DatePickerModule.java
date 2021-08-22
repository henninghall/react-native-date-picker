package com.henninghall.date_picker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
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

    }

    private AlertDialog createDialog (View view) {
        AlertDialog dialog = new AlertDialog.Builder(context.getCurrentActivity())
                .setTitle("Select date")
//                .setMessage("Click yes to exit!")
                .setCancelable(true)
                .setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        MainActivity.this.finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
//                        dialog.cancel();
                    }
                })
                .create();

        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        dialog.getWindow().setWindowAnimations(R.style.PauseDialog);

//        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);

        dialog.show();

        return dialog;
    }

    private PickerView createPicker(ReadableMap props){
        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                400
        );
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