package be.ictdynamic.dynarouteservice.common.collections.diff;

/**
 * Class DiffEquals.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since Oct 27, 2009
 * @param <T> The type of element
 */
public interface DiffEquals<T> {
    boolean isEqual(T t1, T t2);
}
