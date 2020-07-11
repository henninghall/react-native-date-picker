package com.henninghall.date_picker;

import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.props.VariantProp;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.HeightProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.ui.UIManager;

import java.util.ArrayList;

public class PickerView extends RelativeLayout {

    private UIManager uiManager;
    private State state = new State();
    private ArrayList<String> updatedProps = new ArrayList<>();
    private boolean initialized = false;

    public PickerView() {
        super(DatePickerManager.context);
    }

    public void update() {

        if(!initialized){
            inflate(getContext(), state.derived.getRootLayout(), this);
            uiManager = new UIManager(state, this);
            initialized = true;
        }

        if(updatedProps.contains(FadeToColorProp.name)) {
            uiManager.updateFadeToColor();
        }

        if(updatedProps.contains(TextColorProp.name)) {
            uiManager.updateTextColor();
        }

        if(updatedProps.contains(ModeProp.name)) {
            uiManager.updateWheelVisibility();
        }

        if(updatedProps.contains(HeightProp.name)) {
            uiManager.updateHeight();
        }

        if(updatedProps.contains(ModeProp.name) || updatedProps.contains(LocaleProp.name)) {
            uiManager.updateWheelOrder();
        }

        ArrayList<String> noDisplayValueChangeProps = new ArrayList<String>(){{
            add(DateProp.name);
            add(FadeToColorProp.name);
            add(TextColorProp.name);
        }};
        updatedProps.removeAll(noDisplayValueChangeProps);

        if(updatedProps.size() != 0) {
            uiManager.updateDisplayValues();
        }

        uiManager.setWheelsToDate();

        updatedProps = new ArrayList<>();
    }

    public void updateProp(String propName, Dynamic value) {
        state.setProp(propName, value);
        updatedProps.add(propName);
    }

    public void scroll(int wheelIndex, int scrollTimes) {
        uiManager.scroll(wheelIndex, scrollTimes);
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
