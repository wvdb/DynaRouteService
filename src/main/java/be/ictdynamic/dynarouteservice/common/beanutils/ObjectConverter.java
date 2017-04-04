package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Default/Dummy BaseConverter implementation to parse an object of any type into a String.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 27-apr-2007
 */
public class ObjectConverter extends BaseConverter<Object> {

    /**
     * Parses the specified input string into an object of the specified data type.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    protected Object parse(Class<Object> type, String input) {
        return input;
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(Object input) {
        return ObjectUtils.toString(input);
    }
}