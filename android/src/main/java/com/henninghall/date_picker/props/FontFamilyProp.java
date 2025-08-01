// node_modules/react-native-date-picker/android/src/main/java/com/henninghall/date_picker/props/FontFamilyProp.java
package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

/** Prop for passing the JS fontFamily string into native state */
public class FontFamilyProp extends Prop<String> {
    public static final String name = "fontFamily";

    @Override
    public String toValue(Dynamic dynamic) {
        return dynamic.asString();
    }
}
