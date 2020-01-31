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
            if(piece.contains(format)) {
                return piece;
            }
        }
        return null;
    }

    public static int getFullPatternPos(String format, Locale locale) {
        ArrayList<String> pieces = getFullPatternPieces(locale);
        for (String piece: pieces){
            if(piece.contains(format)) {
                return pieces.indexOf(piece);
            }
        }
        return -1;
    }

    public static String getDatePattern(Locale locale){
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        return ((SimpleDateFormat)df).toLocalizedPattern().replace(",", "");
    }

    static String getDateTimePattern(Locale locale){
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
        return ((SimpleDateFormat)format).toLocalizedPattern().replace(",", "");
    }

}


