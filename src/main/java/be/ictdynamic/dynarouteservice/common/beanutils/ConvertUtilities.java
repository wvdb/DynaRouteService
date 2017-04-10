package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

/**
 * Static class providing utility methods related to object type conversion.
 * <p/>
 * This class simply wraps the Jakarta Commons ConvertUtils functionality.
 * This class additionally handles null or empty input by returning a null object.
 * It also catches exceptions (even runtime exceptions), logs them as a warning and returns a null object instead.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 6-mrt-2007
 */
public final class ConvertUtilities extends ConvertUtilsBean {

    /**
     * Logger responsible for logging and debugging statements.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConvertUtilities.class);

    /**
     * ConvertUtilities referencing the single instance of this class.
     */
    private static final ConvertUtilities INSTANCE = new ConvertUtilities();

    /**
     * Returns the single instance of this class.
     *
     * @return ConvertUtilities referencing the single instance of this class.
     */
    public static ConvertUtilities getInstance() {
        return ConvertUtilities.INSTANCE;
    }

    /**
     * Private constructor to prevent this static class from being instantiated outside this class.
     */
    private ConvertUtilities() {
        super();

        // Explicitly deregistering converters for object types to prevent different/unexpected application behaviour.
        this.deregister(BigDecimal.class);
        this.deregister(BigInteger.class);
        this.deregister(Boolean.TYPE);
        this.deregister(Boolean.class);
        this.deregister(ArrayUtils.EMPTY_BOOLEAN_ARRAY.getClass());
        this.deregister(Byte.TYPE);
        this.deregister(Byte.class);
        this.deregister(ArrayUtils.EMPTY_BYTE_ARRAY.getClass());
        this.deregister(Character.TYPE);
        this.deregister(Character.class);
        this.deregister(ArrayUtils.EMPTY_CHAR_ARRAY.getClass());
        this.deregister(Class.class);
        this.deregister(Double.TYPE);
        this.deregister(Double.class);
        this.deregister(ArrayUtils.EMPTY_DOUBLE_ARRAY.getClass());
        this.deregister(Float.TYPE);
        this.deregister(Float.class);
        this.deregister(ArrayUtils.EMPTY_FLOAT_ARRAY.getClass());
        this.deregister(Integer.TYPE);
        this.deregister(Integer.class);
        this.deregister(ArrayUtils.EMPTY_INT_ARRAY.getClass());
        this.deregister(Long.TYPE);
        this.deregister(Long.class);
        this.deregister(ArrayUtils.EMPTY_LONG_ARRAY.getClass());
        this.deregister(Short.TYPE);
        this.deregister(Short.class);
        this.deregister(ArrayUtils.EMPTY_SHORT_ARRAY.getClass());
        this.deregister(String.class);
        this.deregister(ArrayUtils.EMPTY_STRING_ARRAY.getClass());
        this.deregister(Date.class);
        this.deregister(Time.class);
        this.deregister(Timestamp.class);
        this.deregister(java.sql.Date.class);
        this.deregister(File.class);
        this.deregister(Enum.class);
        this.deregister(URL.class);

        // Explicitly registering converters for the object types covered by this functionality.
        this.register(new ObjectConverter(), Object.class);
        this.register(new StringConverter(), String.class);
        this.register(new WrappingConverter(new IntegerConverter()), Integer.class);
        this.register(new WrappingConverter(new IntegerConverter()), Integer.TYPE);
        this.register(new WrappingConverter(new LongConverter()), Long.class);
        this.register(new WrappingConverter(new LongConverter()), Long.TYPE);
        this.register(new DoubleConverter(), Double.class);
        this.register(new DoubleConverter(), Double.TYPE);
        this.register(new WrappingConverter(new BigDecimalConverter()), BigDecimal.class);
        this.register(new WrappingConverter(new BigIntegerConverter()), BigInteger.class);
        this.register(new WrappingConverter(new BooleanConverter()), Boolean.class);
        this.register(new WrappingConverter(new BooleanConverter()), Boolean.TYPE);
        this.register(new WrappingConverter(new CharacterConverter()), Character.class);
        this.register(new WrappingConverter(new CharacterConverter()), Character.TYPE);
        this.register(new UtilDateConverter(), Date.class);
        this.register(new SqlDateConverter(), java.sql.Date.class);
        this.register(new TimestampConverter(), Timestamp.class);
        this.register(new LocalDateConverter(), LocalDate.class);
        this.register(new LocalDateTimeConverter(), LocalDateTime.class);
        this.register(new ByteArrayConverter(), ArrayUtils.EMPTY_BYTE_ARRAY.getClass());
        this.register(new EnumConverter(), Enum.class);
        this.register(new ClassConverter(), Class.class);
    }

