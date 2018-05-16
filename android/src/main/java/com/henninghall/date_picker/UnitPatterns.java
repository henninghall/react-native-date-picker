/*
 * -----------------------------------------------------------------------
 * Copyright © 2013-2015 Meno Hochschild, <http://www.menodata.de/>
 * -----------------------------------------------------------------------
 * This file (UnitPatterns.java) is part of project Time4J.
 *
 * Time4J is free software: You can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Time4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Time4J. If not, see <http://www.gnu.org/licenses/>.
 * -----------------------------------------------------------------------
 */

package com.henninghall.date_picker;

import net.time4j.CalendarUnit;
import net.time4j.ClockUnit;
import net.time4j.IsoUnit;
import net.time4j.base.ResourceLoader;
import net.time4j.format.PluralCategory;
import net.time4j.format.RelativeTimeProvider;
import net.time4j.format.TextWidth;
import net.time4j.format.UnitPatternProvider;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * <p>Offers localized time unit patterns for formatting of durations. </p>
 *
 * @author  Meno Hochschild
 * @since   3.0
 * @see     UnitPatternProvider
 */
public final class UnitPatterns {

    //~ Statische Felder/Initialisierungen --------------------------------

    private static final int MIN_LIST_INDEX = 2;
    private static final int MAX_LIST_INDEX = 7;

    private static final ConcurrentMap<Locale, UnitPatterns> CACHE =
            new ConcurrentHashMap<Locale, UnitPatterns>();
    private static final IsoUnit[] UNIT_IDS = {
            CalendarUnit.YEARS,
            CalendarUnit.MONTHS,
            CalendarUnit.WEEKS,
            CalendarUnit.DAYS,
            ClockUnit.HOURS,
            ClockUnit.MINUTES,
            ClockUnit.SECONDS,
            ClockUnit.MILLIS,
            ClockUnit.MICROS,
            ClockUnit.NANOS
    };
    private static final UnitPatternProvider PROVIDER;
    private static final UnitPatternProvider FALLBACK;

    static {
        FALLBACK = new FallbackProvider();
        UnitPatternProvider p = null;

        for (UnitPatternProvider tmp : ResourceLoader.getInstance().services(UnitPatternProvider.class)) {
            p = tmp;
            break;
        }

        if (p == null) {
            p = FALLBACK;
        }

        PROVIDER = p;
    }

    //~ Instanzvariablen --------------------------------------------------

    private final Locale locale;
    private final Map<IsoUnit, Map<TextWidth, Map<PluralCategory, String>>> patterns;
    private final Map<IsoUnit, Map<PluralCategory, String>> past;
    private final Map<IsoUnit, Map<PluralCategory, String>> future;
    private final Map<IsoUnit, Map<PluralCategory, String>> shortPast;
    private final Map<IsoUnit, Map<PluralCategory, String>> shortFuture;
    private final Map<Integer, Map<TextWidth, String>> list;
    private final String now;
    private final String yesterday;
    private final String today;
    private final String tomorrow;

    //~ Konstruktoren -----------------------------------------------------

