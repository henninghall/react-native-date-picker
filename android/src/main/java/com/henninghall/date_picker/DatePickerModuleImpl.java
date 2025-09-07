package com.henninghall.date_picker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.henninghall.date_picker.ui.SpinnerState;
import com.henninghall.date_picker.ui.SpinnerStateListener;

import net.time4j.android.ApplicationStarter;

public class DatePickerModuleImpl {

    public static final String NAME = "RNDatePicker";
    private static final String DIALOG_TAG = "DatePickerDialog";
    private DatePickerDialogFragment dialogFragment;

    DatePickerModuleImpl(Context context) {
        ApplicationStarter.initialize(context, false);
    }

    public void openPicker(ReadableMap props) {
        FragmentActivity activity = (FragmentActivity) DatePickerPackage.context.getCurrentActivity();
        if (activity == null) {
            return;
        }

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        closePicker();

        dialogFragment = DatePickerDialogFragment.newInstance(props);
        dialogFragment.setProps(props);
        dialogFragment.show(fragmentManager, DIALOG_TAG);
    }

    public void closePicker() {
        if (dialogFragment != null) {
            dialogFragment.dismiss();
            dialogFragment = null;
        } else {
            FragmentActivity activity = (FragmentActivity) DatePickerPackage.context.getCurrentActivity();
            if (activity != null) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                DatePickerDialogFragment existingDialog =
                    (DatePickerDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
                if (existingDialog != null) {
                    existingDialog.dismiss();
                }
            }
        }
    }

    public static class DatePickerDialogFragment extends DialogFragment {

        private static final String ARG_PROPS = "props";
        private PickerView picker;
        private ReadableMap props;
        private AlertDialog alertDialog;

        public static DatePickerDialogFragment newInstance(ReadableMap props) {
            DatePickerDialogFragment fragment = new DatePickerDialogFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public void setProps(ReadableMap props) {
            this.props = props;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            if (props == null) {
                dismiss();
                return super.onCreateDialog(savedInstanceState);
            }

            picker = createPicker(props);

            String confirmText = props.getString("confirmText");
            String cancelText = props.getString("cancelText");
            String buttonColor = props.getString("buttonColor");
            View pickerWithMargin = withTopMargin(picker);

            AlertDialog.Builder builder = new AlertDialogBuilder(requireContext(), getTheme(props))
                    .setColoredTitle(props)
                    .setCancelable(true)
                    .setView(pickerWithMargin)
                    .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Emitter.onConfirm(picker.getDate(), picker.getPickerId());
                            dismiss();
                        }
                    })
                    .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Emitter.onCancel(picker.getPickerId());
                            dismiss();
                        }
                    });

            alertDialog = builder.create();

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    if (buttonColor != null) {
                        int color = Color.parseColor(buttonColor);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
                    }
                }
            });

            return alertDialog;
        }

        @Override
        public void onCancel(@NonNull DialogInterface dialog) {
            super.onCancel(dialog);
            if (picker != null) {
                Emitter.onCancel(picker.getPickerId());
            }
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
                    Utils.toDp(height));
            PickerView picker = new PickerView(rootLayoutParams);
            ReadableMapKeySetIterator iterator = props.keySetIterator();
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                Dynamic value = props.getDynamic(key);
                if (!key.equals("style")) {
                    try {
                        picker.updateProp(key, value);
                    } catch (Exception e) {}
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
            if (alertDialog != null) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(enabled);
            }
        }

        private View withTopMargin(PickerView view) {
            LinearLayout linearLayout = new LinearLayout(requireContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.addView(view);
            linearLayout.setPadding(0, Utils.toDp(20), 0, 0);
            return linearLayout;
        }
    }

    static class AlertDialogBuilder extends AlertDialog.Builder {
        public AlertDialogBuilder(Context context, int themeResId) {
            super(context, themeResId);
        }

        public AlertDialog.Builder setColoredTitle(ReadableMap props) {
            String textColor = props.getString("textColor");
            String title = props.getString("title");
            if (textColor == null) {
                this.setTitle(title);
                return this;
            }
            TextView coloredTitle = new TextView(DatePickerPackage.context.getCurrentActivity());
            coloredTitle.setText(title);
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = DatePickerPackage.context.getCurrentActivity().getTheme();
            theme.resolveAttribute(android.R.attr.dialogPreferredPadding, typedValue, true);
            int paddingInPixels = TypedValue.complexToDimensionPixelSize(typedValue.data, DatePickerPackage.context.getResources().getDisplayMetrics());
            coloredTitle.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, 0);
            coloredTitle.setTextSize(20F);
            int color = Color.parseColor(textColor);
            coloredTitle.setTextColor(color);
            this.setCustomTitle(coloredTitle);
            return this;
        }
    }
}
