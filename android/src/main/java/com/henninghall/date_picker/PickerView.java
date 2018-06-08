package com.henninghall.date_picker;

import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;
import com.henninghall.date_picker.wheels.Wheel;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class PickerView extends RelativeLayout {

    private final RelativeLayout wheelsWrapper;
    private final NumberPickerView hourPicker;
    private final NumberPickerView ampmPicker;
    private SimpleDateFormat dateFormat;
    private HourWheel hourWheel;
    private DayWheel dayWheel;
    private MinutesWheel minutesWheel;
    private AmPmWheel ampmWheel;
    private Date minDate;
    private Date maxDate;

    public PickerView() {
        super(DatePickerManager.context);
        View rootView = inflate(getContext(), R.layout.datepicker_view, this);

        wheelsWrapper = (RelativeLayout) rootView.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);

        Locale locale = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ? Locale.forLanguageTag("en") : Locale.getDefault();

        NumberPickerView dayPicker = (NumberPickerView) rootView.findViewById(R.id.day);
        dayWheel = new DayWheel(dayPicker, onWheelChangeListener, locale);

        NumberPickerView minutePicker = (NumberPickerView) rootView.findViewById(R.id.minutes);
        minutesWheel = new MinutesWheel(minutePicker, onWheelChangeListener, locale);

        ampmPicker = (NumberPickerView) rootView.findViewById(R.id.ampm);
        ampmWheel = new AmPmWheel(ampmPicker, onWheelChangeListener, locale);

        hourPicker = (NumberPickerView) rootView.findViewById(R.id.hour);
        hourWheel = new HourWheel(hourPicker, onWheelChangeListener, locale);

        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
        changeAmPmWhenPassingMidnightOrNoon();

    }

    WheelChangeListener onWheelChangeListener = new WheelChangeListener(){
        @Override
        public void onChange(Wheel wheel) {
            WritableMap event = Arguments.createMap();
            try {
                Date date = dateFormat.parse(getDateString());
                if (date.before(minDate)) wheel.animateToDate(minDate);
                if (date.after(maxDate)) wheel.animateToDate(maxDate);
                else {
                    event.putDouble("date", date.getTime());
                    DatePickerManager.context.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "dateChange", event);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };


    private String getDateFormatTemplate() {
        return dayWheel.getFormatTemplate() + " "
                + hourWheel.getFormatTemplate() + " "
                + minutesWheel.getFormatTemplate()
               +  ampmWheel.getFormatTemplate();
    }

    private String getDateString() {
        return dayWheel.getValue()
                + " " + hourWheel.getValue()
                + " " + minutesWheel.getValue()
                + ampmWheel.getValue();
    }

    public void setMinimumDate(Date date) {
        minDate = DateUtils.truncate(date, Calendar.MINUTE);
    }

    public void setMaximumDate(Date date) {
        maxDate = DateUtils.truncate(date, Calendar.MINUTE);
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

        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private void changeAmPmWhenPassingMidnightOrNoon(){
        hourPicker.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                if(Utils.usesAmPm(hourWheel.getLocale())){
                    String oldValue = hourWheel.getValueAtIndex(oldVal);
                    String newValue = hourWheel.getValueAtIndex(newVal);
                    boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || oldValue.equals("11") && newValue.equals("12");
                    if (passingNoonOrMidnight) ampmPicker.smoothScrollToValue((ampmPicker.getValue() + 1) % 2);
                }
            }
        });

    }
}
