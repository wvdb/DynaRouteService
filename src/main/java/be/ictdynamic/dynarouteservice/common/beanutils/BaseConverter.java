package be.ictdynamic.dynarouteservice.common.beanutils;

import be.ictdynamic.dynarouteservice.common.lang.ObjectUtilities;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract super class for all Converter implementations that convert a String into an Object of the specified type.
 * <p/>
 * It adds functionality to convert an Object of any type into a String.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <T> The type to convert
 * @since 26-apr-2007
 */
public abstract class BaseConverter<T> implements Converter {

    /**
     * Logger responsible for logging and debugging statements.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaseConverter.class);

    /**
     * Constructor.
     */
    public BaseConverter() {
    }

    /**
     * Parses the specified input string into an object of the specified data type.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    protected abstract T parse(Class<T> type, String input);

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected abstract String toString(T input);

    /**
     * Nullsafe method that converts the specified input object into an output object of the specified type.
     * <p/>
     * It additionally checks for an empty or null string input.
     *
     * @param clazz  Class referencing the data type to which this value should be converted.
     * @param object Object referencing the input value to be converted.
     * @return Object referencing the input value converted to the specified data type.
     */
    @SuppressWarnings({"unchecked"})
    public Object convert(Class clazz, Object object) {
        Object converted = null;

        if (object != null && object.getClass() != clazz) {
            String input = ConvertUtilities.toString(object);
            if (StringUtils.isNotEmpty(input)) {
                try {
                    converted = this.parse(clazz, input);
                } catch (ConversionException ce) {
                    this.handleException(ce, clazz);
                }
            }
        } else {
            converted = object;
        }

        return converted;
    }

    /**
     * Nullsafe method that converts the specified input object into a string representation.
     * <p/>
     * It additionally checks for an empty or null string input.
     *
     * @param clazz  Class referencing the data type to which this value should be converted, i.e. String.
     * @param object Object referencing the object to convert to a string representation, may be null.
     * @return String referencing the string representation of the specified object, null when input is null or empty or when an exception is thrown.
     */
    @SuppressWarnings({"unchecked"})
    public String toString(Class clazz, Object object) {
        String converted = null;

        if (object != null && object.getClass() != String.class) {
            try {
                converted = this.toString((T) object);
            } catch (ConversionException ce) {
                this.handleException(ce, String.class);
            }
        } else {
            converted = (String) object;
        }

        return converted;
    }

    /**
     * Utility method that handles a ConversionException occurred during a conversion to the specified data type.
     *
     * @param ce   ConversionException referencing the exception occurred during a conversion.
     * @param type Class referencing the data type the conversion is intended for.
     */
    private void handleException(ConversionException ce, Class type) {
        Throwable exception = ObjectUtilities.defaultIfNull(ce.getCause(), ce);
        LOG.warn(exception.getClass().getSimpleName() + " when converting to " + type.getName() + ": " + exception.getMessage(), exception);
    }
}