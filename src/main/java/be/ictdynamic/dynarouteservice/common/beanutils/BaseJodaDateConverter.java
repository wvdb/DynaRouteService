package be.ictdynamic.dynarouteservice.common.beanutils;

import org.joda.time.DateTime;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Abstract BaseConverter implementation to convert a String into a org.joda.time.ReadablePartial implementation.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <P>   ReadablePartial implementation referencing the Joda Time object to be converted.
 * @since 26-nov-2009
 */
public abstract class BaseJodaDateConverter<P extends ReadablePartial> extends BaseConverter<P> {
    /**
     * Utility method that parses the specified input into a DateTime from the specified pattern.
     *
     * @param input   String referencing the text to be parsed into a DateTime.
     * @param pattern String referencing the pattern to use during parsing.
     * @return DateTime referencing the parsed result.
     */
    protected DateTime parseDateTime(String input, String pattern) {
        return this.parseDateTime(input, DateTimeFormat.forPattern(pattern));
    }

    /**
     * Utility method that parses the specified input into a DateTime using the specified formatter.
     *
     * @param input     String referencing the text to be parsed into a DateTime.
     * @param formatter DateTimeFormatter referencing the formatter used to parse the input.
     * @return DateTime referencing the parsed result.
     */
    protected DateTime parseDateTime(String input, DateTimeFormatter formatter) {
        return formatter.parseDateTime(input);
    }

    /**
     * Converts the specified input object into a string representation in the specified pattern.
     *
     * @param input   Object implementation referencing the object to be converted into a string representation.
     * @param pattern String referencing the pattern for the string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(P input, String pattern) {
        return this.toString(input, DateTimeFormat.forPattern(pattern));
    }

    /**
     * Converts the specified input object into a string representation using the specified formatter.
     *
     * @param input     Object implementation referencing the object to be converted into a string representation.
     * @param formatter DateTimeFormatter referencing the formatter used to convert the input.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(P input, DateTimeFormatter formatter) {
        return formatter.print(input);
    }
}
