package be.ictdynamic.dynarouteservice.common.collections;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator implementation comparing Sortable objects on their sort value.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <S>   Sortable implementation referencing the object types to be compared on their sort value.
 * @since 19-apr-2007 - 12:47:34
 */
public class SortableComparator<S extends Sortable> implements Comparator<S>, Serializable {
    private static final long serialVersionUID = 5901228089025952004L;

    /**
     * {@inheritDoc}
     */
    public int compare(Sortable o1, Sortable o2) {
        return ComparatorUtilities.nullSafeCompare(o1 != null ? o1.getSortValue() : null, o2 != null ? o2.getSortValue() : null);
    }
}
