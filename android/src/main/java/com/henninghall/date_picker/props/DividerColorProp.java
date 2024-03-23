package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class DividerColorProp extends Prop<String> {
    public static final String name = "dividerColor";
    
    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}
