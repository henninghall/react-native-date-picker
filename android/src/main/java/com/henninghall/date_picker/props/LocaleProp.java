package com.henninghall.date_picker.props;

import android.os.Build;

import com.facebook.react.bridge.Dynamic;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Locale;

public class LocaleProp extends Prop<Locale> {
    public static final String name = "locale";

    public LocaleProp(){
        super(
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? Locale.forLanguageTag("en")
                : Locale.getDefault()
        );
    }

    @Override
    public Locale toValue(Dynamic value){
        return LocaleUtils.toLocale(value.asString().replace('-','_'));
    }

}

