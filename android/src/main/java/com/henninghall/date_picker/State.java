package com.henninghall.date_picker;

import android.util.Log;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Is24HourSource;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.Variant;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.IdProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.VariantProp;
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
import com.henninghall.date_picker.props.TimezoneOffsetInMinutesProp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class State {

    private Calendar lastSelectedDate = null;
    private final DateProp dateProp = new DateProp();
    private final ModeProp modeProp = new ModeProp();
    private final LocaleProp localeProp = new LocaleProp();
    private final FadeToColorProp fadeToColorProp = new FadeToColorProp();
    private final TextColorProp textColorProp = new TextColorProp();
    private final MinuteIntervalProp minuteIntervalProp = new MinuteIntervalProp();
    private final MinimumDateProp minimumDateProp = new MinimumDateProp();
    private final MaximumDateProp maximumDateProp = new MaximumDateProp();
    private final TimezoneOffsetInMinutesProp timezoneOffsetInMinutesProp = new TimezoneOffsetInMinutesProp();
    private final HeightProp heightProp = new HeightProp();
    private final VariantProp variantProp = new VariantProp();
    private final DividerHeightProp dividerHeightProp = new DividerHeightProp();
    private final Is24hourSourceProp is24hourSourceProp = new Is24hourSourceProp();
    private final IdProp idProp = new IdProp();

    private final HashMap props = new HashMap<String, Prop>() {{
        put(DateProp.name, dateProp);
        put(ModeProp.name, modeProp);
        put(LocaleProp.name, localeProp);
        put(FadeToColorProp.name, fadeToColorProp);
        put(TextColorProp.name, textColorProp);
        put(MinuteIntervalProp.name, minuteIntervalProp);
        put(MinimumDateProp.name, minimumDateProp);
        put(MaximumDateProp.name, maximumDateProp);
        put(TimezoneOffsetInMinutesProp.name, timezoneOffsetInMinutesProp);
        put(HeightProp.name, heightProp);
        put(VariantProp.name, variantProp);
        put(DividerHeightProp.name, dividerHeightProp);
        put(Is24hourSourceProp.name, is24hourSourceProp);
        put(IdProp.name, idProp);
    }};
    public DerivedData derived;

    public State() {
        derived = new DerivedData(this);
    }

    private Prop getProp(String name) {
        return (Prop) props.get(name);
    }

    void setProp(String propName, Dynamic value) {
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

    public Calendar getMinimumDate() {
        return Utils.isoToCalendar(minimumDateProp.getValue(), getTimeZone());
    }

    public Calendar getMaximumDate() {
        return Utils.isoToCalendar(maximumDateProp.getValue(), getTimeZone());
    }

    public TimeZone getTimeZone() {
        try{
            String offsetString = timezoneOffsetInMinutesProp.getValue();
            if(offsetString == null || offsetString.equals("")) return TimeZone.getDefault();
            int offset = Integer.parseInt(offsetString);
            int totalOffsetMinutes = Math.abs(offset);
            char offsetDirection = offset < 0 ? '-' : '+';
            int offsetHours = (int) Math.floor(totalOffsetMinutes / 60f);
            int offsetMinutes = totalOffsetMinutes - offsetHours * 60;
            String timeZoneId = "GMT" + offsetDirection + offsetHours + ":" + Utils.toPaddedMinutes(offsetMinutes);
            TimeZone zone = TimeZone.getTimeZone(timeZoneId);
            return zone;
        } catch (Exception e){
            e.printStackTrace();
            return TimeZone.getDefault();
        }
    }

    public String getIsoDate() {
        return (String) dateProp.getValue();
    }

    private Calendar getDate() {
        return Utils.isoToCalendar(getIsoDate(), getTimeZone());
    }

    // The date the picker is suppose to display.
    // Includes minute rounding to desired minute interval.
    public Calendar getPickerDate() {
        Calendar cal = getDate();
        int minuteInterval = getMinuteInterval();
        if(minuteInterval <= 1) return cal;
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", getLocale());
        int exactMinute = Integer.parseInt(minuteFormat.format(cal.getTime()));
        int minutesSinceLastInterval = exactMinute % minuteInterval;
        cal.add(Calendar.MINUTE, -minutesSinceLastInterval);
        return (Calendar) cal.clone();
    }

    public Integer getHeight() {
        return (Integer) heightProp.getValue();
    }

    public String getLocaleLanguageTag() {
        return localeProp.getLanguageTag();
    }

    public Variant getVariant() {
        return variantProp.getValue();
    }

    public int getDividerHeight() {
        return dividerHeightProp.getValue();
    }
    public String getId() {
        return idProp.getValue();
    }

    public Is24HourSource getIs24HourSource() {
        return is24hourSourceProp.getValue();
    }

    public Calendar getLastSelectedDate() {
        return lastSelectedDate;
    }

    public void setLastSelectedDate(Calendar date) {
        lastSelectedDate = date;
    }
}