    /**
     * Look up and return any registered {@link Converter} for the specified destination class.
     * <p/>
     * If there is no registered Converter for the specified destination class,
     * a lookup is performed for one of the super classes of the specified destination class.
     * When found, the converter is automatically registered for the specified destination class.
     * <p/>
     * If there is no registered Converter for the specified destination class or one of its super classes, return <code>null</code>.
     *
     * @param type Class for which to return a registered Converter.
     * @return the converter
     */
    @Override
    public Converter lookup(Class type) {
        Converter converter = ConvertUtils.lookup(type);

        if (converter == null) {
            converter = lookupSuperType(type, converter);
        }

        return converter;
    }

    private Converter lookupSuperType(Class type, Converter c) {
        Converter converter = c;
        Class superType = type.getSuperclass();
        if (superType != null) {
            converter = this.lookup(superType);
            if (converter != null) {
                if (ClassUtils.isAssignable(converter.getClass(), ObjectConverter.class)) {
                    LOG.warn("Dummy converter is registered for type " + type.getName() + ". Specify a custom converter if any conversion is required.", new ClassCastException(converter.getClass().getName()));
                }
                this.register(converter, type);
            }
        }
        return converter;
    }

    /**
     * Nullsafe method that parses the specified input string into an Integer.
     *
     * @param input String referencing the string to be parsed to an Integer, may be null.
     * @return Integer referencing the Integer parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Integer parseInteger(String input) {
        return ConvertUtilities.parse(input, Integer.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an Long.
     *
     * @param input String referencing the string to be parsed to an Long, may be null.
     * @return Long referencing the Long parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Long parseLong(String input) {
        return ConvertUtilities.parse(input, Long.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an Double.
     *
     * @param input String referencing the string to be parsed to an Double, may be null.
     * @return Double referencing the Double parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Double parseDouble(String input) {
        return ConvertUtilities.parse(input, Double.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an Boolean.
     *
     * @param input String referencing the string to be parsed to an Boolean, may be null.
     * @return Boolean referencing the Boolean parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Boolean parseBoolean(String input) {
        return ConvertUtilities.parse(input, Boolean.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an java.util.Date.
     *
     * @param input String referencing the string to be parsed to an java.util.Date, may be null.
     * @return java.util.Date referencing the date parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Date parseUtilDate(String input) {
        return ConvertUtilities.parse(input, Date.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an java.sql.Timestamp.
     *
     * @param input String referencing the string to be parsed to an java.sql.Timestamp, may be null.
     * @return java.sql.Timestamp referencing the timestamp parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Timestamp parseTimestamp(String input) {
        return ConvertUtilities.parse(input, Timestamp.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an java.sql.Date.
     *
     * @param input String referencing the string to be parsed to an java.sql.Date, may be null.
     * @return java.sql.Date referencing the date parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static java.sql.Date parseSqlDate(String input) {
        return ConvertUtilities.parse(input, java.sql.Date.class);
    }

    /**
     * Nullsafe method that parses the specified input string into a LocalDate.
     *
     * @param input String referencing the string to be parsed to a LocalDate, may be null.
     * @return LocalDate referencing the LocalDate parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static LocalDate parseLocalDate(String input) {
        return ConvertUtilities.parse(input, LocalDate.class);
    }

    /**
     * Nullsafe method that parses the specified input string into a LocalDateTime.
     *
     * @param input String referencing the string to be parsed to a LocalDateTime, may be null.
     * @return LocalDateTime referencing the LocalDateTime parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static LocalDateTime parseLocalDateTime(String input) {
        return ConvertUtilities.parse(input, LocalDateTime.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an Class.
     *
     * @param input String referencing the string to be parsed to an Class, may be null.
     * @return Class referencing the Class parsed from the specified string, null when input is null or when an exception is thrown.
     */
    public static Class parseClass(String input) {
        return ConvertUtilities.parse(input, Class.class);
    }

