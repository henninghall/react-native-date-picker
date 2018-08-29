package com.henninghall.date_picker;

import android.widget.*;
import cn.carbswang.android.numberpickerview.library.*;
import android.content.*;
import com.henninghall.date_picker.wheels.*;
import com.facebook.react.uimanager.events.*;
import java.text.*;
import com.facebook.react.bridge.*;
import android.view.*;
import android.os.*;
import org.apache.commons.lang3.time.*;
import com.henninghall.date_picker.wheelFunctions.*;
import java.util.*;

public class PickerView extends RelativeLayout
{
    private final NumberPickerView hourPicker;
    private final NumberPickerView ampmPicker;
    private SimpleDateFormat dateFormat;
    private HourWheel hourWheel;
    private DayWheel dayWheel;
    public MinutesWheel minutesWheel;
    private AmPmWheel ampmWheel;
    private Date minDate;
    private Date maxDate;
    public int minuteInterval;
    public Locale locale;
    public Mode mode;
    public Style style;
    WheelChangeListener onWheelChangeListener;
    private final Runnable measureAndLayout;
    
    public PickerView() {
        super((Context)DatePickerManager.context);
        this.minuteInterval = 1;
        this.onWheelChangeListener = new WheelChangeListener() {
            @Override
            public void onChange(final Wheel wheel) {
                final WritableMap event = Arguments.createMap();
                try {
                    final Date date = PickerView.this.dateFormat.parse(PickerView.this.getDateString());
                    if (PickerView.this.minDate != null && date.before(PickerView.this.minDate)) {
                        PickerView.this.applyOnVisibleWheels(new AnimateToDate(PickerView.this.minDate));
                    }
                    else if (PickerView.this.maxDate != null && date.after(PickerView.this.maxDate)) {
                        PickerView.this.applyOnVisibleWheels(new AnimateToDate(PickerView.this.maxDate));
                    }
                    else {
                        event.putDouble("date", (double)date.getTime());
                        ((RCTEventEmitter)DatePickerManager.context.getJSModule((Class)RCTEventEmitter.class)).receiveEvent(PickerView.this.getId(), "dateChange", event);
                    }
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        this.measureAndLayout = new Runnable() {
            @Override
            public void run() {
                PickerView.this.measure(View.MeasureSpec.makeMeasureSpec(PickerView.this.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(PickerView.this.getHeight(), 1073741824));
                PickerView.this.layout(PickerView.this.getLeft(), PickerView.this.getTop(), PickerView.this.getRight(), PickerView.this.getBottom());
            }
        };
        final View rootView = inflate(this.getContext(), R.layout.datepicker_view, (ViewGroup)this);
        this.style = new Style(this);
        final RelativeLayout wheelsWrapper = (RelativeLayout)rootView.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);
        this.locale = ((Build.VERSION.SDK_INT >= 21) ? Locale.forLanguageTag("en") : Locale.getDefault());
        final NumberPickerView dayPicker = (NumberPickerView)rootView.findViewById(R.id.day);
        this.dayWheel = new DayWheel(dayPicker, this);
        final NumberPickerView minutePicker = (NumberPickerView)rootView.findViewById(R.id.minutes);
        this.minutesWheel = new MinutesWheel(minutePicker, this);
        this.ampmPicker = (NumberPickerView)rootView.findViewById(R.id.ampm);
        this.ampmWheel = new AmPmWheel(this.ampmPicker, this);
        this.hourPicker = (NumberPickerView)rootView.findViewById(R.id.hour);
        this.hourWheel = new HourWheel(this.hourPicker, this);
        this.dateFormat = new SimpleDateFormat(this.getDateFormatTemplate(), Locale.US);
        this.changeAmPmWhenPassingMidnightOrNoon();
    }
    
    private void changeAmPmWhenPassingMidnightOrNoon() {
        this.hourPicker.setOnValueChangeListenerInScrolling((NumberPickerView.OnValueChangeListenerInScrolling)new NumberPickerView.OnValueChangeListenerInScrolling() {
            public void onValueChangeInScrolling(final NumberPickerView picker, final int oldVal, final int newVal) {
                if (Utils.usesAmPm(PickerView.this.locale)) {
                    final String oldValue = PickerView.this.hourWheel.getValueAtIndex(oldVal);
                    final String newValue = PickerView.this.hourWheel.getValueAtIndex(newVal);
                    final boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || (oldValue.equals("11") && newValue.equals("12"));
                    if (passingNoonOrMidnight) {
                        PickerView.this.ampmPicker.smoothScrollToValue((PickerView.this.ampmPicker.getValue() + 1) % 2, false);
                    }
                }
            }
        });
    }
    
    public void setMinimumDate(final Date date) {
        this.minDate = DateUtils.truncate(date, 12);
    }
    
    public void setMaximumDate(final Date date) {
        this.maxDate = DateUtils.truncate(date, 12);
    }
    
    public void setDate(final Date date) {
        this.applyOnAllWheels(new SetDate(date));
    }
    
    public void setLocale(final Locale locale) {
        this.locale = locale;
        this.dateFormat = new SimpleDateFormat(this.getDateFormatTemplate(), Locale.US);
        this.applyOnAllWheels(new Refresh());
    }
    
    public void setMinuteInterval(final int interval) {
        this.minuteInterval = interval;
        this.applyOnVisibleWheels(new Refresh());
    }
    
    public Calendar getInitialDate() {
        final Calendar cal = Calendar.getInstance();
        if (this.minuteInterval <= 1) {
            return cal;
        }
        final int exactMinute = Integer.valueOf(this.minutesWheel.format.format(cal.getTime()));
        final int diffSinceLastInterval = exactMinute % this.minuteInterval;
        final int diffAhead = this.minuteInterval - diffSinceLastInterval;
        final int diffBehind = -diffSinceLastInterval;
        final boolean closerToPrevious = this.minuteInterval / 2 > diffSinceLastInterval;
        final int diffToExactValue = closerToPrevious ? diffBehind : diffAhead;
        cal.add(12, diffToExactValue);
        return (Calendar)cal.clone();
    }
    
    private String getDateFormatTemplate() {
        return this.dayWheel.getFormatTemplate() + " " + this.hourWheel.getFormatTemplate() + " " + this.minutesWheel.getFormatTemplate() + this.ampmWheel.getFormatTemplate();
    }
    
    private String getDateString() {
        return this.dayWheel.getValue() + " " + this.hourWheel.getValue() + " " + this.minutesWheel.getValue() + this.ampmWheel.getValue();
    }
    
    public void setMode(final Mode mode) {
        this.mode = mode;
        this.applyOnAllWheels(new Refresh());
    }
    
    public Collection<Wheel> getVisibleWheels() {
        final Collection<Wheel> visibleWheels = new ArrayList<Wheel>();
        for (final Wheel wheel : this.getAllWheels()) {
            if (wheel.visible()) {
                visibleWheels.add(wheel);
            }
        }
        return visibleWheels;
    }
    
    public List<Wheel> getAllWheels() {
        return new ArrayList<Wheel>(Arrays.asList(this.dayWheel, this.hourWheel, this.minutesWheel, this.ampmWheel));
    }
    
    public void applyOnAllWheels(final WheelFunction function) {
        for (final Wheel wheel : this.getAllWheels()) {
            function.apply(wheel);
        }
    }
    
    public void applyOnVisibleWheels(final WheelFunction function) {
        for (final Wheel wheel : this.getVisibleWheels()) {
            function.apply(wheel);
        }
    }
    
    public void requestLayout() {
        super.requestLayout();
        this.post(this.measureAndLayout);
    }
    
    public WheelChangeListener getListener() {
        return this.onWheelChangeListener;
    }
}

