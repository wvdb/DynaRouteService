package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.common.beanutils.ConvertUtilities;
import org.apache.commons.beanutils.DynaBean;

import java.util.Collection;

/**
 * Class MathUtilities.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 25-May-2010
 */
public final class MathUtilities {

    private MathUtilities() {
    }

    /**
     * Null safe sum.
     *
     * @param double1 the double 1
     * @param double2 the double 2
     * @return the double
     */
    public static Double nullSafeSum(Double double1, Double double2) {
        if (double1 != null) {
            if (double2 != null) {
                return double1 + double2;
            } else {
                return double1;
            }
        } else {
            return double2;
        }
    }

    /**
     * Null safe sum.
     *
     * @param int1 the int 1
     * @param int2 the int 2
     * @return the integer
     */
    public static Integer nullSafeSum(Integer int1, Integer int2) {
        if (int1 != null) {
            if (int2 != null) {
                return int1 + int2;
            } else {
                return int1;
            }
        } else {
            return int2;
        }
    }

    /**
     * Calculates the sum of the given column of the given records of dynabeans.
     *
     * @param records  The records
     * @param property The property to get the sum from
     * @return The sum
     */
    public static double sum(Collection<DynaBean> records, String property) {
        double total = 0;
        for (DynaBean record : records) {
            total += ConvertUtilities.parseLong(ConvertUtilities.toString(record.get(property)));
        }
        return total;
    }
}
