package be.ictdynamic.dynarouteservice.common.collections;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class ReverseSortableComparator.
 *
 * @author David Van der Eyken
 * @version $Revision$
 * @param <S>   Sortable implementation referencing the objects to be compared by their sortValue.
 * @since 30-jul-2009
 */
public class ReverseSortableComparator<S extends Sortable> implements Comparator<S>, Serializable {
    private static final long serialVersionUID = 5901228089025952004L;

    /**
     * {@inheritDoc}
     */
    public int compare(Sortable o1, Sortable o2) {
        return ComparatorUtilities.nullSafeCompare(o2.getSortValue(), o1.getSortValue());
    }
}
