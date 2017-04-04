package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseConverter implementation to convert a String into an enum type.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 29-mrt-2007
 */
public class EnumConverter extends BaseConverter<Enum> {

    /**
     * Logger responsible for logging and debugging statements.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EnumConverter.class);

    /**
     * Parses the specified input string into an object of the specified data type.
     * <p/>
     * This method parses a string to a enum instance.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    @SuppressWarnings({"unchecked"})
    protected Enum parse(Class<Enum> type, String input) {
        Enum result = null;
        try {
            result = Enum.valueOf(type, input);
        } catch (IllegalArgumentException iae) {
            LOG.warn(iae.getMessage());
        }
        return result;
    }


    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(Enum input) {
        return ObjectUtils.toString(input);
    }
}