    private UnitPatterns(Locale language) {
        super();

        this.locale = language;

        Map<IsoUnit, Map<TextWidth, Map<PluralCategory, String>>> map =
                new HashMap<IsoUnit, Map<TextWidth, Map<PluralCategory, String>>>(10);
        Map<IsoUnit, Map<PluralCategory, String>> mapPast =
                new HashMap<IsoUnit, Map<PluralCategory, String>>(10);
        Map<IsoUnit, Map<PluralCategory, String>> mapFuture =
                new HashMap<IsoUnit, Map<PluralCategory, String>>(10);
        Map<IsoUnit, Map<PluralCategory, String>> mapShortPast =
                new HashMap<IsoUnit, Map<PluralCategory, String>>(10);
        Map<IsoUnit, Map<PluralCategory, String>> mapShortFuture =
                new HashMap<IsoUnit, Map<PluralCategory, String>>(10);
        Map<Integer, Map<TextWidth, String>> mapList =
                new HashMap<Integer, Map<TextWidth, String>>(10);

        for (IsoUnit unit : UNIT_IDS) {
            // Standard-Muster
            Map<TextWidth, Map<PluralCategory, String>> tmp1 =
                    new EnumMap<TextWidth, Map<PluralCategory, String>>(TextWidth.class);
            for (TextWidth width : TextWidth.values()) {
                Map<PluralCategory, String> tmp2 =
                        new EnumMap<PluralCategory, String>(PluralCategory.class);
                for (PluralCategory cat : PluralCategory.values()) {
                    tmp2.put(cat, lookup(language, unit, width, cat));
                }
                tmp1.put(width, Collections.unmodifiableMap(tmp2));
            }
            map.put(
                    unit,
                    Collections.unmodifiableMap(tmp1));

            if (!Character.isDigit(unit.getSymbol())) { // no subseconds
                // Vergangenheit
                Map<PluralCategory, String> tmp3 =
                        new EnumMap<PluralCategory, String>(PluralCategory.class);
                for (PluralCategory cat : PluralCategory.values()) {
                    tmp3.put(cat, lookup(language, unit, false, false, cat));
                }
                mapPast.put(
                        unit,
                        Collections.unmodifiableMap(tmp3));
                Map<PluralCategory, String> tmp3a =
                        new EnumMap<PluralCategory, String>(PluralCategory.class);
                for (PluralCategory cat : PluralCategory.values()) {
                    tmp3a.put(cat, lookup(language, unit, false, true, cat));
                }
                mapShortPast.put(
                        unit,
                        Collections.unmodifiableMap(tmp3a));

                // Zukunft
                Map<PluralCategory, String> tmp4 =
                        new EnumMap<PluralCategory, String>(PluralCategory.class);
                for (PluralCategory cat : PluralCategory.values()) {
                    tmp4.put(cat, lookup(language, unit, true, false, cat));
                }
                mapFuture.put(
                        unit,
                        Collections.unmodifiableMap(tmp4));
                Map<PluralCategory, String> tmp4a =
                        new EnumMap<PluralCategory, String>(PluralCategory.class);
                for (PluralCategory cat : PluralCategory.values()) {
                    tmp4a.put(cat, lookup(language, unit, true, true, cat));
                }
                mapShortFuture.put(
                        unit,
                        Collections.unmodifiableMap(tmp4a));
            }
        }

        // Liste
        for (int i = MIN_LIST_INDEX; i <= MAX_LIST_INDEX; i++) {
            Integer index = Integer.valueOf(i);
            Map<TextWidth, String> tmp5 =
                    new EnumMap<TextWidth, String>(TextWidth.class);
            for (TextWidth width : TextWidth.values()) {
                tmp5.put(width, lookup(language, width, index));
            }
            mapList.put(
                    index,
                    Collections.unmodifiableMap(tmp5));
        }

        this.patterns = Collections.unmodifiableMap(map);
        this.past = Collections.unmodifiableMap(mapPast);
        this.future = Collections.unmodifiableMap(mapFuture);
        this.shortPast = Collections.unmodifiableMap(mapShortPast);
        this.shortFuture = Collections.unmodifiableMap(mapShortFuture);
        this.list = Collections.unmodifiableMap(mapList);

        String n;

        String y = "";
        String t1 = "";
        String t2 = "";

        try {
            n = PROVIDER.getNowWord(language);

            if (PROVIDER instanceof RelativeTimeProvider) {
                RelativeTimeProvider rp = RelativeTimeProvider.class.cast(PROVIDER);
                y = rp.getYesterdayWord(language);
                t1 = rp.getTodayWord(language);
                t2 = rp.getTomorrowWord(language);
            }
        } catch (MissingResourceException mre) {
            n = FALLBACK.getNowWord(language); // should not happen
        }

        this.now = n;
        this.yesterday = y;
        this.today = t1;
        this.tomorrow = t2;

    }

    //~ Methoden ----------------------------------------------------------

