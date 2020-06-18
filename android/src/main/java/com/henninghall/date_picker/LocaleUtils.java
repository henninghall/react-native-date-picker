package com.henninghall.date_picker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LocaleUtils {

    private static ArrayList<String> getFullPatternPieces(Locale locale){
        return Utils.splitOnSpace(getDatePattern(locale));
    }

    /**
        @return Full pattern including special char. Example: Year pattern char be "y" for most
        locales but "y년" for korean.
    */
    public static String getPatternIncluding(String format, Locale locale) {
        for (String piece: getFullPatternPieces(locale)){
            if(piece.contains(format) && !piece.contains("'")) {
                return piece;
            }
        }
        return null;
    }

    public static String getDatePattern(Locale locale){
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        return ((SimpleDateFormat) df).toLocalizedPattern()
                .replaceAll(",", "")
                .replaceAll("([a-zA-Z]+)", " $1")
                .trim();
    }

    static String getDateTimePattern(Locale locale){
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
        return ((SimpleDateFormat)format).toLocalizedPattern().replace(",", "");
    }

     public static Locale getLocale(String languageTag){
        Locale locale;
        try{
            locale = org.apache.commons.lang3.LocaleUtils.toLocale(languageTag);
        } catch (Exception e ){
            // Some locales can only be interpreted from country string (for instance zh_Hans_CN )
            String firstPartOfLanguageTag = languageTag.substring(0, languageTag.indexOf(""));
            locale = org.apache.commons.lang3.LocaleUtils.toLocale(firstPartOfLanguageTag);
        }
        return locale;
    }

}
