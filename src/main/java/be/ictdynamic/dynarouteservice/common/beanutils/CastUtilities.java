package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.lang3.BooleanUtils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Static class for casting objects.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 10-aug-2005
 */
public final class CastUtilities {

    /**
     * Private constructor to prevent this static class from initialised.
     */
    private CastUtilities() {
    }

    /**
     * Cast long.
     *
     * @param number the number
     * @return the long
     */
    public static Long castLong(Number number) {
        return (number == null ? null : number.longValue());
    }

    /**
     * Cast long.
     *
     * @param date the date
     * @return the long
     */
    public static Long castLong(java.util.Date date) {
        return (date == null ? null : date.getTime());
    }

    /**
     * Cast long.
     *
     * @param bool the bool
     * @return the long
     */
    public static Long castLong(Boolean bool) {
        return CastUtilities.castLong(BooleanUtils.toIntegerObject(bool, 1, 0, null));
    }

    /**
     * Cast integer.
     *
     * @param number the number
     * @return the integer
     */
    public static Integer castInteger(Number number) {
        return (number == null ? null : number.intValue());
    }

    /**
     * Cast integer.
     *
     * @param bool the bool
     * @return the integer
     */
    public static Integer castInteger(Boolean bool) {
        return BooleanUtils.toIntegerObject(bool, 1, 0, null);
    }

    /**
     * Cast double.
     *
     * @param number the number
     * @return the double
     */
    public static Double castDouble(Number number) {
        return (number == null ? null : number.doubleValue());
    }

    /**
     * Cast boolean.
     *
     * @param n the n
     * @return the boolean
     */
    public static Boolean castBoolean(Number n) {
        return BooleanUtils.toBooleanObject((n == null ? null : n.intValue()), 1, 0, null);
    }

    /**
     * Cast sql date.
     *
     * @param date the date
     * @return the java.sql. date
     */
    public static Date castSqlDate(java.util.Date date) {
        return (date == null ? null : new Date(date.getTime()));
    }

    /**
     * Cast sql date.
     *
     * @param dateInMillis the date in millis
     * @return the java.sql. date
     */
    public static Date castSqlDate(Long dateInMillis) {
        return (dateInMillis == null ? null : new Date(dateInMillis));
    }

    /**
     * Cast timestamp.
     *
     * @param date the date
     * @return the timestamp
     */
    public static Timestamp castTimestamp(java.util.Date date) {
        return (date == null ? null : new Timestamp(date.getTime()));
    }

    /**
     * Cast timestamp.
     *
     * @param dateInMillis the date in millis
     * @return the timestamp
     */
    public static Timestamp castTimestamp(Long dateInMillis) {
        return (dateInMillis == null ? null : new Timestamp(dateInMillis));
    }
}