package be.ictdynamic.dynarouteservice.common.collections;

import java.util.Comparator;

/**
 * Class Mappable.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since Jun 10, 2009
 */
public interface Mappable {
    /**
     * Comparator constant defining a default comparator for classes implementing this interface.
     */
    Comparator<Mappable> COMPARATOR = new Comparator<Mappable>() {
        /**
         * {@inheritDoc}
         */
        public int compare(Mappable mappable1, Mappable mappable2) {
            return ComparatorUtilities.nullSafeCompare(mappable1.getMapKey(), mappable2.getMapKey());
        }
    };

    /**
     * Should return the key when this object is being placed inside a map
     *
     * @return The key value
     */
    Comparable getMapKey();
}
