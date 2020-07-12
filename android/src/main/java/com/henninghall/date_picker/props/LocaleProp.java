package com.henninghall.date_picker.props;

import android.os.Build;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.LocaleUtils;


import java.util.Locale;

public class LocaleProp extends Prop<Locale> {
    public static final String name = "locale";
    private String languageTag = getDefaultLanguageTag();

    public LocaleProp(){
        super(getDefaultLocale());
    }

    static private Locale getDefaultLocale(){
        return LocaleUtils.getLocale(getDefaultLanguageTag());
    }

    static private String getDefaultLanguageTag(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? Locale.getDefault().toLanguageTag().replace('-', '_')
                : "en";
    }

    public String getLanguageTag(){
        return languageTag;
    }

    @Override
    public Locale toValue(Dynamic value){
        this.languageTag = value.asString().replace('-','_');
        return LocaleUtils.getLocale(languageTag);
    }

}

