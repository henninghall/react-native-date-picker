package com.henninghall.date_picker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.henninghall.date_picker.ui.SpinnerState;
import com.henninghall.date_picker.ui.SpinnerStateListener;

import net.time4j.android.ApplicationStarter;

public class DatePickerModuleImpl {

    public static final String NAME = "RNDatePicker";
    private AlertDialog dialog;

    DatePickerModuleImpl(Context context) {
        ApplicationStarter.initialize(context, false); // no need to prefetch time data
    }

    public void openPicker(ReadableMap props) {
        final PickerView picker = createPicker(props);
        Callback onConfirm = new Callback() {
            @Override
            public void invoke(Object... objects) {
                Emitter.onConfirm(picker.getDate(), picker.getPickerId());
            }
        };
        Callback onCancel = new Callback() {
            @Override
            public void invoke(Object... objects) {
                Emitter.onCancel(picker.getPickerId());
            }
        };

        dialog = createDialog(props, picker, onConfirm, onCancel);
        dialog.show();
    }

    public void closePicker() {
        if (dialog != null) dialog.dismiss();
    }

    private AlertDialog createDialog(
        ReadableMap props,
        final PickerView picker,
        final Callback onConfirm,
        final Callback onCancel
    ) {
        String confirmText = props.getString("confirmText");
        final String cancelText = props.getString("cancelText");
        final String buttonColor = props.getString("buttonColor");

        // 1) Load the custom font if provided
        String fontFamily = props.hasKey("fontFamily") ? props.getString("fontFamily") : null;
        final Typeface dialogTf;
        if (fontFamily != null && !fontFamily.isEmpty()) {
            try {
                Typeface tf = Typeface.createFromAsset(
                    DatePickerPackage.context.getAssets(),
                    "fonts/" + fontFamily + ".ttf"
                );
                dialogTf = tf;  // Apply the font
            } catch (Exception e) {
                android.util.Log.e("DatePickerModule", "Font loading error: " + e.getMessage() + ". Falling back to default font.");
                dialogTf = Typeface.DEFAULT;  // Fallback to default
                // Optionally, notify or log for further debugging
            }
        } else {
            dialogTf = null;  // No font provided
        }

        final View pickerWithMargin = withTopMargin(picker);

        AlertDialog dialog = new AlertDialogBuilder(
                DatePickerPackage.context.getCurrentActivity(),
                getTheme(props)
            )
            .setColoredTitle(props, dialogTf)
            .setCancelable(true)
            .setView(pickerWithMargin)
            .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface d, int id) {
                    onConfirm.invoke(picker.getDate());
                    d.dismiss();
                }
            })
            .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface d, int id) {
                    onCancel.invoke();
                    d.dismiss();
                }
            })
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface d) {
                    onCancel.invoke();
                }
            })
            .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                // Apply button colors
                if (buttonColor != null) {
                    int color = Color.parseColor(buttonColor);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                          .setTextColor(color);
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                          .setTextColor(color);
                }
                // Apply custom font to buttons
                if (dialogTf != null) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                          .setTypeface(dialogTf);
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                          .setTypeface(dialogTf);
                }
            }
        });

        return dialog;
    }

    private int getTheme(ReadableMap props) {
        int defaultTheme = 0;
        String theme = props.getString("theme");
        if (theme == null) return defaultTheme;
        switch (theme) {
            case "light":
                return AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
            case "dark":
                return AlertDialog.THEME_DEVICE_DEFAULT_DARK;
            default:
                return defaultTheme;
        }
    }

    private PickerView createPicker(ReadableMap props) {
        int height = 180;
        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            Utils.toDp(height)
        );
        PickerView picker = new PickerView(rootLayoutParams);
        ReadableMapKeySetIterator iterator = props.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            Dynamic value = props.getDynamic(key);
            if (!key.equals("style")) {
                try {
                    picker.updateProp(key, value);
                } catch (Exception e) {
                    // ignore invalid prop
                }
            }
        }
        picker.update();
        picker.addSpinnerStateListener(new SpinnerStateListener() {
            @Override
            public void onChange(SpinnerState state) {
                setEnabledConfirmButton(state == SpinnerState.idle);
            }
        });
        return picker;
    }

    private void setEnabledConfirmButton(boolean enabled) {
        if (dialog != null) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                  .setEnabled(enabled);
        }
    }

    private View withTopMargin(PickerView view) {
        LinearLayout linearLayout = new LinearLayout(DatePickerPackage.context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.addView(view);
        linearLayout.setPadding(0, Utils.toDp(20), 0, 0);
        return linearLayout;
    }

    static class AlertDialogBuilder extends AlertDialog.Builder {
        AlertDialogBuilder(Context context, int themeResId) {
            super(context, themeResId);
        }

        /**
         * Sets title color and font. dialogTf may be null.
         */
        public AlertDialogBuilder setColoredTitle(ReadableMap props, Typeface dialogTf) {
            String textColor = props.getString("textColor");
            String title = props.getString("title");
            // If neither color nor font is set, fall back to plain title
            if (textColor == null && dialogTf == null) {
                this.setTitle(title);
                return this;
            }
            TextView coloredTitle = new TextView(DatePickerPackage.context.getCurrentActivity());
            coloredTitle.setText(title);

            // padding
            TypedValue tv = new TypedValue();
            Resources.Theme th = DatePickerPackage.context
                .getCurrentActivity().getTheme();
            th.resolveAttribute(android.R.attr.dialogPreferredPadding, tv, true);
            int pad = TypedValue.complexToDimensionPixelSize(
                tv.data,
                DatePickerPackage.context.getResources().getDisplayMetrics()
            );
            coloredTitle.setPadding(pad, pad, pad, 0);

            // text size
            coloredTitle.setTextSize(20F);

            // apply color
            if (textColor != null) {
                int colorInt = Color.parseColor(textColor);
                coloredTitle.setTextColor(colorInt);
            }
            // apply font
            if (dialogTf != null) {
                coloredTitle.setTypeface(dialogTf);
            }
            this.setCustomTitle(coloredTitle);
            return this;
        }
    }
}
