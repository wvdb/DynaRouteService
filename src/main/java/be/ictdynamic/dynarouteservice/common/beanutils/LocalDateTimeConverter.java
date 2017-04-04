package be.ictdynamic.dynarouteservice.common.beanutils;

import be.ictdynamic.common.lang.DateFormatUtilities;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * BaseJodaDateConverter implementation to parse an object of type LocalDateTime into a String.
 * <p/>
 * This class does not perform any conversion but exists to prevent the needless execution of the default conversion functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 26-nov-2009
 */
public class LocalDateTimeConverter extends BaseJodaDateConverter<LocalDateTime> {
    /**
     * DateTimeFormatter constant referencing the default formatter for a LocalDateTime.
     */
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormat.forPattern(DateFormatUtilities.DATE_TIME_FORMAT_PATTERN);

    /**
     * Constructor.
     */
    LocalDateTimeConverter() {
    }

    /**
     * {@inheritDoc}
     */
    protected LocalDateTime parse(Class<LocalDateTime> type, String input) {
        return this.parseDateTime(input, DEFAULT_FORMATTER).toLocalDateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String toString(LocalDateTime input) {
        return this.toString(input, DEFAULT_FORMATTER);
    }

    /**
     * Utility method that parses the specified input into a LocalDateTime from the specified pattern.
     *
     * @param input   String referencing the text to be parsed into a DateTime.
     * @param pattern String referencing the pattern to use during parsing.
     * @return LocalDateTime referencing the parsed result.
     */
    public static LocalDateTime parse(String input, String pattern) {
        LocalDateTimeConverter converter = (LocalDateTimeConverter) ConvertUtilities.getInstance().lookup(LocalDateTime.class);
        return converter.parseDateTime(input, pattern).toLocalDateTime();
    }
}