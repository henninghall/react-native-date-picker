package com.henninghall.date_picker;

import java.util.EnumMap;
import java.util.HashMap;

public class Formats {

    public static EnumMap<Format, String> defaultFormat = mapOf("EEE, MMM d", "d", "y");

    public enum Format {
        MMMEd, d, y
    }

    public static String get(String locale, Format format) throws FormatNotFoundException {
        try {
            return map.get(locale).get(format)
                    .replaceAll(",", "");
        } catch (NullPointerException e) {
            throw new FormatNotFoundException();
        }
    }

    private static HashMap<String, EnumMap<Format, String>> map = new HashMap<String, EnumMap<Format, String>>() {{
        put("af", mapOf("EEE d MMM", "d", "y"));
        put("am", mapOf("EEE፣ MMM d", "d", "y"));
        put("ar", mapOf("EEE، d MMM", "d", "y"));
        put("ar_DZ", mapOf("EEE، d MMM", "d", "y"));
        put("ar_EG", mapOf("EEE، d MMM", "d", "y"));
        put("az", mapOf("d MMM, EEE", "d", "y"));
        put("be", mapOf("EEE, d MMM", "d", "y"));
        put("bg", mapOf("EEE, d.MM", "d", "y 'г'."));
        put("bn", mapOf("EEE d MMM", "d", "y"));
        put("br", mapOf("EEE d MMM", "d", "y"));
        put("bs", mapOf("EEE, d. MMM", "d.", "y."));
        put("ca", mapOf("EEE, d MMM", "d", "y"));
        put("chr", mapOf("EEE, MMM d", "d", "y"));
        put("cs", mapOf("EEE d. M.", "d.", "y"));
        put("cy", mapOf("EEE, d MMM", "d", "y"));
        put("da", mapOf("EEE d. MMM", "d.", "y"));
        put("de", mapOf("EEE, d. MMM", "d", "y"));
        put("de_AT", mapOf("EEE, d. MMM", "d", "y"));
        put("de_CH", mapOf("EEE, d. MMM", "d", "y"));
        put("el", mapOf("EEE, d MMM", "d", "y"));
        put("en", mapOf("EEE, MMM d", "d", "y"));
        put("en_AU", mapOf("EEE, d MMM", "d", "y"));
        put("en_CA", mapOf("EEE, MMM d", "d", "y"));
        put("en_GB", mapOf("EEE, d MMM", "d", "y"));
        put("en_IE", mapOf("EEE, d MMM", "d", "y"));
        put("en_IN", mapOf("EEE, d MMM", "d", "y"));
        put("en_SG", mapOf("EEE, d MMM", "d", "y"));
        put("en_US", mapOf("EEE, MMM d", "d", "y"));
        put("en_ZA", mapOf("EEE, dd MMM", "d", "y"));
        put("es", mapOf("EEE, d MMM", "d", "y"));
        put("es_419", mapOf("EEE, d MMM", "d", "y"));
        put("es_ES", mapOf("EEE, d MMM", "d", "y"));
        put("es_MX", mapOf("EEE d 'de' MMM", "d", "y"));
        put("es_US", mapOf("EEE, d 'de' MMM", "d", "y"));
        put("et", mapOf("EEE, d. MMM", "d", "y"));
        put("eu", mapOf("MMM d, EEE", "d", "y"));
        put("fa", mapOf("EEE d LLL", "d", "y"));
        put("fi", mapOf("EEE d. MMM", "d", "y"));
        put("fil", mapOf("EEE, MMM d", "d", "y"));
        put("fr", mapOf("EEE d MMM", "d", "y"));
        put("fr_CA", mapOf("EEE d MMM", "d", "y"));
        put("ga", mapOf("EEE d MMM", "d", "y"));
        put("gl", mapOf("EEE, d 'de' MMM", "d", "y"));
        put("gsw", mapOf("EEE d. MMM", "d", "y"));
        put("gu", mapOf("EEE, d MMM", "d", "y"));
        put("haw", mapOf("EEE, d MMM", "d", "y"));
        put("he", mapOf("EEE, d בMMM", "d", "y"));
        put("hi", mapOf("EEE, d MMM", "d", "y"));
        put("hr", mapOf("EEE, d. MMM", "d.", "y."));
        put("hu", mapOf("MMM d., EEE", "d", "y."));
        put("hy", mapOf("d MMM, EEE", "d", "y"));
        put("id", mapOf("EEE, d MMM", "d", "y"));
        put("in", mapOf("EEE, d MMM", "d", "y"));
        put("is", mapOf("EEE, d. MMM", "d", "y"));
        put("it", mapOf("EEE d MMM", "d", "y"));
        put("iw", mapOf("EEE, d בMMM", "d", "y"));
        put("ja", mapOf("M月d日 EEE", "d日", "y年"));
        put("ka", mapOf("EEE, d MMM", "d", "y"));
        put("kk", mapOf("d MMM, EEE", "d", "y"));
        put("km", mapOf("EEE d MMM", "d", "y"));
        put("kn", mapOf("EEE, d MMM", "d", "y"));
        put("ko", mapOf("MMM d일 EEE", "d일", "y년"));
        put("ky", mapOf("d-MMM, EEE", "d", "y"));
        put("lb", mapOf("EEE d MMM", "d", "y"));
        put("ln", mapOf("EEE d MMM", "d", "y"));
        put("lo", mapOf("EEE d MMM", "d", "y"));
        put("lt", mapOf("MM-dd, EEE", "dd", "y"));
        put("lv", mapOf("EEE, d. MMM", "d", "y. 'g'."));
        put("mk", mapOf("EEE, d MMM", "d", "y"));
        put("ml", mapOf("MMM d, EEE", "d", "y"));
        put("mn", mapOf("MMM'ын' d. EEE", "d", "y"));
        put("mo", mapOf("EEE, d MMM", "d", "y"));
        put("mr", mapOf("EEE, d MMM", "d", "y"));
        put("ms", mapOf("EEE, d MMM", "d", "y"));
        put("mt", mapOf("EEE, d 'ta'’ MMM", "d", "y"));
        put("my", mapOf("MMM d၊ EEE", "d", "y"));
        put("nb", mapOf("EEE d. MMM", "d.", "y"));
        put("ne", mapOf("MMM d, EEE", "d", "y"));
        put("nl", mapOf("EEE d MMM", "d", "y"));
        put("nn", mapOf("EEE d. MMM", "d.", "y"));
        put("no", mapOf("EEE d. MMM", "d.", "y"));
        put("no_NO", mapOf("EEE d. MMM", "d.", "y"));
        put("or", mapOf("EEE, MMM d", "d", "y"));
        put("pa", mapOf("EEE, d MMM", "d", "y"));
        put("pl", mapOf("EEE, d MMM", "d", "y"));
        put("pt", mapOf("EEE, d 'de' MMM", "d", "y"));
        put("pt_BR", mapOf("EEE, d 'de' MMM", "d", "y"));
        put("pt_PT", mapOf("EEE, d/MM", "d", "y"));
        put("ro", mapOf("EEE, d MMM", "d", "y"));
        put("ru", mapOf("ccc, d MMM", "d", "y"));
        put("sh", mapOf("EEE d. MMM", "d", "y."));
        put("si", mapOf("MMM d EEE", "d", "y"));
        put("sk", mapOf("EEE d. M.", "d.", "y"));
        put("sl", mapOf("EEE, d. MMM", "d.", "y"));
        put("sq", mapOf("EEE, d MMM", "d", "y"));
        put("sr", mapOf("EEE d. MMM", "d", "y."));
        put("sr_Latn", mapOf("EEE d. MMM", "d", "y."));
        put("sv", mapOf("EEE d MMM", "d", "y"));
        put("sw", mapOf("EEE, d MMM", "d", "y"));
        put("ta", mapOf("MMM d, EEE", "d", "y"));
        put("te", mapOf("d MMM, EEE", "d", "y"));
        put("th", mapOf("EEE d MMM", "d", "y"));
        put("tl", mapOf("EEE, MMM d", "d", "y"));
        put("tr", mapOf("d MMMM EEE", "d", "y"));
        put("uk", mapOf("EEE, d MMM", "d", "y"));
        put("ur", mapOf("EEE، d MMM", "d", "y"));
        put("uz", mapOf("EEE, d-MMM", "d", "y"));
        put("vi", mapOf("EEE, d MMM", "d", "y"));
        put("zh", mapOf("M月d日EEE", "d日", "y年"));
        put("zh_CN", mapOf("M月d日EEE", "d日", "y年"));
        put("zh_HK", mapOf("M月d日EEE", "d日", "y年"));
        put("zh_TW", mapOf("M月d日 EEE", "d日", "y年"));
        put("zu", mapOf("EEE, MMM d", "d", "y"));
        put("en_ISO", mapOf("EEE, MMM d", "d", "y"));
        put("en_MY", mapOf("EEE, d MMM", "d", "y"));
        put("fr_CH", mapOf("EEE d MMM", "d", "y"));
        put("it_CH", mapOf("EEE d MMM", "d", "y"));
        put("ps", mapOf("MMM d, EEE", "d", "y"));
    }};

    private static EnumMap<Format, String> mapOf(final String MMMed, final String d, final String y) {
        return new EnumMap<Format, String>(Format.class) {{
            put(Format.MMMEd, MMMed);
            put(Format.d, d);
            put(Format.y, y);
        }};
    }

    static class FormatNotFoundException extends Exception {
    }
}
