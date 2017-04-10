package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract BaseConverter implementation to convert a String into a java.util.Date implementation.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <D> The date type
 * @since 18-okt-2007
 */
public abstract class BaseDateConverter<D extends Date> extends BaseConverter<D> {

    /**
     * String constant defining the pattern to be used by the date formatters.
     */
    public static final String DEFAULT_DATE_PATTERN = System.getProperty(DateFormat.class.getName() + ".DatePattern", "dd/MM/yyyy");

    /**
     * Constructor.
     */
    BaseDateConverter() {
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(D input) {
        return DateFormatUtils.format(input, BaseDateConverter.DEFAULT_DATE_PATTERN);
    }

    /**
     * Converts the specified input object into a string representation of the specified pattern.
     *
     * @param input   Object implementation referencing the object to be converted into a string representation of the specified pattern.
     * @param pattern String referencing the pattern of the converted string representation.
     * @return String referencing the string representation of the specified pattern of the specified object.
     */
    public static String toString(Date input, String pattern) {
        return DateFormatUtils.format(input, pattern);
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
    protected Date parse(String input, SimpleDateFormat format) {
        Date parsed = null;

        if (StringUtils.isNotEmpty(input)) {
            String pattern = format.toPattern();
            int length = pattern.length();
            if (input.length() != length) {
                // check for: Text can be quoted using single quotes (') to avoid interpretation. "''" represents a single quote.
                length = length - StringUtils.countMatches(pattern, "'") + StringUtils.countMatches(pattern, "''");
                if (input.length() != length) {
                    throw new ConversionException("Length mismatch: " + input);
                }
            }
            ParsePosition position = new ParsePosition(0);
            parsed = format.parse(input, position);
            if (position.getIndex() == 0 || position.getIndex() != input.length()) {
                throw new ConversionException(input);
            }
        }

        return parsed;
    }

    /**
     * Retrieves the date format associated with the specified pattern.
     * <p/>
     * When the date format for a pattern does not yet exist, it is created and cached.
     *
     * @param pattern String referencing the pattern to retrieve the date format for.
     * @return SimpleDateFormat referencing the date format associated with the specified pattern.
     * @throws IllegalArgumentException Thrown when the specified pattern is empty.
     */
    protected SimpleDateFormat getDateFormat(String pattern) {
        Validate.notEmpty(pattern, "Date pattern cannot be empty.");
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        // Setting strict parsing: inputs must match the format.
        dateFormat.setLenient(false);
        return dateFormat;
    }
}