package com.henninghall.date_picker;

import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class PickerView extends RelativeLayout {

    public LinearLayout wheelsWrapper;
    public SimpleDateFormat dateFormat;
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
    private WheelOrderUpdater wheelOrderUpdater;
    private EmptyWheelUpdater emptyWheelUpdater;
    public boolean requireDisplayValueUpdate = true;
    public TimeZone timeZone = TimeZone.getDefault();
    private DateBoundary minDate;
    private DateBoundary maxDate;
    private WheelChangeListener onWheelChangeListener = new WheelChangeListenerImpl(this);

    public PickerView() {
        super(DatePickerManager.context);

        View rootView = inflate(getContext(), R.layout.datepicker_view, this);
        this.style = new Style(this);
        this.wheelOrderUpdater = new WheelOrderUpdater(this);
        this.emptyWheelUpdater = new EmptyWheelUpdater(this);

        wheelsWrapper = (LinearLayout) rootView.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);

        locale = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? Locale.forLanguageTag("en") : Locale.getDefault();

        yearWheel = new YearWheel( this, R.id.year);
        monthWheel = new MonthWheel( this, R.id.month);
        dateWheel = new DateWheel( this, R.id.date);
        dayWheel = new DayWheel( this, R.id.day);
        minutesWheel = new MinutesWheel( this, R.id.minutes);
        ampmWheel = new AmPmWheel(this, R.id.ampm);
        hourWheel = new HourWheel(this, R.id.hour);

        setDateFormat();
        changeAmPmWhenPassingMidnightOrNoon();
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

    private void changeAmPmWhenPassingMidnightOrNoon(){
        hourWheel.picker.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                if(Settings.usesAmPm()){
                    String oldValue = hourWheel.getValueAtIndex(oldVal);
                    String newValue = hourWheel.getValueAtIndex(newVal);
                    boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || oldValue.equals("11") && newValue.equals("12");
                    if (passingNoonOrMidnight) ampmWheel.picker.smoothScrollToValue((ampmWheel.picker.getValue() + 1) % 2,false);
                }
            }
        });
    }


    public void setMinimumDate(String date) {
        minDate = new DateBoundary(this, date);
        requireDisplayValueUpdate = true;
    }

    public void setMaximumDate(String date) {
        maxDate = new DateBoundary(this, date);
        requireDisplayValueUpdate = true;
    }

    public void setDate(String isoDate) {
        Calendar cal = Utils.isoToCalendar(isoDate, timeZone);
        applyOnAllWheels(new SetDate(cal));
        update2DigitYearStart(cal);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        setDateFormat();
        wheelOrderUpdater.update(locale, mode);
        emptyWheelUpdater.update(mode);
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

    public String getDateString() {
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
        setDateFormat();
        applyOnAllWheels(new UpdateVisibility());
        wheelOrderUpdater.update(locale, mode);
        emptyWheelUpdater.update(mode);
        requireDisplayValueUpdate = true;
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

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        requireDisplayValueUpdate = true;
    }

    public Calendar getMinimumDate(){
        if (minDate == null) return null;
        return minDate.get();
    }

    public Calendar getMaximumDate(){
        if (maxDate == null) return null;
        return maxDate.get();
    }

    public void setDateFormat(){
        dateFormat = new SimpleDateFormat(getDateFormatTemplate(), Locale.US);
    }

    public void update2DigitYearStart(Calendar selectedDate){
        Calendar cal = (Calendar) selectedDate.clone();
        cal.add(Calendar.YEAR, -50); // subtract 50 years to hit the middle of the century
        dateFormat.set2DigitYearStart(cal.getTime());
    }

    public void setShownCountOnEmptyWheels(int shownCount) {
        int[] ids = {
                R.id.emptyStart,
                R.id.empty1,
                R.id.empty2,
                R.id.empty3,
                R.id.emptyEnd
        };

        for (int id : ids) {
            NumberPickerView view = (NumberPickerView) findViewById(id);
           if(view != null) view.setShownCount(shownCount);
        }



    }
}
