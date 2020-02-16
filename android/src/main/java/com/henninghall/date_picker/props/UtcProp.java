package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class UtcProp extends Prop<Boolean> {
    public static final String name = "utc";

    @Override
    Boolean toValue(Dynamic value) {
        return value.asBoolean();
    }
}
