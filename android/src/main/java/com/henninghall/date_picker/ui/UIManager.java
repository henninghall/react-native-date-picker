package com.henninghall.date_picker.ui;

import android.view.View;

import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.SetShowCount;
import com.henninghall.date_picker.wheelFunctions.TextColor;
import com.henninghall.date_picker.wheelFunctions.UpdateVisibility;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class UIManager {
    private final State state;
    private final View rootView;
    private Wheels wheels;
    private FadingOverlay fadingOverlay;
    private WheelScroller wheelScroller = new WheelScroller();

    public UIManager(State state, View rootView){
        this.rootView = rootView;
        this.state = state;
        wheels = new Wheels(state, rootView, this);
        fadingOverlay = new FadingOverlay(state, rootView);
    }

    public void updateWheelVisibility(){
        wheels.applyOnAll(new UpdateVisibility());
    }

    public void updateTextColor(){
        wheels.applyOnAll(new TextColor(state.getTextColor()));
    }

    public void updateFadeToColor(){
        fadingOverlay.updateColor();
    }

    public void updateHeight(){
        int shownCount = state.getShownCount();
        wheels.applyOnAll(new SetShowCount(shownCount));
        setShownCountOnEmptyWheels(shownCount);
    }

    public void updateWheelOrder() {
        wheels.removeAll();
        wheels.addInOrder();
        wheels.addEmpty();
    }

    public void updateDisplayValues(){
        wheels.applyOnAll(new Refresh());
    }

    public void setWheelsToDate(){
        wheels.applyOnAll(new SetDate(state.getDate()));
    }

    public void scroll(int wheelIndex, int scrollTimes) {
        Wheel wheel = wheels.getWheel(state.getOrderedVisibleWheels().get(wheelIndex));
        wheelScroller.scroll(wheel,scrollTimes);
    }

    SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(wheels.getFormatPattern(), state.getLocale());
    }

    void animateToDate(Calendar date) {
        wheels.applyOnVisible(new AnimateToDate(date));
    }

    private void setShownCountOnEmptyWheels(int shownCount) {
        for (int id : Utils.emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) rootView.findViewById(id);
            if(view != null) view.setShownCount(shownCount);
        }
    }


}