    /**
     * Nullsafe method that parses the specified input string into an enum of the specified type.
     *
     * @param input String referencing the string to be parsed to an enum, may be null.
     * @param type  Class referencing the type of the enum to parse the input into.
     * @return Enum referencing the Enum parsed from the specified string, null when input is null or when an exception is thrown.
     * @throws IllegalArgumentException Thrown when the specified type is not an enum type.
     */
    public static <T> T parseEnum(String input, Class<T> type) {
        ConvertUtils.register(ConvertUtils.lookup(Enum.class), type);
        return ConvertUtilities.parse(input, type);
    }

    /**
     * Nullsafe method that parses the specified input string into an object of the specified data type.
     * <p/>
     * Utility method that delegates the application logic to the ConvertUtils class and is invoked by each parser method.
     * <p/>
     * Also, any exception thrown during the parsing process is caught, logged and a null object is returned instead.
     *
     * @param input String referencing the input string to be parsed to the specified data type.
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    public static <T> T parse(String input, Class<T> type) {
        LOG.trace((LOG.isTraceEnabled() ? "Parsing " + input + " to " + type.getName() : null));
        return type.cast(ConvertUtils.convert(input, type));
    }

    /**
     * Nullsafe method that parses the array of specified values to an array of objects of the data type.
     * <p/>
     * Also, any exception thrown during the parsing process is caught, logged and a null object is returned instead.
     *
     * @param input Array of Strings referencing the array of string values to be parsed to an array of objects of the specified data type.
     * @param type  Class referencing the data type the specified array of string values must be parsed into.
     * @return Array of Objects referencing the string array parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T[] parse(String[] input, Class<T> type) {
        LOG.trace((LOG.isTraceEnabled() ? "Parsing " + Arrays.toString(input) + " to " + type.getName() : null));
        T[] output = null;

        if (input != null) {
            output = (T[]) ConvertUtils.convert(input, type);
        }

        return output;
    }

    /**
     * Nullsafe method that converts the specified input object into a string representation.
     *
     * @param input Object referencing the object to convert to a string representation, may be null.
     * @return String referencing the string representation of the specified object, null when input is null or empty or when an exception is thrown.
     */
    public static String toString(Object input) {
        String output;

        if (input != null && input.getClass() != String.class) {
            LOG.trace((LOG.isTraceEnabled() ? "Parsing object of type " + input.getClass().getName() + " to " + String.class.getName() : null));
            BaseConverter converter = (BaseConverter) ConvertUtilities.getInstance().lookup(input.getClass());
            output = converter.toString(String.class, input);
        } else {
            output = (String) input;
        }

        return output;
    }

    /**
     * Register a custom {@link Converter} for the specified destination <code>Class</code>, replacing any previously registered Converter.
     *
     * @param converter Converter to be registered
     * @param clazz     Destination class for conversions performed by this Converter
     */
    @Override
    public void register(Converter converter, Class clazz) {
        ConvertUtils.register(converter, clazz);
    }

    /**
     * Remove any registered {@link Converter} for the specified destination <code>Class</code>.
     *
     * @param clazz Class for which to remove a registered Converter.
     */
    @Override
    public void deregister(Class clazz) {
        ConvertUtils.deregister(clazz);
    }

    /**
     * Called by the garbage collector on an object when garbage collection determines that there are no more references to the object.
     *
     * @throws Throwable the <code>Exception</code> raised by this method
     */
    @Override
    protected void finalize() throws Throwable {
        ConvertUtils.deregister();
        super.finalize();
    }
}