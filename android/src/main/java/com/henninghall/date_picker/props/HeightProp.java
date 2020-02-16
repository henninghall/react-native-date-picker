package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Mode;

public class HeightProp extends Prop<Integer> {
    public static final String name = "height";

    @Override
    public Integer toValue(Dynamic value){
        return value.asInt();
    }
}
