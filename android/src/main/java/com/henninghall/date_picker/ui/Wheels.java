package com.henninghall.date_picker.ui;

import android.view.View;
import android.widget.LinearLayout;

import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.models.WheelType;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.wheelFunctions.AddOnChangeListener;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

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
    private final LinearLayout wheelsWrapper;
    private final EmptyWheels emptyWheels;

    private HashMap<WheelType, Wheel> wheelPerWheelType;
    private UIManager uiManager;

    Wheels(State state, View rootView, UIManager uiManager){
        this.state = state;
        this.rootView = rootView;
        this.uiManager = uiManager;
        wheelsWrapper = (LinearLayout) rootView.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);

        yearWheel = new YearWheel(getPickerWithId(R.id.year), state);
        monthWheel = new MonthWheel(getPickerWithId(R.id.month), state);
        dateWheel = new DateWheel(getPickerWithId(R.id.date), state);
        dayWheel = new DayWheel(getPickerWithId(R.id.day), state);
        minutesWheel = new MinutesWheel(getPickerWithId(R.id.minutes), state);
        ampmWheel = new AmPmWheel(getPickerWithId(R.id.ampm), state);
        hourWheel = new HourWheel(getPickerWithId(R.id.hour), state);
        wheelPerWheelType = getWheelPerType();

        changeAmPmWhenPassingMidnightOrNoon();
        addOnChangeListener();
        emptyWheels = new EmptyWheels(rootView,this,state);
    }

    private void addOnChangeListener(){
        WheelChangeListener onWheelChangeListener = new WheelChangeListenerImpl(this, state, uiManager, rootView);
        applyOnAll(new AddOnChangeListener(onWheelChangeListener));
    }

    private NumberPickerView getPickerWithId(int id){
        return (NumberPickerView) rootView.findViewById(id);
    }

    private Collection<Wheel> getVisible() {
        ArrayList<WheelType> wheelTypes = state.getVisibleWheels();
        Collection<Wheel> wheels = new ArrayList<>();
        for (WheelType type: wheelTypes){
            wheels.add(getWheel(type));
        }
        return wheels;
    }

    void applyOnAll(WheelFunction function) {
        for (Wheel wheel: getAll()) function.apply(wheel);
    }

    void applyOnVisible(WheelFunction function) {
        for (Wheel wheel: getVisible()) function.apply(wheel);
    }

    Wheel getWheel(WheelType type){
        return wheelPerWheelType.get(type);
    }

    void addInOrder(){
        ArrayList<WheelType> wheels = state.getOrderedVisibleWheels();
        for (WheelType wheelType : wheels) {
            Wheel wheel = getWheel(wheelType);
            addWheel(wheel.picker);
        }
    }

    private ArrayList<Wheel> getOrderedWheels(){
        ArrayList<Wheel> list = new ArrayList<>();
        for (WheelType type : state.getOrderedVisibleWheels()) {
            list.add(getWheel(type));
        }
        return list;
    }

    void addWheel(View wheel) { wheelsWrapper.addView(wheel); }

    void addWheel(View wheel, int index) { wheelsWrapper.addView(wheel,index); }

    void removeAll() {
        wheelsWrapper.removeAllViews();
    }

    private void changeAmPmWhenPassingMidnightOrNoon() {
        hourWheel.picker.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                if(Utils.usesAmPm()){
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
        ArrayList<Wheel> wheels = getOrderedWheels();
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

    String getDateString() {
        ArrayList<Wheel> wheels = getOrderedWheels();

        String dateString = (state.getMode() == Mode.date)
                ? wheels.get(0).getValue() + " "
                + wheels.get(1).getValue() + " "
                + wheels.get(2).getValue()
                : dayWheel.getValue();
        return dateString
                + " " + hourWheel.getValue()
                + " " + minutesWheel.getValue()
                + ampmWheel.getValue();
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

    void addEmpty() {
        emptyWheels.add();
    }
}
