package com.henninghall.date_picker;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.props.DividerColorProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.TimezoneOffsetInMinutesProp;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.ui.SpinnerStateListener;
import com.henninghall.date_picker.ui.UIManager;
import com.henninghall.date_picker.ui.Accessibility;

import java.util.ArrayList;

public class PickerView extends RelativeLayout {

    private UIManager uiManager;
    private State state = new State();
    private ArrayList<String> updatedProps = new ArrayList<>();

    public PickerView(ViewGroup.LayoutParams layoutParams) {
        super(DatePickerPackage.context);
        LinearLayout layout = new LinearLayout(getContext());
        LayoutInflater.from(getContext()).inflate(state.derived.getRootLayout(), layout);
        this.addView(layout, layoutParams);
        uiManager = new UIManager(state, this);
    }

    public void update() {

        if (didUpdate(TextColorProp.name)) {
            uiManager.updateTextColor();
        }

        if (didUpdate(ModeProp.name, Is24hourSourceProp.name)) {
            uiManager.updateWheelVisibility();
        }

        if (didUpdate(ModeProp.name, LocaleProp.name, Is24hourSourceProp.name)) {
            uiManager.updateWheelOrder();
        }

        if (didUpdate(DateProp.name, LocaleProp.name,
                MaximumDateProp.name, MinimumDateProp.name, MinuteIntervalProp.name, ModeProp.name,
                TimezoneOffsetInMinutesProp.name
        )) {
            uiManager.updateDisplayValues();
        }

        if (didUpdate(LocaleProp.name)) {
            Accessibility.setLocale(state.getLocale());
        }

        if (didUpdate(DividerColorProp.name)) {
            uiManager.setDividerColor(state.getDividerColor());
        }

        uiManager.setWheelsToDate();

        updatedProps = new ArrayList<>();
    }

    private boolean didUpdate(String... propNames) {
        for (String propName : propNames) {
            if (updatedProps.contains(propName)) return true;
        }
        return false;
    }

    public void updateProp(String propName, Dynamic value) {
        state.setProp(propName, value);
        updatedProps.add(propName);
    }

    public void scroll(int wheelIndex, int scrollTimes) {
        uiManager.scroll(wheelIndex, scrollTimes);
    }

    public void addSpinnerStateListener(SpinnerStateListener listener){
        uiManager.addStateListener(listener);
    }

    public String getDate() {
        return state.derived.getLastDate();
    }

    public String getPickerId() {
        return state.getId();
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }


}
