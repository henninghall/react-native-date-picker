package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class DateProp extends Prop<String> {
    public static final String name = "date";

    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}