package be.ictdynamic.dynarouteservice.common.lang;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Static utility class containing constants and methods related to character encoding.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 12-jun-2008
 */
public final class EncodingUtilities {

    /**
     * Logger responsible for logging and debugging statements.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EncodingUtilities.class);

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private EncodingUtilities() {
    }

    /**
     * Encodes the specified text from UTF-8 to ISO-LATIN-1.
     *
     * @param str String referencing the text to be encoded.
     * @return String referencing the encoded text, or the specified text if no encoding is needed.
     */
    public static String convertISOToUTF8(String str) {
        return EncodingUtilities.encode(str, CharEncoding.ISO_8859_1, CharEncoding.UTF_8);
    }

    /**
     * Encodes the specified text from UTF-8 to ISO-LATIN-1.
     *
     * @param str String referencing the text to be encoded.
     * @return String referencing the encoded text, or the specified text if no encoding is needed.
     */
    public static String convertUTF8ToISO(String str) {
        return EncodingUtilities.encode(str, CharEncoding.UTF_8, CharEncoding.ISO_8859_1);
    }

    /**
     * Encodes the specified text from the specified character encoding to the specified character encoding.
     *
     * @param value          String referencing the text to be encoded.
     * @param encodingFrom String referencing the name of the character encoding to encode the text from.
     * @param encodingTo   String referencing the name of the character encoding to encode the text to.
     * @return String referencing the encoded text, or the specified text if no encoding is needed.
     */
    public static String encode(String value, String encodingFrom, String encodingTo) {
        String str = value;
        try {
            // Encoding is needed
            // when String is not empty
            boolean encodingNeeded = StringUtils.isNotEmpty(str);
            // when String length does not equals the number of bytes
            encodingNeeded = encodingNeeded && (str.length() != str.getBytes(encodingFrom).length);
            // when the encoding from and to are not empty
            encodingNeeded = encodingNeeded && StringUtils.isNotEmpty(encodingFrom) && StringUtils.isNotEmpty(encodingTo);
            // when the encoding from is not equal to the encoding to
            encodingNeeded = encodingNeeded && !StringUtils.equals(encodingFrom, encodingTo);

            if (encodingNeeded) {

                str = new String(str.getBytes(encodingFrom), encodingTo);

            }
        } catch (UnsupportedEncodingException uee) {
            LOG.warn(uee.getClass().getName() + " caught in encode: " + uee.getMessage(), uee);
        }
        return str;
    }
}