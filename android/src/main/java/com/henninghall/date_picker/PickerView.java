package com.henninghall.date_picker;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;
import com.henninghall.date_picker.wheels.Wheel;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class PickerView extends RelativeLayout {

    private final NumberPickerView hourPicker;
    private final NumberPickerView ampmPicker;
    private SimpleDateFormat dateFormat;
    private HourWheel hourWheel;
    private DayWheel dayWheel;
    public MinutesWheel minutesWheel;
    private AmPmWheel ampmWheel;
    private Date minDate;
    private Date maxDate;
    public int minuteInterval = 1;
    public Locale locale;
    public Mode mode;
    public Style style;

    public PickerView() {
        super(DatePickerManager.context);
        View rootView = inflate(getContext(), R.layout.datepicker_view, this);
        this.style = new Style(this);

        RelativeLayout wheelsWrapper = (RelativeLayout) rootView.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);

        locale = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ? Locale.forLanguageTag("en") : Locale.getDefault();

        NumberPickerView dayPicker = (NumberPickerView) rootView.findViewById(R.id.day);
        dayWheel = new DayWheel(dayPicker, this);

        NumberPickerView minutePicker = (NumberPickerView) rootView.findViewById(R.id.minutes);
        minutesWheel = new MinutesWheel(minutePicker, this);

        ampmPicker = (NumberPickerView) rootView.findViewById(R.id.ampm);
        ampmWheel = new AmPmWheel(ampmPicker, this);

        hourPicker = (NumberPickerView) rootView.findViewById(R.id.hour);
        hourWheel = new HourWheel(hourPicker,this);

        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
        changeAmPmWhenPassingMidnightOrNoon();
    }

    WheelChangeListener onWheelChangeListener = new WheelChangeListener(){
        @Override
        public void onChange(Wheel wheel) {
            WritableMap event = Arguments.createMap();
            try {
                Date date = dateFormat.parse(getDateString());
                if (minDate != null && date.before(minDate)) applyOnVisibleWheels(new AnimateToDate(minDate));
                else if (maxDate != null && date.after(maxDate)) applyOnVisibleWheels(new AnimateToDate(maxDate));
                else {
                    event.putDouble("date", date.getTime());
                    DatePickerManager.context.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "dateChange", event);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    private void changeAmPmWhenPassingMidnightOrNoon(){
        hourPicker.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                if(Utils.usesAmPm(locale)){
                    String oldValue = hourWheel.getValueAtIndex(oldVal);
                    String newValue = hourWheel.getValueAtIndex(newVal);
                    boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || oldValue.equals("11") && newValue.equals("12");
                    if (passingNoonOrMidnight) ampmPicker.smoothScrollToValue((ampmPicker.getValue() + 1) % 2,false);
                }
            }
        });
    }

    public void setMinimumDate(Date date) {
        minDate = DateUtils.truncate(date, Calendar.MINUTE);
    }

    public void setMaximumDate(Date date) {
        maxDate = DateUtils.truncate(date, Calendar.MINUTE);
    }

    public void setDate(Date date) {
        applyOnVisibleWheels(new SetDate(date));
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
        applyOnAllWheels(new Refresh());
    }

    public void setMinuteInterval(int interval) {
        this.minuteInterval = interval;
        applyOnVisibleWheels(new Refresh());
    }

    // Rounding cal to closest minute interval
    public Calendar getInitialDate() {
        Calendar cal = Calendar.getInstance();
        if(minuteInterval <= 1) return cal;
        int exactMinute = Integer.valueOf(minutesWheel.format.format(cal.getTime()));
        int diffSinceLastInterval = exactMinute % minuteInterval;
        int diffAhead = minuteInterval - diffSinceLastInterval;
        int diffBehind= -diffSinceLastInterval;
        boolean closerToPrevious = minuteInterval / 2 > diffSinceLastInterval;
        int diffToExactValue = closerToPrevious ? diffBehind : diffAhead;
        cal.add(Calendar.MINUTE, diffToExactValue);
        return (Calendar) cal.clone();
    }

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

    public void setMode(Mode mode) {
        this.mode = mode;
        applyOnAllWheels(new Refresh());
    }

    public Collection<Wheel> getVisibleWheels() {
        Collection<Wheel> visibleWheels = new ArrayList<>();
        for (Wheel wheel: getAllWheels()) if (wheel.visible()) visibleWheels.add(wheel);
        return visibleWheels;
    }

    public List<Wheel> getAllWheels(){
        return new ArrayList<>(Arrays.asList(dayWheel, hourWheel, minutesWheel, ampmWheel));
    }

    public void applyOnAllWheels(WheelFunction function) {
        for (Wheel wheel: getAllWheels()) function.apply(wheel);
    }

    public void applyOnVisibleWheels(WheelFunction function) {
        for (Wheel wheel: getVisibleWheels()) function.apply(wheel);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    public WheelChangeListener getListener() {
        return onWheelChangeListener;
    }

}
