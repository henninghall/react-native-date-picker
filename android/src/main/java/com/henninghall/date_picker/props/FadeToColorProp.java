package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class FadeToColorProp extends Prop<String> {
    public static final String name = "fadeToColor";
    
    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }

}