    /**
     * <p>Factory method as constructor replacement. </p>
     *
     * @param   lang    language setting
     * @return  chached instance
     */
    public static UnitPatterns of(Locale lang) {

        if (lang == null) {
            throw new NullPointerException("Missing language.");
        }

        UnitPatterns p = CACHE.get(lang);

        if (p == null) {
            p = new UnitPatterns(lang);
            UnitPatterns old = CACHE.putIfAbsent(lang, p);
            if (old != null) {
                p = old;
            }
        }

        return p;

    }

    /**
     * <p>Yields a unit pattern which optionally contains a placeholder
     * of the form &quot;{0}&quot; standing for the count of units. </p>
     *
     * @param   width       text width (ABBREVIATED as synonym for SHORT)
     * @param   category    plural category
     * @param   unit        associated iso unit
     * @return  unit pattern
     */
    String getPattern(
            TextWidth width,
            PluralCategory category,
            IsoUnit unit
    ) {

        checkNull(width, category);
        return this.patterns.get(unit).get(width).get(category);

    }

    /**
     * <p>Yields a unit pattern which optionally contains a placeholder
     * of the form &quot;{0}&quot; standing for the count of units in the
     * past. </p>
     *
     * @param   category        plural category
     * @param   abbreviated     using short form or not
     * @param   unit            associated iso unit
     * @return  unit pattern in the past
     * @since   3.6/4.4
     */
    String getPatternInPast(
            PluralCategory category,
            boolean abbreviated,
            IsoUnit unit
    ) {

        checkNull(category);

        if (abbreviated) {
            return this.shortPast.get(unit).get(category);
        } else {
            return this.past.get(unit).get(category);
        }

    }

    /**
     * <p>Yields a unit pattern which optionally contains a placeholder
     * of the form &quot;{0}&quot; standing for the count of units in the
     * future. </p>
     *
     * @param   category        plural category
     * @param   abbreviated     using short form or not
     * @param   unit            associated iso unit
     * @return  unit pattern in the future
     * @since   3.6/4.4
     */
    String getPatternInFuture(
            PluralCategory category,
            boolean abbreviated,
            IsoUnit unit
    ) {

        checkNull(category);

        if (abbreviated) {
            return this.shortFuture.get(unit).get(category);
        } else {
            return this.future.get(unit).get(category);
        }

    }

    /**
     * <p>Yields the localized word for the current time (&quot;now&quot;). </p>
     *
     * @return  String
     */
    String getNowWord() {

        return this.now;

    }

    /**
     * <p>Yields the localized word for &quot;yesterday&quot;. </p>
     *
     * @return  String (maybe empty)
     * @since   3.6/4.4
     */
    String getYesterdayWord() {

        return this.yesterday;

    }

    /**
     * <p>Yields the localized word for &quot;today&quot;. </p>
     *
     * @return  String (maybe empty)
     * @since   3.6/4.4
     */
    public String getTodayWord() {

        return this.today;

    }

    /**
     * <p>Yields the localized word for &quot;tomorrow&quot;. </p>
     *
     * @return  String (maybe empty)
     * @since   3.6/4.4
     */
    String getTomorrowWord() {

        return this.tomorrow;

    }

    /**
     * <p>Constructs a localized list pattern suitable for the use in
     * {@link java.text.MessageFormat#format(String, Object[])}. </p>
     *
     * @param   width       text width (ABBREVIATED as synonym for SHORT)
     * @param   size        count of list items
     * @return  message format pattern with placeholders {0}, {1}, ..., {x}, ...
     * @throws  IllegalArgumentException if size is smaller than 2
     */
    String getListPattern(
            TextWidth width,
            int size
    ) {

        if (width == null) {
            throw new NullPointerException("Missing width.");
        }

        if (
                (size >= MIN_LIST_INDEX)
                        && (size <= MAX_LIST_INDEX)
                ) {
            return this.list.get(Integer.valueOf(size)).get(width);
        }

        return lookup(this.locale, width, size);

    }

    private static void checkNull(PluralCategory category) {

        if (category == null) {
            throw new NullPointerException("Missing plural category.");
        }

    }

