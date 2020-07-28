package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public class DividerHeightProp extends Prop<Integer> {
    public static final String name = "dividerHeight";

    @Override
    public Integer toValue(Dynamic value){
        return value.asInt();
    }
}
