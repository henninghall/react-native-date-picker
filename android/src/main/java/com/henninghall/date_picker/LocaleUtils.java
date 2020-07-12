package com.henninghall.date_picker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LocaleUtils {

    public static String getDay(String locale) {
        return getFormat(locale, Formats.Formatz.MMMEd);
    }

    public static String getYear(String locale) {
        return getFormat(locale, Formats.Formatz.y);
    }

    public static String getDate(String locale) {
        return getFormat(locale, Formats.Formatz.d);
    }

    private static String getFormat (String languageTag, Formats.Formatz format) {
        try {
            return Formats.get(languageTag, format);
        } catch (Formats.FormatNotFoundException e) {
            try {
                String firstPartOfLanguageTag = languageTag.substring(0, languageTag.indexOf("_"));
                return Formats.get(firstPartOfLanguageTag, format);
            } catch (Formats.FormatNotFoundException ex) {
                return Formats.defaultFormat.get(format);
            }
        }
    }

//    /**
//     * @return Full pattern including special char. Example: Year pattern char be "y" for most
//     * locales but "y년" for korean.
//     */
//    public static String getPatternIncluding(String format, Locale locale) {
////        Set<String> locales = Formats.getKeys();
//
//
////        for(String l : locales){
////            Locale loc = Locale.forLanguageTag(l);
////            String datepattern = getDatePattern(loc);
////            ArrayList<String> full = getFullPatternPieces(loc);
////            String piecestring = Arrays.toString(new ArrayList[]{full});
////            if(piecestring.contains("'")
////                    && !piecestring.contains(", y]")
////                    && !piecestring.contains(", y,")
////                    && !piecestring.contains("[y,")
////            ){
////                Log.d("========== ", l);
////
////                Log.d("henning", datepattern);
////                Log.d("henning", piecestring);
////            }
////        }
//
//
//        for (String piece : getFullPatternPieces(locale)) {
//            if (piece.contains(format) && !piece.contains("'")) {
//                return piece;
//            }
//        }
//        return null;
//    }

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

}
