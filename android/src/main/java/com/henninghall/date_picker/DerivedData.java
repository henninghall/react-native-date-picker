package com.henninghall.date_picker;

import android.util.Log;

import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.WheelType;

import java.util.ArrayList;
import java.util.Arrays;

public class DerivedData {
    private final State state;

    DerivedData(State state) {
        this.state = state;
    }

    public ArrayList<WheelType> getVisibleWheels() {
        ArrayList<WheelType> visibleWheels = new ArrayList<>();
        Mode mode = state.getMode();
        switch (mode){
            case datetime: {
                visibleWheels.add(WheelType.DAY);
                visibleWheels.add(WheelType.HOUR);
                visibleWheels.add(WheelType.MINUTE);
                break;
            }
            case time: {
                visibleWheels.add(WheelType.HOUR);
                visibleWheels.add(WheelType.MINUTE);
                break;
            }
            case date: {
                visibleWheels.add(WheelType.YEAR);
                visibleWheels.add(WheelType.MONTH);
                visibleWheels.add(WheelType.DATE);
                break;
            }
        }
        if((mode == Mode.time || mode == Mode.datetime) && Utils.usesAmPm()){
            visibleWheels.add(WheelType.AM_PM);
        }
        return visibleWheels;
    }

    public ArrayList<WheelType> getOrderedVisibleWheels() {
        ArrayList<WheelType> orderedWheels = getOrderedWheels();
        ArrayList<WheelType> visibleWheels = getVisibleWheels();
        ArrayList<WheelType> visibleOrderedWheels = new ArrayList<>();
        for (WheelType wheel : orderedWheels){
            if(visibleWheels.contains(wheel)) visibleOrderedWheels.add(wheel);
        }
        return visibleOrderedWheels;
    }

    private ArrayList<WheelType> getOrderedWheels() {
        String dateTimePattern = LocaleUtils.getDateTimePattern(state.getLocale());
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
            Log.e(
                    "RNDatePicker",
                    unorderedTypes.size() + " wheel types cannot be ordered. Wheel type 0: " + unorderedTypes.get(0));
        }

        return orderedWheels;
    }

    public int getShownCount() {
        int DP_PER_SHOW_SHOW_COUNT = 35;
        int showCount = state.getHeight() / DP_PER_SHOW_SHOW_COUNT;
        int oddShowCount = showCount % 2 == 0 ? showCount + 1 : showCount;
        return oddShowCount;
    }

}
