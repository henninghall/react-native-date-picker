package com.henninghall.date_picker.ui;

import android.view.View;

import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.WheelType;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DateWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;
import com.henninghall.date_picker.wheels.MonthWheel;
import com.henninghall.date_picker.wheels.Wheel;
import com.henninghall.date_picker.wheels.YearWheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Wheels {

    private final State state;
    private HourWheel hourWheel;
    private DayWheel dayWheel;
    private MinutesWheel minutesWheel;
    private AmPmWheel ampmWheel;
    private DateWheel dateWheel;
    private MonthWheel monthWheel;
    private YearWheel yearWheel;
    private View rootView;
    private final PickerWrapper pickerWrapper;

    private HashMap<WheelType, Wheel> wheelPerWheelType;

    Wheels(State state, View rootView){
        this.state = state;
        this.rootView = rootView;
        pickerWrapper = new PickerWrapper(rootView);

        yearWheel = new YearWheel(getPickerWithId(R.id.year), state);
        monthWheel = new MonthWheel(getPickerWithId(R.id.month), state);
        dateWheel = new DateWheel(getPickerWithId(R.id.date), state);
        dayWheel = new DayWheel(getPickerWithId(R.id.day), state);
        minutesWheel = new MinutesWheel(getPickerWithId(R.id.minutes), state);
        ampmWheel = new AmPmWheel(getPickerWithId(R.id.ampm), state);
        hourWheel = new HourWheel(getPickerWithId(R.id.hour), state);
        wheelPerWheelType = getWheelPerType();
        changeAmPmWhenPassingMidnightOrNoon();
    }

    private Picker getPickerWithId(final int id){
        return (Picker) rootView.findViewById(id);
    }

    void applyOnAll(WheelFunction function) {
        for (Wheel wheel: getAll()) function.apply(wheel);
    }

    void applyOnVisible(WheelFunction function) {
        for(Wheel wheel: getAll()) {
            if(wheel.visible()) function.apply(wheel);
        }
    }

    void applyOnInVisible(WheelFunction function) {
        for(Wheel wheel: getAll()) {
            if(!wheel.visible()) function.apply(wheel);
        }
    }

    void updateWheelOrder() {
        pickerWrapper.removeAll();
        addInOrder();
    }

    Wheel getWheel(WheelType type){
        return wheelPerWheelType.get(type);
    }

    String getDateTimeString(int daysToSubtract) {
        return getDateString(daysToSubtract) + " " + getTimeString();
    }

    private String getDateModeString(int daysToSubtract) {
        ArrayList<Wheel> wheels = getOrderedVisibleWheels();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (i != 0) sb.append(" ");
            Wheel w = wheels.get(i);
            if (w instanceof DateWheel) {
                sb.append(w.getPastValue(daysToSubtract));
            }
            else sb.append(w.getValue());
        }
        return sb.toString();
    }

    private String getDateString(int daysToSubtract){
        if(state.getMode() == Mode.date ){
            return getDateModeString(daysToSubtract);
        }
        return dayWheel.getValue();
    }

    String getTimeString(){
        return hourWheel.getValue()
                + " " + minutesWheel.getValue()
                + ampmWheel.getValue();
    }

    String getDateTimeString() {
        return getDateTimeString(0);
    }

    String getDateString() {
        return getDateString(0);
    }

    String getDisplayValue() {
        StringBuilder sb = new StringBuilder();
        for (Wheel wheel: getOrderedVisibleWheels()) {
            sb.append(wheel.getDisplayValue());
        }
        return sb.toString();
    }

    private void addInOrder(){
        ArrayList<WheelType> wheels = state.derived.getOrderedVisibleWheels();
        for (WheelType wheelType : wheels) {
            Wheel wheel = getWheel(wheelType);
            pickerWrapper.addPicker(wheel.picker.getView());
        }
    }

    private ArrayList<Wheel> getOrderedVisibleWheels(){
        ArrayList<Wheel> list = new ArrayList<>();
        for (WheelType type : state.derived.getOrderedVisibleWheels()) {
            list.add(getWheel(type));
        }
        return list;
    }

    private void changeAmPmWhenPassingMidnightOrNoon() {
        hourWheel.picker.setOnValueChangeListenerInScrolling(new Picker.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(Picker picker, int oldVal, int newVal) {
                if(state.derived.usesAmPm()){
                    String oldValue = hourWheel.getValueAtIndex(oldVal);
                    String newValue = hourWheel.getValueAtIndex(newVal);
                    boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || oldValue.equals("11") && newValue.equals("12");
                    if (passingNoonOrMidnight) ampmWheel.picker.smoothScrollToValue((ampmWheel.picker.getValue() + 1) % 2,false);
                }
            }
        });
    }

    private List<Wheel> getAll(){
        return new ArrayList<>(Arrays.asList(yearWheel, monthWheel, dateWheel, dayWheel, hourWheel, minutesWheel, ampmWheel));
    }

    private String getDateFormatPattern(){
        ArrayList<Wheel> wheels = getOrderedVisibleWheels();
        if(state.getMode() == Mode.date){
            return wheels.get(0).getFormatPattern() + " "
                    + wheels.get(1).getFormatPattern() + " "
                    + wheels.get(2).getFormatPattern();
        }
        return dayWheel.getFormatPattern();
    }

    public String getFormatPattern() {
        return this.getDateFormatPattern() + " "
                + hourWheel.getFormatPattern() + " "
                + minutesWheel.getFormatPattern()
                + ampmWheel.getFormatPattern();
    }

    private HashMap<WheelType, Wheel> getWheelPerType(){
        return new HashMap<WheelType, Wheel>() {{
            put(WheelType.DAY, dayWheel);
            put(WheelType.YEAR, yearWheel);
            put(WheelType.MONTH,monthWheel);
            put(WheelType.DATE, dateWheel);
            put(WheelType.HOUR, hourWheel);
            put(WheelType.MINUTE, minutesWheel);
            put(WheelType.AM_PM, ampmWheel);
        }};
    }

    public boolean hasSpinningWheel() {
        for(Wheel wheel: getAll()) {
            if(wheel.picker.isSpinning()) return true;
        }
        return false;
    }
}
