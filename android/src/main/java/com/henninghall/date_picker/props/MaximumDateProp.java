package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class MaximumDateProp extends Prop<String> {
    public static final String name = "maximumDate";
    
    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}
