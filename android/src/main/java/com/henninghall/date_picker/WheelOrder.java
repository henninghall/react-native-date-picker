package com.henninghall.date_picker;

import com.henninghall.date_picker.wheels.Wheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class WheelOrder
{

    private final HashMap<WheelType, Wheel> wheelPerWheelType;
    private PickerView pickerView;
    private ArrayList<WheelType> orderedWheels;

    WheelOrder(final PickerView view) {
        this.pickerView = view;
        this.wheelPerWheelType = new HashMap<WheelType, Wheel>() {{
            put(WheelType.DAY, pickerView.dayWheel);
            put(WheelType.YEAR, pickerView.yearWheel);
            put(WheelType.MONTH, pickerView.monthWheel);
            put(WheelType.DATE, pickerView.dateWheel);
            put(WheelType.HOUR, pickerView.hourWheel);
            put(WheelType.MINUTE, pickerView.minutesWheel);
            put(WheelType.AM_PM, pickerView.ampmWheel);
        }};
    }

    private void updateValueWheels(final Locale locale) {
        try {
            this.orderedWheels = getOrderedWheels(locale);
            pickerView.wheelsWrapper.removeAllViews();
            for (int i = 0; i < wheelPerWheelType.size(); i++) {
                Wheel w = getWheels(i);
                if(w.visible()) {
                    pickerView.wheelsWrapper.addView(w.picker);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void update(final Locale locale) {
        updateValueWheels(locale);
        pickerView.emptyWheelUpdater.update();
    }

    private Wheel getWheels(int index){
        return wheelPerWheelType.get(orderedWheels.get(index));
    }

    Wheel getVisibleWheel(int index){
        return getVisibleWheels().get(index);
    }

    private ArrayList<Wheel> getVisibleWheels() {
        ArrayList<Wheel> visibleOrderedWheels = new ArrayList<>();
        for (WheelType wheelType : orderedWheels){
            Wheel wheel = wheelPerWheelType.get(wheelType);
            if(wheel.visible()) {
                visibleOrderedWheels.add(wheel);
            }
        }
        return visibleOrderedWheels;
    }

    private ArrayList<WheelType> getOrderedWheels(Locale locale) throws Exception {
        String dateTimePattern = LocaleUtils.getDateTimePattern(locale);
        ArrayList<WheelType> unorderedTypes = new ArrayList(Arrays.asList(WheelType.values()));
        ArrayList<WheelType> orderedWheels = new ArrayList<>();

        // Always put day wheel first
        unorderedTypes.remove(WheelType.DAY);
        orderedWheels.add(WheelType.DAY);

        for (char ch : dateTimePattern.toCharArray()){
            try {
                WheelType wheelType = Utils.patternCharToWheelType(ch);
                if (unorderedTypes.contains(wheelType)) {
                    unorderedTypes.remove(wheelType);
                    orderedWheels.add(wheelType);
                }
            } catch (Exception e) {
                // ignore unknown pattern chars that not correspond to any wheel type
            }
        }
        // If AM/PM wheel remains it means that the locale does not have AM/PM by default and it
        // should be put last.
        if(unorderedTypes.contains(WheelType.AM_PM)){
            unorderedTypes.remove(WheelType.AM_PM);
            orderedWheels.add(WheelType.AM_PM);
        }

        if(!unorderedTypes.isEmpty()) {
            throw new Exception(unorderedTypes.size() + " wheel types cannot be ordered. Wheel type 0: " + unorderedTypes.get(0));
        }

        return orderedWheels;
    }




}

