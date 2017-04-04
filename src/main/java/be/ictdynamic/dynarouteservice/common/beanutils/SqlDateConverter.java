package be.ictdynamic.dynarouteservice.common.beanutils;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * BaseDateConverter implementation to convert a String into a java.sql.Date.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 18-okt-2007
 */
public class SqlDateConverter extends BaseDateConverter<Date> {

    /**
     * Constructor.
     */
    SqlDateConverter() {
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
    protected Date parse(Class<Date> type, String input) {
        return this.parse(input, this.getDateFormat(BaseDateConverter.DEFAULT_DATE_PATTERN));
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
    public static Date parse(String input, String pattern) {
        SqlDateConverter converter = (SqlDateConverter) ConvertUtilities.getInstance().lookup(Date.class);
        SimpleDateFormat format = converter.getDateFormat(pattern);
        return converter.parse(input, format);
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
    protected Date parse(String input, SimpleDateFormat format) {
        return CastUtilities.castSqlDate(super.parse(input, format));
    }
}