package be.ictdynamic.dynarouteservice.common.beanutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * BaseConverter implementation to parse an object of type Byte array into a String.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 12-sep-2008
 */
public class ByteArrayConverter extends BaseConverter<byte[]> {
    /**
     * Logger responsible for logging and debugging statements.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ByteArrayConverter.class);

    /**
     * Constructor.
     */
    ByteArrayConverter() {
    }

    /**
     * Parses the specified input string into an object of the specified data type.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    protected byte[] parse(Class<byte[]> type, String input) {
        byte[] bytes = new byte[0];
        try {
            bytes = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Exception (" + e.getClass().getName() + ") caught in parse: " + e.getMessage(), e);
        }
        return bytes;
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(byte[] input) {
        String converted = null;
        try {
            converted = new String(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Exception (" + e.getClass().getName() + ") caught in toString: " + e.getMessage(), e);
        }
        return converted;
    }
}