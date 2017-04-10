package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.dynarouteservice.common.beanutils.CastUtilities;
import be.ictdynamic.dynarouteservice.common.beanutils.ConvertUtilities;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.joda.time.ReadableInterval;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Class DateUtilities.
 *
 * @author Yves Cieters
 * @version $Revision$
 * @since 2-jun-2009
 */
public final class DateUtilities {
    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private DateUtilities() {
    }

    public static final Collection<Date> PUBLIC_HOLIDAYS = new HashSet<Date>();

    static {
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/04/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("06/04/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("14/05/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("24/05/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/05/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2015"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("27/03/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("28/03/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/05/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/05/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("16/05/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2016"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("16/04/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("17/04/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/05/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("04/06/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/06/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2017"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/04/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("02/04/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("10/05/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("20/05/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/05/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2018"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/04/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("22/04/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("30/05/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("09/06/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("10/06/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2019"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("12/04/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("13/04/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/05/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("31/05/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/06/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2020"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("04/04/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/04/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("13/05/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("23/05/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("24/05/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2021"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("17/04/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("18/04/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("26/05/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/06/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("06/06/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2022"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("09/04/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("10/04/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("18/05/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("28/05/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("29/05/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2023"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("31/03/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/04/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("09/05/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("19/05/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("20/05/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2024"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("20/04/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/04/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("29/05/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("08/06/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("09/06/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2025"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/04/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("06/04/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("14/05/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("24/05/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/05/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2026"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("28/03/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("29/03/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("06/05/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("16/05/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("17/05/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2027"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("16/04/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("17/04/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/05/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("04/06/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("05/06/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2028"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/04/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("02/04/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("10/05/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("20/05/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/05/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2029"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/01/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/04/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("22/04/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/05/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("30/05/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("09/06/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("10/06/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("21/07/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("15/08/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("01/11/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("11/11/2030"));
        PUBLIC_HOLIDAYS.add(ConvertUtilities.parseSqlDate("25/12/2030"));
    }

    /**
     * Returns whether the period with the specified begin and enddate contains a specified date.
     *
     * @param start Date referencing the begin of the period to be checked.
     * @param end Date referencing the end of the period to be checked.
     * @param check Date referencing the date to be checked.
     * @return boolean flag with value true if the period with the specified begin and enddate contains a specified date, false when not.
     */
    public static boolean containsDate(Date start, Date end, Date check) {
        boolean contained = false;
        if (check != null) {
            long startMillis = (start != null ? start.getTime() : Long.MIN_VALUE);
            long endMillis = (end != null ? end.getTime() : Long.MAX_VALUE);
            Interval interval = new Interval(startMillis, endMillis);
            contained = interval.contains(check.getTime()) || (endMillis == check.getTime());
        }
        return contained;
    }

    /**
     * Returns whether the period with the specified begin and enddate contains now.
     *
     * @param start Date referencing the begin of the period to be checked.
     * @param end Date referencing the end of the period to be checked.
     * @return boolean flag with value true if the period with the specified begin and enddate contains now, false when not.
     */
    public static boolean containsNow(Date start, Date end) {
        return DateUtilities.containsDate(start, end, DateUtilities.now());
    }

    /**
     * Returns the first date of the specified year at midnight.
     *
     * @param year Integer referencing the year to return the first day at midnight for.
     * @return Date referencing the first date of the specified year at midnight.
     */
    public static Date startOfYear(Integer year) {
        Date start = null;

        if (year != null) {
            start = new Date(new DateMidnight(year, 1, 1).getMillis());
        }

        return start;
    }

    /**
     * Returns the last second of the specified year.
     *
     * @param year Integer referencing the year to return the last second for.
     * @return Date referencing the last date of the specified year, with time 23:59:59.
     */
    public static Date endOfYear(Integer year) {
        Date end = null;

        if (year != null) {
            end = new Date(new DateMidnight(year + 1, 1, 1).toDateTime().minusMillis(1).getMillis());
        }

        return end;
    }

    /**
     * Today date.
     *
     * @return the date
     */
    public static Date today() {
        return CastUtilities.castSqlDate(DateUtils.truncate(now(), Calendar.DAY_OF_MONTH));
    }

    /**
     * Remove time info.
     *
     * @param date the date
     * @return the date
     */
    public static Date removeTimeInfo(Date date) {
        return CastUtilities.castSqlDate(DateUtils.truncate(date, Calendar.DAY_OF_MONTH));
    }

    /**
     * Days from today.
     *
     * @param days the days
     * @return the date
     */
    public static Date daysFromToday(int days) {
        return CastUtilities.castSqlDate(DateUtils.addDays(today(), days));
    }

    /**
     * Months from today.
     *
     * @param months the months
     * @return the date
     */
    public static Date monthsFromToday(int months) {
        return CastUtilities.castSqlDate(DateUtils.addMonths(today(), months));
    }

    /**
     * Years from today.
     *
     * @param years the years
     * @return the date
     */
    public static Date yearsFromToday(int years) {
        return CastUtilities.castSqlDate(DateUtils.addYears(today(), years));
    }

    /**
     * Days from date.
     *
     * @param days the days
     * @param date the date
     * @return the date
     */
    public static Date daysFromDate(int days, Date date) {
        return CastUtilities.castSqlDate(DateUtils.addDays(removeTimeInfo(date), days));
    }

    /**
     * Months from date.
     *
     * @param months the months
     * @param date the date
     * @return the date
     */
    public static Date monthsFromDate(int months, Date date) {
        return CastUtilities.castSqlDate(DateUtils.addMonths(removeTimeInfo(date), months));
    }

    /**
     * Years from date.
     *
     * @param years the years
     * @param date the date
     * @return the date
     */
    public static Date yearsFromDate(int years, Date date) {
        return CastUtilities.castSqlDate(DateUtils.addYears(removeTimeInfo(date), years));
    }

    /**
     * Now date.
     *
     * @return the date
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Hours from now.
     *
     * @param hours the hours
     * @return the date
     */
    public static Date hoursFromNow(int hours) {
        return CastUtilities.castSqlDate(DateUtils.addHours(now(), hours));
    }

    /**
     * Days from now.
     *
     * @param days the days
     * @return the date
     */
    public static Date daysFromNow(int days) {
        return CastUtilities.castSqlDate(DateUtils.addDays(now(), days));
    }

    /**
     * Months from now.
     *
     * @param months the months
     * @return the date
     */
    public static Date monthsFromNow(int months) {
        return CastUtilities.castSqlDate(DateUtils.addMonths(now(), months));
    }

    /**
     * Years from now.
     *
     * @param years the years
     * @return the date
     */
    public static Date yearsFromNow(int years) {
        return CastUtilities.castSqlDate(DateUtils.addYears(now(), years));
    }

    /**
     * New timestamp.
     *
     * @return the timestamp
     */
    public static Timestamp newTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Checks whether the specified interval overlaps one of the specified intervals.
     *
     * @param interval ReadableInterval referencing the interval to be check for overlapping.
     * @param intervals Array of ReadableIntervals referencing the intervals to check the specified interval with.
     * @return boolean flag with value true when the specified interval overlaps one of the specified intervals, false when not or when parameters are null or empty.
     */
    public static boolean overlapsOneOf(ReadableInterval interval, ReadableInterval... intervals) {
        boolean overlaps = false;

        if (interval != null && !ArrayUtils.isEmpty(intervals)) {
            for (int i = 0; !overlaps && i < intervals.length; i++) {
                ReadableInterval check = intervals[i];
                overlaps = interval.overlaps(check);
            }
        }

        return overlaps;
    }

    public static long getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) + 1);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return calendar.getTime().getTime();
    }

}
