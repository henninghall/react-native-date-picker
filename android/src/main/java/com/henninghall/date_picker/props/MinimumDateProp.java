package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class MinimumDateProp extends Prop<String> {
    public static final String name = "minimumDate";

    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}
