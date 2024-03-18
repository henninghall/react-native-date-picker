package com.henninghall.date_picker.ui;

import android.view.View;

import com.henninghall.date_picker.State;
import com.henninghall.date_picker.wheelFunctions.AddOnChangeListener;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.SetDividerColor;
import com.henninghall.date_picker.wheelFunctions.TextColor;
import com.henninghall.date_picker.wheelFunctions.UpdateVisibility;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UIManager {
    private final State state;
    private final View rootView;
    private Wheels wheels;
    private WheelScroller wheelScroller = new WheelScroller();
    private WheelChangeListenerImpl onWheelChangeListener;

    public UIManager(State state, View rootView){
        this.state = state;
        this.rootView = rootView;
        wheels = new Wheels(state, rootView);
        addOnChangeListener();
    }

    public void updateWheelVisibility(){
        wheels.applyOnAll(new UpdateVisibility());
    }

    public void updateTextColor(){
        wheels.applyOnAll(new TextColor(state.getTextColor()));
    }

    public void updateWheelOrder() {
        wheels.updateWheelOrder();
    }

    public void updateDisplayValues(){
        wheels.applyOnAll(new Refresh());
    }

    public void setWheelsToDate(){
        wheels.applyOnAll(new SetDate(state.getPickerDate()));
    }

    public void scroll(int wheelIndex, int scrollTimes) {
        Wheel wheel = wheels.getWheel(state.derived.getOrderedVisibleWheels().get(wheelIndex));
        wheelScroller.scroll(wheel, scrollTimes);
    }

    SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(wheels.getFormatPattern(), state.getLocale());
    }

    String getDisplayValueString() {
        return wheels.getDisplayValue();
    }

    void animateToDate(Calendar date) {
        wheels.applyOnInVisible(new SetDate(date));
        wheels.applyOnVisible(new AnimateToDate(date));
    }

    private void addOnChangeListener(){
        onWheelChangeListener = new WheelChangeListenerImpl(wheels, state, this, rootView);
        wheels.applyOnAll(new AddOnChangeListener(onWheelChangeListener));
    }

    public void addStateListener(SpinnerStateListener listener){
        onWheelChangeListener.addStateListener(listener);
    }

    public void updateLastSelectedDate(Calendar date) {
        state.setLastSelectedDate(date);
    }

    public void setDividerColor(String color) {
        wheels.applyOnAll(new SetDividerColor(color));
    }
}
