package com.henninghall.date_picker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LocaleUtils {

    public static String getDay(String locale) {
        return getFormat(locale, Formats.Format.MMMEd);
    }

    public static String getYear(String locale) {
        return getFormat(locale, Formats.Format.y);
    }

    public static String getDate(String locale) {
        return getFormat(locale, Formats.Format.d);
    }

    private static String getFormat(String languageTag, Formats.Format format) {
        try {
            return Formats.get(languageTag, format);
        } catch (Formats.FormatNotFoundException e) {
            try {
                String firstPartOfLanguageTag = languageTag.substring(0, languageTag.indexOf("_"));
                return Formats.get(firstPartOfLanguageTag, format);
            } catch (Formats.FormatNotFoundException | IndexOutOfBoundsException ex) {
                return Formats.defaultFormat.get(format);
            }
        }
    }

    public static String getDatePattern(Locale locale) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        return ((SimpleDateFormat) df).toLocalizedPattern()
                .replaceAll(",", "")
                .replaceAll("([a-zA-Z]+)", " $1")
                .trim();
    }

    static String getDateTimePattern(Locale locale) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
        return ((SimpleDateFormat) format).toLocalizedPattern().replace(",", "");
    }

    public static Locale getLocale(String languageTag) {
        Locale locale;
        try {
            locale = org.apache.commons.lang3.LocaleUtils.toLocale(languageTag);
        } catch (Exception e) {
            // Some locales can only be interpreted from country string (for instance zh_Hans_CN )
            String firstPartOfLanguageTag = languageTag.substring(0, languageTag.indexOf("_"));
            locale = org.apache.commons.lang3.LocaleUtils.toLocale(firstPartOfLanguageTag);
        }
        return locale;
    }

    public static boolean localeUsesAmPm(Locale locale){
        DateFormat df = DateFormat.getTimeInstance(DateFormat.FULL, locale);
        return df instanceof SimpleDateFormat && ((SimpleDateFormat) df).toPattern().contains("a");
    }

    public static String getLocaleStringResource(Locale requestedLocale, int resourceId, Context context) {
        try {
            String result;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // use latest api
                Configuration config = new Configuration(context.getResources().getConfiguration());
                config.setLocale(requestedLocale);
                result = context.createConfigurationContext(config).getText(resourceId).toString();
            }
            else { // support older android versions
                Resources resources = context.getResources();
                Configuration conf = resources.getConfiguration();
                Locale savedLocale = conf.locale;
                conf.locale = requestedLocale;
                resources.updateConfiguration(conf, null);

                // retrieve resources from desired locale
                result = resources.getString(resourceId);

                // restore original locale
                conf.locale = savedLocale;
                resources.updateConfiguration(conf, null);
            }

            return result;
        } catch (Exception e) {
            return ""; // Eat the error and return empty string when no resource was found
        }
    }

}
