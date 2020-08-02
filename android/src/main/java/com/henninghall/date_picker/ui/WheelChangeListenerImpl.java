package com.henninghall.date_picker.ui;

import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.DatePickerManager;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WheelChangeListenerImpl implements WheelChangeListener {

    private final Wheels wheels;
    private final State state;
    private final UIManager uiManager;
    private final View rootView;

    WheelChangeListenerImpl(Wheels wheels, State state, UIManager uiManager, View rootView) {
        this.wheels = wheels;
        this.uiManager = uiManager;
        this.state = state;
        this.rootView = rootView;
    }

    @Override
    public void onChange(Wheel picker) {
        if(wheels.hasSpinningWheel()) return;

        WritableMap event = Arguments.createMap();
        TimeZone timeZone = state.getTimeZone();
        SimpleDateFormat dateFormat = uiManager.getDateFormat();
        Calendar minDate = state.getMinimumDate();
        Calendar maxDate = state.getMaximumDate();
        try {
            dateFormat.setTimeZone(timeZone);
            Calendar date = Calendar.getInstance(timeZone);
            String toParse = wheels.getDateString();
            Date newDate = dateFormat.parse(toParse);
            date.setTime(newDate);
            String dateString = Utils.dateToIso(date);
            if (minDate != null && date.before(minDate)) uiManager.animateToDate(minDate);
            else if (maxDate != null && date.after(maxDate)) uiManager.animateToDate(maxDate);
            else {
                event.putString("date", dateString);
                event.putString("dateString", uiManager.getDisplayValueString());
                DatePickerManager.context.getJSModule(RCTEventEmitter.class)
                        .receiveEvent(rootView.getId(), "dateChange", event);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
