package be.ictdynamic.dynarouteservice.common.beanutils;

import be.ictdynamic.common.lang.DateFormatUtilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BaseDateConverter implementation to convert a String into a java.sql.Timestamp.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 20-okt-2008
 */
public class TimestampConverter extends BaseDateConverter<Timestamp> {

    /**
     * Constructor.
     */
    TimestampConverter() {
    }

    @Override
    protected String toString(Timestamp input) {
        return this.getDateFormat(DateFormatUtilities.DATE_TIME_FORMAT_PATTERN).format(input);
    }

    /**
     * Parses the specified input string into an object of the specified data type.
     * <p/>
     * This method parses a string to a date, using strict parsing and string length checking.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    protected Timestamp parse(Class<Timestamp> type, String input) {
        return CastUtilities.castTimestamp(this.parse(input, this.getDateFormat(DateFormatUtilities.DATE_TIME_FORMAT_PATTERN)));
    }

    /**
     * Parses the specified input string in the specified format into a util date.
     * <p/>
     * This method parses a string to a date, using strict parsing and string length checking.
     *
     * @param input   String referencing the input string to be parsed to a util date.
     * @param pattern String referencing the pattern of the format of the specified string to be parsed.
     * @return Date referencing the string parse result in a util date, null when input is null or when an exception is thrown.
     */
    public static Timestamp parse(String input, String pattern) {
        TimestampConverter converter = (TimestampConverter) ConvertUtilities.getInstance().lookup(Timestamp.class);
        SimpleDateFormat format = converter.getDateFormat(pattern);
        return CastUtilities.castTimestamp(converter.parse(input, format));
    }

    /**
     * Parses the specified input string in the specified format into a util date.
     * <p/>
     * This method parses a string to a date, using strict parsing and string length checking.
     *
     * @param input  String referencing the input string to be parsed to a util date.
     * @param format SimpleDateFormat referencing the format of the specified string to be parsed.
     * @return Date referencing the string parse result in a util date, null when input is null or when an exception is thrown.
     */
    @Override
    protected Timestamp parse(String input, SimpleDateFormat format) {
        return CastUtilities.castTimestamp(super.parse(input, format));
    }
}