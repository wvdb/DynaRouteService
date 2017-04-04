package be.ictdynamic.dynarouteservice.common.lang;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * User: dhondted
 * Date: 20/09/11
 * Time: 13:14
 */
public final class IdGenerator {
    public static final String YYYYMMDD_PATTERN = "yyyyMMdd";
    public static final String HHMMSSSSSPATTERN = "HHmmssSSS";
    public static final String YYYYMMDDHHMMSSSSS_PATTERN = YYYYMMDD_PATTERN + HHMMSSSSSPATTERN;
    public static final int RANDOM_NUMBER_BOUND = 100;

    private IdGenerator() {
    }

    /**
     * Returns a standard uuid of 36 bytes length (a UUID)
     * For example: fb1943a9-9824-4cc4-adb6-241dc4580751
     */
    public static String createId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Returns an id of 48 bytes length (a UUID + ":" + current date as hex string)
     * For example: 6e808362-89d4-4509-9d99-7941cfb3c3fe:133b1ece355
     *
     * @return the hextimestamp-suffixed id
     */
    public static String createIdWithHexTimeStampSuffix() {
        StringBuffer idBuffer;
        idBuffer = new StringBuffer();
        idBuffer.append(createId());
        idBuffer.append(":");
        idBuffer.append(getDateAsHexString(new Date()));
        return idBuffer.toString();
    }

    /**
     * Returns an id of 54 bytes length (a UUID + ":" + current date as formatted timestamp)
     * The timestamp will be in the default yyyyMMddHHMMSSsss format.
     * For example: 7eb164dc-1b5e-40db-b300-d91701859ee2:20111117152731796
     *
     * @return a id consisting of a UUID a ":" and the current date formatted according to the default timestamp pattern.
     */
    public static String createIdWithFormattedTimeStampSuffix() {
        StringBuffer idBuffer;
        idBuffer = new StringBuffer();
        idBuffer.append(createId());
        idBuffer.append(":");
        idBuffer.append(getFormattedDateTimeShort());
        return idBuffer.toString();
    }

    /**
     * Returns an id of 54 bytes length (a UUID + ":" + formatted timestamp)
     * The timestamp will be in the default yyyyMMddHHMMSSsss format.
     * For example: 7eb164dc-1b5e-40db-b300-d91701859ee2:20111117152731796
     *
     * @param date the date that should be formatted.
     * @return a id consisting of a UUID a ":" and the given date formatted as a string according to the default timestamp pattern.
     */
    public static String createIdWithFormattedTimeStampSuffix(final Date date) {
        return createIdWithFormattedTimeStampSuffix(date, ":");
    }

    /**
     * Returns an id of 54 bytes length (a UUID + idTimeStampSeparator + formatted timestamp)
     * The timestamp will be in the default yyyyMMddHHMMSSsss format.
     * For example: 7eb164dc-1b5e-40db-b300-d91701859ee2:20111117152731796, if the provided idTimeStampSeparator was ":"
     *
     * @param date                 the date that should be formatted.
     * @param idTimeStampSeparator the seperator character to use to separate the id from the timestamp portion.
     * @return a id consisting of a UUID a separator and the given date formatted as a string according to the default timestamp pattern.
     */
    public static String createIdWithFormattedTimeStampSuffix(final Date date, final String idTimeStampSeparator) {
        StringBuffer idBuffer;
        idBuffer = new StringBuffer();
        idBuffer.append(createId());
        idBuffer.append(idTimeStampSeparator);
        idBuffer.append(getFormattedDateTimeShort(date));
        return idBuffer.toString();
    }


    /**
     * Returns an id of 54 bytes length (a UUID + ":" + date formatted as pattern)
     * For example (with pattern yyyyMMddHHMMSSsss): 7eb164dc-1b5e-40db-b300-d91701859ee2:20111117152731796
     *
     * @param date        the date that should be formatted.
     * @param datePattern the pattern according to which the date should be formatted.
     * @return a id consisting of a UUID a ":" and the given date formatted as a string according to the pattern
     */
    public static String createIdWithFormattedTimeStampPatternSuffix(final Date date, final String datePattern) {
        StringBuffer idBuffer;
        idBuffer = new StringBuffer();
        idBuffer.append(createId());
        idBuffer.append(":");
        idBuffer.append(getFormattedDate(date, datePattern));
        return idBuffer.toString();
    }

    /**
     * Returns an id consisting of the current time in milliseconds followed by a "-" dash followed by a Random
     * number.
     *
     * @return an id consisting consisting of the current time in milliseconds followed by a "-" dash followed by a Random
     */
    public static String createSysTimeRandomId() {
        StringBuffer idBuffer = new StringBuffer();
        idBuffer.append(System.currentTimeMillis());
        idBuffer.append("-");
        idBuffer.append(new Random().nextInt(RANDOM_NUMBER_BOUND));
        return idBuffer.toString();
    }

    public static String getDateAsHexString(final Date date) {
        return Long.toHexString(date.getTime());
    }

    /**
     * Returns the current Date as a String formatted according to the YYYYMMDDHHMMSSSSS_PATTERN pattern.
     *
     * @return current date formatted according to the pattern.
     */
    public static String getFormattedDateTimeShort() {
        return getFormattedDate(new Date(), YYYYMMDDHHMMSSSSS_PATTERN);
    }

    /**
     * Returns the given Date as a String formatted according to the YYYYMMDDHHMMSSSSS_PATTERN pattern.
     *
     * @param date the date that should be formatted.
     * @return date formatted according to the pattern.
     */
    public static String getFormattedDateTimeShort(final Date date) {
        return getFormattedDate(date, YYYYMMDDHHMMSSSSS_PATTERN);
    }

    /**
     * Returns the given Date as a String formatted according to a pattern.
     *
     * @param date    the date that should be formatted.
     * @param pattern the pattern according to which the date should be formatted.
     * @return date formatted according to the pattern.
     */
    public static String getFormattedDate(final Date date, final String pattern) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(pattern);
        return dateFormat.format(date);
    }
}




