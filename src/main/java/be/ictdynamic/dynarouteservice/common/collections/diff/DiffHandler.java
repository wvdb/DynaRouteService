package be.ictdynamic.dynarouteservice.common.collections.diff;

/**
 * Interface for classes that handle changes between 2 collections.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <T> Object implementation referencing the type of the element to handle.
 * @since Oct 27, 2009
 */
public interface DiffHandler<T> {
    /**
     * Invoked when the specified object is added to the collection.
     *
     * @param object Object implementation referencing the object added to the collection.
     */
    void doAdd(T object);

    /**
     * Invoked when the specified object is removed from the collection.
     *
     * @param object Object implementation referencing the object removed from the collection.
     */
    void doRemove(T object);

    /**
     * Invoked when the specified object is unchanged.
     *
     * @param object Object implementation referencing the unchanged object.
     */
    void doSame(T object);
}
