package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class IdProp extends Prop<String> {
    public static final String name = "id";

    @Override
    public String toValue(Dynamic value){
        return value.asString();
    }
}
