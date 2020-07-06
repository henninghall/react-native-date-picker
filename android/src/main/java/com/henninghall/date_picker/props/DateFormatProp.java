package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class DateFormatProp extends Prop<String> {
    public static final String name = "dateFormat";
    
    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}
