package com.henninghall.date_picker;

import android.util.Log;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.WheelType;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.HeightProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.Prop;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.UtcProp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class State {

    private final Prop dateProp = new DateProp();
    private final Prop modeProp = new ModeProp();
    private final Prop localeProp = new LocaleProp();
    private final Prop fadeToColorProp = new FadeToColorProp();
    private final Prop textColorProp = new TextColorProp();
    private final Prop minuteIntervalProp = new MinuteIntervalProp();
    private final Prop minimumDateProp = new MinimumDateProp();
    private final Prop maximumDateProp = new MaximumDateProp();
    private final Prop utcProp = new UtcProp();
    private final Prop heightProp = new HeightProp();

    private final HashMap props = new HashMap<String, Prop>() {{
        put(DateProp.name, dateProp);
        put(ModeProp.name, modeProp);
        put(LocaleProp.name, localeProp);
        put(FadeToColorProp.name, fadeToColorProp);
        put(TextColorProp.name, textColorProp);
        put(MinuteIntervalProp.name, minuteIntervalProp);
        put(MinimumDateProp.name, minimumDateProp);
        put(MaximumDateProp.name, maximumDateProp);
        put(UtcProp.name, utcProp);
    }};

    private Prop getProp(String name){
        return (Prop) props.get(name);
    }

    void setProp(String propName, Dynamic value){
        getProp(propName).setValue(value);
    }

    public Mode getMode() {
        return (Mode) modeProp.getValue();
    }

    public String getFadeToColor() {
        return (String) fadeToColorProp.getValue();
    }

    public String getTextColor() {
        return (String) textColorProp.getValue();
    }

    public int getMinuteInterval() {
        return (int) minuteIntervalProp.getValue();
    }

    public Locale getLocale() {
        return (Locale) localeProp.getValue();
    }

    public Calendar getMinimumDate(){
        DateBoundary db = new DateBoundary(getTimeZone(), (String) minimumDateProp.getValue());
        return db.get();
    }

    public Calendar getMaximumDate(){
        DateBoundary db = new DateBoundary(getTimeZone(), (String) maximumDateProp.getValue());
        return db.get();
    }

    public TimeZone getTimeZone(){
        boolean utc = (boolean) utcProp.getValue();
        return utc ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault();
    }

    public Calendar getDate() {
        String date = (String) dateProp.getValue();
        return Utils.isoToCalendar(date, getTimeZone());
    }

    public Integer getHeight() {
        return (Integer) heightProp.getValue();
    }

    public int getShownCount() {
        int DP_PER_SHOW_SHOW_COUNT = 35;
        int showCount = getHeight() / DP_PER_SHOW_SHOW_COUNT;
        int oddShowCount = showCount % 2 == 0 ? showCount + 1 : showCount;
        return oddShowCount;
    }

    public ArrayList<WheelType> getOrderedWheels() {
        String dateTimePattern = LocaleUtils.getDateTimePattern(getLocale());
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

    public ArrayList<WheelType> getVisibleWheels() {
        ArrayList<WheelType> visibleWheels = new ArrayList<>();
        Mode mode = getMode();
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

    // Rounding cal to closest minute interval
    public Calendar getInitialDate() {
        Calendar cal = Calendar.getInstance();
        int minuteInterval = getMinuteInterval();
        if(minuteInterval <= 1) return cal;
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", getLocale());
        int exactMinute = Integer.valueOf(minuteFormat.format(cal.getTime()));
        int diffSinceLastInterval = exactMinute % minuteInterval;
        int diffAhead = minuteInterval - diffSinceLastInterval;
        int diffBehind= -diffSinceLastInterval;
        boolean closerToPrevious = minuteInterval / 2 > diffSinceLastInterval;
        int diffToExactValue = closerToPrevious ? diffBehind : diffAhead;
        cal.add(Calendar.MINUTE, diffToExactValue);
        return (Calendar) cal.clone();
    }


    public WheelType getVisibleWheel(int index) {
        return getOrderedVisibleWheels().get(index);
    }
}