    private static void checkNull(
            TextWidth width,
            PluralCategory category
    ) {

        if (width == null) {
            throw new NullPointerException("Missing text width.");
        }

        checkNull(category);

    }

    private static char getID(IsoUnit unit) {

        char unitID = unit.getSymbol();

        if (unit == ClockUnit.MINUTES) {
            return 'N';
        }

        return unitID;

    }

    private static String lookup(
            Locale language,
            IsoUnit unit,
            TextWidth width,
            PluralCategory category
    ) {

        try {
            return lookup(PROVIDER, language, getID(unit), width, category);
        } catch (MissingResourceException mre) { // should not happen
            return lookup(FALLBACK, language, getID(unit), width, category);
        }

    }

    private static String lookup(
            UnitPatternProvider p,
            Locale language,
            char unitID,
            TextWidth width,
            PluralCategory category
    ) {

        switch (unitID) {
            case 'Y':
                return p.getYearPattern(language, width, category);
            case 'M':
                return p.getMonthPattern(language, width, category);
            case 'W':
                return p.getWeekPattern(language, width, category);
            case 'D':
                return p.getDayPattern(language, width, category);
            case 'H':
                return p.getHourPattern(language, width, category);
            case 'N':
                return p.getMinutePattern(language, width, category);
            case 'S':
                return p.getSecondPattern(language, width, category);
            case '3':
                return p.getMilliPattern(language, width, category);
            case '6':
                return p.getMicroPattern(language, width, category);
            case '9':
                return p.getNanoPattern(language, width, category);
            default:
                throw new UnsupportedOperationException("Unit-ID: " + unitID);
        }

    }

    private static String lookup(
            Locale language,
            IsoUnit unit,
            boolean future,
            boolean abbreviated,
            PluralCategory category
    ) {

        try {
            return lookup(PROVIDER, language, getID(unit), future, abbreviated, category);
        } catch (MissingResourceException mre) { // should not happen
            return lookup(FALLBACK, language, getID(unit), future, abbreviated, category);
        }

    }

    private static String lookup(
            UnitPatternProvider p,
            Locale language,
            char unitID,
            boolean future,
            boolean abbreviated,
            PluralCategory category
    ) {

        if (abbreviated && (p instanceof RelativeTimeProvider)) {
            RelativeTimeProvider rp = RelativeTimeProvider.class.cast(p);
            switch (unitID) {
                case 'Y':
                    return rp.getShortYearPattern(language, future, category);
                case 'M':
                    return rp.getShortMonthPattern(language, future, category);
                case 'W':
                    return rp.getShortWeekPattern(language, future, category);
                case 'D':
                    return rp.getShortDayPattern(language, future, category);
                case 'H':
                    return rp.getShortHourPattern(language, future, category);
                case 'N':
                    return rp.getShortMinutePattern(language, future, category);
                case 'S':
                    return rp.getShortSecondPattern(language, future, category);
                default:
                    throw new UnsupportedOperationException("Unit-ID: " + unitID);
            }
        }

        switch (unitID) {
            case 'Y':
                return p.getYearPattern(language, future, category);
            case 'M':
                return p.getMonthPattern(language, future, category);
            case 'W':
                return p.getWeekPattern(language, future, category);
            case 'D':
                return p.getDayPattern(language, future, category);
            case 'H':
                return p.getHourPattern(language, future, category);
            case 'N':
                return p.getMinutePattern(language, future, category);
            case 'S':
                return p.getSecondPattern(language, future, category);
            default:
                throw new UnsupportedOperationException("Unit-ID: " + unitID);
        }

    }

    private static String lookup(
            Locale language,
            TextWidth width,
            int size
    ) {

        try {
            return PROVIDER.getListPattern(language, width, size);
        } catch (MissingResourceException mre) { // should not happen
            return FALLBACK.getListPattern(language, width, size);
        }

    }

    //~ Innere Klassen ----------------------------------------------------

    private static class FallbackProvider
            implements UnitPatternProvider {

        //~ Methoden ------------------------------------------------------

        @Override
        public String getYearPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("year", "yr", "y", width, category);
            }

