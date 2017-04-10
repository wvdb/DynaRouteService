package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.dynarouteservice.common.beanutils.ConvertUtilities;
import org.apache.commons.lang3.StringUtils;

/**
 * Static class providing utility methods related to String and other CharSequence implementations.
 * <p/>
 * This class mainly extends the Jakarta Commons Lang StringUtils functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 26-okt-2010
 */
public final class StringUtilities {
    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private StringUtilities() {
    }

    /**
     * Surrounds (aka adds before and after) the input string with the specified padding character.
     * <p/>
     * - Null strings are handled as empty string.
     * - Null or empty padding characters will yield the input string.
     *
     * @param str     String referencing the string to be surrounded.
     * @param padchar char referencing the padchar to surround the input string with.
     * @return String referencing the surrounded string.
     */
    public static String surround(String str, char padchar) {
        return StringUtilities.surround(str, ConvertUtilities.toString(padchar));
    }

    /**
     * Surrounds (aka adds before and after) the input string with the specified padding character.
     * <p/>
     * - Null strings are handled as empty string.
     * - Null or empty padding characters will yield the input string.
     *
     * @param str     String referencing the string to be surrounded.
     * @param padchar String referencing the padchar to surround the input string with.
     * @return String referencing the surrounded string.
     */
    public static String surround(String str, String padchar) {
        String surrounded;

        if (StringUtils.isNotEmpty(padchar)) {
            if (StringUtils.isNotEmpty(str)) {
                surrounded = StringUtils.center(str, str.length() + (padchar.length() * 2), padchar);
            } else {
                surrounded = StringUtils.repeat(padchar, 2);
            }
        } else {
            surrounded = str;
        }

        return surrounded;
    }
}