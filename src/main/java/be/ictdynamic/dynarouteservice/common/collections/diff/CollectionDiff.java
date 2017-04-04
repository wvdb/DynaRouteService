package be.ictdynamic.dynarouteservice.common.collections.diff;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * Class CollectionDiff.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <T> The type of the elements in the collection
 * @since Oct 27, 2009
 */
public class CollectionDiff<T> {
    private Collection<? extends T> originalOld;
    private Collection<? extends T> originalNew;
    private Collection<T> added;
    private Collection<T> removed;
    private Collection<T> same;

    public CollectionDiff(final Collection<? extends T> oldCollection, final Collection<? extends T> newCollection, final DiffEquals<T> equals) {
        this.originalOld = oldCollection;
        this.originalNew = newCollection;

        if (isNotEmpty(oldCollection) && isNotEmpty(newCollection)) {
            getSame().addAll(CollectionUtils.select(newCollection, new Predicate<T>() {
                public boolean evaluate(final T element) {
                    return CollectionUtils.find(oldCollection, new EqualsPredicate<T>(element, equals)) != null;
                }
            }));
            getRemoved().addAll(extract(oldCollection, newCollection, equals));
            getAdded().addAll(extract(newCollection, oldCollection, equals));
        } else if (isNotEmpty(oldCollection)) {
            getRemoved().addAll(oldCollection);
        } else if (isNotEmpty(newCollection)) {
            getAdded().addAll(newCollection);
        }
    }

    /**
     * Extract all elements from the source collection which are not present in the 'check' collection
     *
     * @param source The collection to check
     * @param check  The collection containing the elements
     * @param equals The equals check
     * @return  Collection
     */
    @SuppressWarnings({"unchecked"})
    private Collection<T> extract(Collection<? extends T> source, final Collection<? extends T> check, final DiffEquals<T> equals) {
        return (Collection<T>) CollectionUtils.select(source, new Predicate<T>() {
            public boolean evaluate(final T oldElement) {
                return CollectionUtils.find(check, new EqualsPredicate<T>(oldElement, equals)) == null;
            }
        });
    }

    public Collection<T> getAdded() {
        if (this.added == null) {
            this.setAdded(new ArrayList<T>());
        }
        return added;
    }

    public Collection<T> getRemoved() {
        if (this.removed == null) {
            this.setRemoved(new ArrayList<T>());
        }
        return removed;
    }

    public Collection<T> getSame() {
        if (this.same == null) {
            this.setSame(new ArrayList<T>());
        }
        return same;
    }

    private void setAdded(Collection<T> added) {
        this.added = added;
    }

    private void setRemoved(Collection<T> removed) {
        this.removed = removed;
    }

    private void setSame(Collection<T> same) {
        this.same = same;
    }

    public Collection<? extends T> getOriginalOld() {
        return originalOld;
    }

    public void setOriginalOld(Collection<? extends T> originalOld) {
        this.originalOld = originalOld;
    }

    public Collection<? extends T> getOriginalNew() {
        return originalNew;
    }

    public void setOriginalNew(Collection<? extends T> originalNew) {
        this.originalNew = originalNew;
    }
}