            return getUnitPattern("y");

        }

        @Override
        public String getMonthPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("month", "mth", "m", width, category);
            }

            return getUnitPattern("m");

        }

        @Override
        public String getWeekPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("week", "wk", "w", width, category);
            }

            return getUnitPattern("w");

        }

        @Override
        public String getDayPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("day", "day", "d", width, category);
            }

            return getUnitPattern("d");

        }

        @Override
        public String getHourPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("hour", "hr", "h", width, category);
            }

            return getUnitPattern("h");

        }

        @Override
        public String getMinutePattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("minute", "min", "m", width, category);
            }

            return getUnitPattern("min");

        }

        @Override
        public String getSecondPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern("second", "sec", "s", width, category);
            }

            return getUnitPattern("s");

        }

        @Override
        public String getYearPattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("year", future, category);
            }

            return getRelativePattern("y", future);

        }

        @Override
        public String getMonthPattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("month", future, category);
            }

            return getRelativePattern("m", future);

        }

        @Override
        public String getWeekPattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("week", future, category);
            }

            return getRelativePattern("w", future);

        }

        @Override
        public String getDayPattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("day", future, category);
            }

            return getRelativePattern("d", future);

        }

        @Override
        public String getHourPattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("hour", future, category);
            }

            return getRelativePattern("h", future);

        }

        @Override
        public String getMinutePattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("minute", future, category);
            }

            return getRelativePattern("min", future);

        }

        @Override
        public String getSecondPattern(
                Locale lang,
                boolean future,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getRelativeEnglishPattern("second", future, category);
            }

            return getRelativePattern("s", future);

        }

        @Override
        public String getNowWord(Locale lang) {

            return "now";

        }

        private static String getEnglishPattern(
                String wide,
                String abbr,
                String narrow,
                TextWidth width,
                PluralCategory category
        ) {

            switch (width) {
                case WIDE:
                    return getPluralPattern(wide, category);
                case ABBREVIATED:
                case SHORT:
                    return getPluralPattern(abbr, category);
                case NARROW:
                    return "{0}" + narrow;
                default:
                    throw new UnsupportedOperationException(width.name());
            }

        }

        private static String getPluralPattern(
                String unit,
                PluralCategory category
        ) {

            String plural = (category == PluralCategory.ONE ? "" : "s");
            return "{0} " + unit + plural;

        }

        private static String getUnitPattern(String unit) {

            return "{0} " + unit;

        }

        private static String getRelativeEnglishPattern(
                String unit,
                boolean future,
                PluralCategory category
        ) {

            String plural = (category == PluralCategory.ONE ? "" : "s");

            if (future) {
                return "in {0} " + unit + plural;
            } else {
                return "{0} " + unit + plural + " ago";
            }

        }

        private static String getRelativePattern(
                String unit,
                boolean future
        ) {

            return ((future ? "+" : "-") + "{0} " + unit);

        }

        @Override
        public String getListPattern(
                Locale lang,
                TextWidth width,
                int size
        ) {

            if (size < 2) {
                throw new IllegalArgumentException(
                        "Size must be greater than 1.");
            }

            StringBuilder sb = new StringBuilder(size * 5);

            for (int i = 0; i < size; i++) {
                sb.append('{');
                sb.append(i);
                sb.append('}');

                if (i < size - 1) {
                    sb.append(", ");
                }
            }

            return sb.toString();

        }

        @Override
        public String getMilliPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern(
                        "millisecond", "msec", "ms", width, category);
            }

            return getUnitPattern("ms");

        }

        @Override
        public String getMicroPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern(
                        "microsecond", "µsec", "µs", width, category);
            }

            return getUnitPattern("µs");

        }

        @Override
        public String getNanoPattern(
                Locale lang,
                TextWidth width,
                PluralCategory category
        ) {

            if (lang.getLanguage().equals("en")) {
                return getEnglishPattern(
                        "nanosecond", "nsec", "ns", width, category);
            }

            return getUnitPattern("ns");

        }

    }

}
