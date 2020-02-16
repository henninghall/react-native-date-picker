package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class TextColorProp extends Prop<String> {
    public static final String name = "textColor";
    
    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}
