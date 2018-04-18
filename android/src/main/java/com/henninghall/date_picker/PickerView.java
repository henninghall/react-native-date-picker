package com.henninghall.date_picker;

import android.view.View;
import android.view.ViewManager;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class PickerView extends RelativeLayout {

    private SimpleDateFormat dateFormat;
    private HourWheel hourWheel;
    private DayWheel dayWheel;
    private MinutesWheel minutesWheel;
    private AmPmWheel ampmWheel;

    public PickerView() {
        super(DatePickerManager.context);
        View rootView = inflate(getContext(), R.layout.datepicker_view, this);
        Locale locale = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ? Locale.forLanguageTag("en") : Locale.getDefault();

        NumberPickerView hourPicker = (NumberPickerView) rootView.findViewById(R.id.hour);
        hourWheel = new HourWheel(hourPicker, onWheelChangeListener, locale);

        NumberPickerView dayPicker= (NumberPickerView) rootView.findViewById(R.id.day);
        dayWheel = new DayWheel(dayPicker, onWheelChangeListener, locale);

        NumberPickerView minutePicker = (NumberPickerView) rootView.findViewById(R.id.minutes);
        minutesWheel = new MinutesWheel(minutePicker, onWheelChangeListener, locale);

        NumberPickerView ampmPicker = (NumberPickerView) rootView.findViewById(R.id.ampm);
        ampmWheel = new AmPmWheel(ampmPicker, onWheelChangeListener, locale);

        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), locale);

        ((ViewManager) ampmPicker.getParent()).removeView(ampmPicker);
    }

    WheelChangeListener onWheelChangeListener = new WheelChangeListener(){
        @Override
        public void onChange() {
            WritableMap event = Arguments.createMap();
            try {
                Date date = dateFormat.parse(getDateString());
                event.putDouble("date", date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DatePickerManager.context.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "dateChange", event);
        }
    };


    private String getDateFormatTemplate() {
        return dayWheel.getFormatTemplate() + " "
                + hourWheel.getFormatTemplate() + " "
                + minutesWheel.getFormatTemplate() + " "
                + ampmWheel.getFormatTemplate();
    }

    private String getDateString() {
        return dayWheel.getValue()
                + " " + hourWheel.getValue()
                + " " + minutesWheel.getValue()
                + " " + ampmWheel.getValue();
    }

    public void setDate(Date date) {
        dayWheel.setValue(date);
        hourWheel.setValue(date);
        minutesWheel.setValue(date);
        ampmWheel.setValue(date);
    }

    public void setLocale(Locale locale) {
        dayWheel.setLocale(locale);
        hourWheel.setLocale(locale);
        minutesWheel.setLocale(locale);
        ampmWheel.setLocale(locale);
    }
}
