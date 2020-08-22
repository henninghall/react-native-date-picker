package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Is24HourSource;

public class Is24hourSourceProp extends Prop<Is24HourSource> {
    public static final String name = "is24hourSource";

    @Override
    public Is24HourSource toValue(Dynamic value){
        return Is24HourSource.valueOf(value.asString());
    }
}
