package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class TimezoneOffsetInMinutesProp extends Prop<String> {
    public static final String name = "timezoneOffsetInMinutes";

    @Override
    String toValue(Dynamic value) {
        if(value.isNull()) return null;
        return value.asString();
    }
}
