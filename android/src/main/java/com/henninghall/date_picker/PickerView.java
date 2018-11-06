package com.henninghall.date_picker;

import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.UpdateVisibility;
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DateWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;
import com.henninghall.date_picker.wheels.MonthWheel;
import com.henninghall.date_picker.wheels.Wheel;
import com.henninghall.date_picker.wheels.YearWheel;

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

    private SimpleDateFormat dateFormat;
    private HourWheel hourWheel;
    private DayWheel dayWheel;
    public MinutesWheel minutesWheel;
    private AmPmWheel ampmWheel;
    public int minuteInterval = 1;
    public Locale locale;
    public Mode mode;
    public Style style;
    public DateWheel dateWheel;
    public MonthWheel monthWheel;
    public YearWheel yearWheel;
    public Date maxDate;
    public Date minDate;
    private WheelOrderUpdater wheelOrderUpdater;
    public boolean isInitialized = false;
    public boolean requireDisplayValueUpdate = true;


    public PickerView() {
        super(DatePickerManager.context);
        View rootView = inflate(getContext(), R.layout.datepicker_view, this);
        this.style = new Style(this);
        this.wheelOrderUpdater = new WheelOrderUpdater(this);

        RelativeLayout wheelsWrapper = (RelativeLayout) rootView.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);

        locale = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ? Locale.forLanguageTag("en") : Locale.getDefault();

        yearWheel = new YearWheel( this, R.id.year);
        monthWheel = new MonthWheel( this, R.id.month);
        dateWheel = new DateWheel( this, R.id.date);

        dayWheel = new DayWheel( this, R.id.day);
        minutesWheel = new MinutesWheel( this, R.id.minutes);
        ampmWheel = new AmPmWheel(this, R.id.ampm);
        hourWheel = new HourWheel(this, R.id.hour);

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
                    event.putString("date", Utils.dateToIso(date));
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
        hourWheel.picker.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                if(Utils.usesAmPm(locale)){
                    String oldValue = hourWheel.getValueAtIndex(oldVal);
                    String newValue = hourWheel.getValueAtIndex(newVal);
                    boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || oldValue.equals("11") && newValue.equals("12");
                    if (passingNoonOrMidnight) ampmWheel.picker.smoothScrollToValue((ampmWheel.picker.getValue() + 1) % 2,false);
                }
            }
        });
    }

    public void setMinimumDate(Date date) {
        minDate = Utils.getTruncatedDateOrNull(date);
        requireDisplayValueUpdate = true;
    }

    public void setMaximumDate(Date date) {
        maxDate = Utils.getTruncatedDateOrNull(date);
        requireDisplayValueUpdate = true;
    }

    public void setDate(Date date) {
        applyOnAllWheels(new SetDate(date));
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
        wheelOrderUpdater.update(locale, mode);
        requireDisplayValueUpdate = true;
    }

    public void setMinuteInterval(int interval) {
        this.minuteInterval = interval;
        requireDisplayValueUpdate = true;
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
        String dateTemplate = (mode == Mode.date)
                ? (yearWheel.getFormatTemplate() + " "
                + monthWheel.getFormatTemplate() + " "
                + dateWheel.getFormatTemplate())
                : dayWheel.getFormatTemplate();
        return dateTemplate + " "
                + hourWheel.getFormatTemplate() + " "
                + minutesWheel.getFormatTemplate()
                +  ampmWheel.getFormatTemplate();
    }

    private String getDateString() {
        String dateString= (mode == Mode.date)
                ? (yearWheel.getValue() + " "
                + monthWheel.getValue() + " "
                + dateWheel.getValue())
                : dayWheel.getValue();
        return dateString
                + " " + hourWheel.getValue()
                + " " + minutesWheel.getValue()
                + ampmWheel.getValue();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
        applyOnAllWheels(new UpdateVisibility());
        wheelOrderUpdater.update(locale, mode);
    }

    public Collection<Wheel> getVisibleWheels() {
        Collection<Wheel> visibleWheels = new ArrayList<>();
        for (Wheel wheel: getAllWheels()) if (wheel.visible()) visibleWheels.add(wheel);
        return visibleWheels;
    }

    public List<Wheel> getAllWheels(){
        return new ArrayList<>(Arrays.asList(yearWheel, monthWheel, dateWheel, dayWheel, hourWheel, minutesWheel, ampmWheel));
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

    public void updateDisplayValuesIfNeeded() {
        if(requireDisplayValueUpdate) {
            applyOnAllWheels(new Refresh());
            requireDisplayValueUpdate = false;
        }
    }
}
