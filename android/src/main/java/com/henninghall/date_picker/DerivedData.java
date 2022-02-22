package com.henninghall.date_picker;

import android.text.format.DateFormat;
import android.util.Log;
import android.util.TimeUtils;

import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.Variant;
import com.henninghall.date_picker.models.WheelType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.henninghall.date_picker.models.Is24HourSource.*;

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
        if((mode == Mode.time || mode == Mode.datetime) && state.derived.usesAmPm()){
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
        String dateTimePatternOld = LocaleUtils.getDateTimePattern(state.getLocale());
        String dateTimePattern = dateTimePatternOld.replaceAll("\\('(.+?)'\\)","\\${$1}")
                .replaceAll("'.+?'","")
                .replaceAll("\\$\\{(.+?)\\}","('$1')");
        ArrayList<WheelType> unorderedTypes = new ArrayList(Arrays.asList(WheelType.values()));
        ArrayList<WheelType> orderedWheels = new ArrayList<>();

        // Always put day wheel first
        unorderedTypes.remove(WheelType.DAY);
        orderedWheels.add(WheelType.DAY);

        for (char c: dateTimePattern.toCharArray()){
            try {
                WheelType wheelType = Utils.patternCharToWheelType(c);
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

    public boolean hasNativeStyle() {
        return state.getVariant() == Variant.nativeAndroid;
    }

    public int getRootLayout() {
        switch (state.getVariant()){
            case nativeAndroid: return R.layout.native_picker;
            case iosClone: return R.layout.ios_clone;
            default: return R.layout.ios_clone;
        }
    }

    public boolean usesAmPm(){
        if(state.getIs24HourSource() == locale) return LocaleUtils.localeUsesAmPm(state.getLocale());
        return Utils.deviceUsesAmPm();
    }

    public boolean hasOnly2Wheels(){
        return state.getMode() == Mode.time && !usesAmPm();
    }


    public String getLastDate() {
        Calendar lastSelectedDate = state.getLastSelectedDate();
        String initialDate = state.getIsoDate();
        if(lastSelectedDate != null) return Utils.dateToIso(lastSelectedDate);
        return initialDate;
    }
}
