package com.henninghall.date_picker;

import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.HeightProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.ui.UIManager;

import java.util.ArrayList;

public class PickerView extends RelativeLayout {

    private final View rootView = inflate(getContext(), R.layout.datepicker_view, this);
    private final UIManager uiManager;
    private State state;
    private ArrayList<String> updatedProps = new ArrayList<>();

    public PickerView() {
        super(DatePickerManager.context);
        state = new State();
        uiManager = new UIManager(state, this);
    }

    public void update() {

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

        ArrayList<String> nonRefreshingProps = new ArrayList<String>(){{
            add(DateProp.name);
            add(FadeToColorProp.name);
            add(TextColorProp.name);
        }};
        updatedProps.removeAll(nonRefreshingProps);

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

    public View getRootView(){
        return rootView;
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
