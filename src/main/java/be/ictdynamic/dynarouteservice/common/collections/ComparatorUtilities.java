package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.ComparatorUtils;
import org.apache.commons.collections15.comparators.ComparableComparator;

import java.util.Comparator;

/**
 * Static class providing utility methods related to object comparison.
 * <p/>
 * This class mainly invokes the Jakarta Commons Collections functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 28-feb-2007
 */
public final class ComparatorUtilities {

    /**
     * Comparator constant defining the comparator to compare 2 comparable objects, including null values.
     */
    private static final Comparator<Comparable> NULL_SAFE_OBJECT_COMPARATOR = ComparatorUtils.nullLowComparator(ComparableComparator.<Comparable>getInstance());

    /**
     * Comparator constant defining the comparator to compare 2 strings case insensitively, including null values.
     */
    private static final Comparator<String> NULL_SAFE_STRING_COMPARATOR = ComparatorUtils.nullLowComparator(String.CASE_INSENSITIVE_ORDER);

    /**
     * Comparator constant defining the comparator to compare 2 class case insensitively by their fully qualified name, including null values.
     */
    public static final Comparator<Class> NULL_SAFE_CLASS_COMPARATOR = new Comparator<Class>() {
        /**
         * {@inheritDoc}
         */
        public int compare(Class class1, Class class2) {
            return ComparatorUtilities.nullSafeCompareIgnoreCase((class1 != null ? class1.getName() : null), (class2 != null ? class2.getName() : null));
        }
    };

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private ComparatorUtilities() {
    }

    /**
     * Nullsafe method to compare 2 objects.
     *
     * @param o1 Comparable referencing the first object to be compared
     * @param o2 Comparable referencing the second object to be compared
     * @return int containing a negative integer, zero, or a positive integer as the first object is less than, equal to, or greater than the second object
     */
    public static int nullSafeCompare(Comparable o1, Comparable o2) {
        return ComparatorUtilities.NULL_SAFE_OBJECT_COMPARATOR.compare(o1, o2);
    }

    /**
     * Nullsafe method to compare 2 class lexicographically by their fully qualified name, ignoring case differences.
     *
     * @param o1 String referencing the first Class to be compared
     * @param o2 String referencing the second Class to be compared
     * @return int containing a negative integer, zero, or a positive integer as the first Class is less than, equal to, or greater than the second Class
     */
    public static int nullSafeCompare(Class o1, Class o2) {
        return ComparatorUtilities.NULL_SAFE_CLASS_COMPARATOR.compare(o1, o2);
    }

    /**
     * Nullsafe method to compare 2 strings lexicographically, ignoring case differences.
     *
     * @param o1 String referencing the first string to be compared
     * @param o2 String referencing the second string to be compared
     * @return int containing a negative integer, zero, or a positive integer as the first string is less than, equal to, or greater than the second string
     */
    public static int nullSafeCompareIgnoreCase(String o1, String o2) {
        return ComparatorUtilities.NULL_SAFE_STRING_COMPARATOR.compare(o1, o2);
    }
}