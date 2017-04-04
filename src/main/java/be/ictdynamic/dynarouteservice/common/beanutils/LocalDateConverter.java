package be.ictdynamic.dynarouteservice.common.beanutils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * BaseJodaDateConverter implementation to parse an object of type LocalDate into a String.
 * <p/>
 * This class does not perform any conversion but exists to prevent the needless execution of the default conversion functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 26-nov-2009
 */
public class LocalDateConverter extends BaseJodaDateConverter<LocalDate> {
    /**
     * DateTimeFormatter constant referencing the default formatter for a LocalDate.
     */
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormat.forPattern(BaseDateConverter.DEFAULT_DATE_PATTERN);

    /**
     * Constructor.
     */
    LocalDateConverter() {
    }

    /**
     * {@inheritDoc}
     */
    protected LocalDate parse(Class<LocalDate> type, String input) {
        return this.parseDateTime(input, DEFAULT_FORMATTER).toLocalDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String toString(LocalDate input) {
        return this.toString(input, DEFAULT_FORMATTER);
    }

    /**
     * Utility method that parses the specified input into a LocalDate from the specified pattern.
     *
     * @param input   String referencing the text to be parsed into a DateTime.
     * @param pattern String referencing the pattern to use during parsing.
     * @return LocalDate referencing the parsed result.
     */
    public static LocalDate parse(String input, String pattern) {
        LocalDateConverter converter = (LocalDateConverter) ConvertUtilities.getInstance().lookup(LocalDate.class);
        return converter.parseDateTime(input, pattern).toLocalDate();
    }
}