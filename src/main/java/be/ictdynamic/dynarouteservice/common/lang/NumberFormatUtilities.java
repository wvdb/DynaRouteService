package be.ictdynamic.dynarouteservice.common.lang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Utilities related to number formatting.
 *
 * @author Pawel Baranik
 * @since 2015-01-05
 */
public final class NumberFormatUtilities {
    private NumberFormatUtilities() {
    }

    /**
     * Format number to given decimal places (from-to)
     *
     * @param value the value
     * @param minDecimals the decimals from
     * @param maxDecimals the decimals to
     * @return the string
     */
    public static String formatNumberDecimalsMinMax(Number value, int minDecimals, int maxDecimals) {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(new Locale("nl", "BE")));
        df.setMinimumFractionDigits(minDecimals);
        df.setMaximumFractionDigits(maxDecimals);
        return df.format(value);
    }

    /**
     * Format number to given decimal places (from)
     *
     * @param value the value
     * @param minDecimals the decimals from
     * @return the string
     */
    public static String formatNumberDecimalMin(Number value, int minDecimals) {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(new Locale("nl", "BE")));
        df.setMinimumFractionDigits(minDecimals);
        df.setMaximumFractionDigits(Integer.MAX_VALUE);
        return df.format(value);
    }

    /**
     * Format number to given decimal places (to)
     *
     * @param value the value
     * @param maxDecimals the decimals to
     * @return the string
     */
    public static String formatNumberDecimalMax(Number value, int maxDecimals) {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(new Locale("nl", "BE")));
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(maxDecimals);
        return df.format(value);
    }
}
