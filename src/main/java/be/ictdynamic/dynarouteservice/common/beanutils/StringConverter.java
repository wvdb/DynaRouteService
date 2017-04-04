package be.ictdynamic.dynarouteservice.common.beanutils;

/**
 * BaseConverter implementation to parse an object of type String into a String.
 * <p/>
 * This class does not perform any conversion but exists to prevent the needless execution of the default conversion functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 5-mrt-2007
 */
public class StringConverter extends BaseConverter<String> {

    /**
     * Constructor.
     */
    StringConverter() {
    }

    /**
     * Parses the specified input string into an object of the specified data type.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    protected String parse(Class<String> type, String input) {
        return input;
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(String input) {
        return input;
    }
}