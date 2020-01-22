package com.henninghall.date_picker;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class WheelChangeListenerImpl implements WheelChangeListener {


    private final PickerView pickerView;

    public WheelChangeListenerImpl(PickerView pickerView) {
        this.pickerView = pickerView;
    }

    @Override
    public void onChange(Wheel picker) {
        WritableMap event = Arguments.createMap();
        TimeZone timeZone = pickerView.timeZone;
        SimpleDateFormat dateFormat = pickerView.dateFormat;
        Calendar minDate = pickerView.getMinimumDate();
        Calendar maxDate = pickerView.getMaximumDate();
        try {
            dateFormat.setTimeZone(timeZone);
            Calendar date = Calendar.getInstance(timeZone);
            String toParse = this.pickerView.getDateString();
            date.setTime(dateFormat.parse(toParse));

            if (minDate != null && date.before(minDate)) pickerView.applyOnVisibleWheels(
                    new AnimateToDate(minDate)
            );
            else if (maxDate != null && date.after(maxDate)) pickerView.applyOnVisibleWheels(
                    new AnimateToDate(maxDate)
            );
            else {
                event.putString("date", Utils.dateToIso(date));
                DatePickerManager.context.getJSModule(RCTEventEmitter.class).receiveEvent(pickerView.getId(), "dateChange", event);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
