package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.text.DecimalFormat;

/**
 * Abstract BaseConverter implementation to convert a String into a java.lang.Double.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 18-okt-2007
 */
public class DoubleConverter extends WrappingConverter<Double> {

    /**
     * String constant defining the pattern to be used by the decimal formatters.
     */
    public static final String DEFAULT_DECIMAL_PATTERN = System.getProperty(DecimalFormat.class.getName() + ".DecimalPattern", "0.00");

    /**
     * Constructor.
     */
    DoubleConverter() {
        super(new org.apache.commons.beanutils.converters.DoubleConverter());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Double parse(Class<Double> type, String input) {
        return super.parse(type, StringUtils.replace(input, ",", "."));
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    @SuppressWarnings({"PMD.StringToString"})
    protected String toString(Double input) {
        return DoubleConverter.toString(input, DoubleConverter.DEFAULT_DECIMAL_PATTERN);
    }

    /**
     * Converts the specified input object into a string representation of the specified pattern.
     *
     * @param input   Object implementation referencing the object to be converted into a string representation of the specified pattern.
     * @param pattern String referencing the pattern of the converted string representation.
     * @return String referencing the string representation of the specified pattern of the specified object.
     */
    public static String toString(Double input, String pattern) {
        return DoubleConverter.getDecimalFormat(pattern).format(input);
    }

    /**
     * Retrieves the decimal format associated with the specified pattern.
     * <p/>
     * When the decimal format for a pattern does not yet exist, it is created and cached.
     *
     * @param pattern String referencing the pattern to retrieve the decimal format for.
     * @return DecimalFormat referencing the date format associated with the specified pattern.
     * @throws IllegalArgumentException Thrown when the specified pattern is empty.
     */
    private static DecimalFormat getDecimalFormat(String pattern) {
        Validate.notEmpty(pattern, "Decimal pattern cannot be empty.");
        return new DecimalFormat(pattern);
    }
}