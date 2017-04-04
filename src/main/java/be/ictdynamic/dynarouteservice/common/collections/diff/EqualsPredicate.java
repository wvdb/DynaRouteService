package be.ictdynamic.dynarouteservice.common.collections.diff;

import org.apache.commons.collections15.Predicate;

/**
 * Class EqualsPredicate.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since Oct 27, 2009
 * @param <T> The type of element
 */
public class EqualsPredicate<T> implements Predicate<T> {
    private final T element;
    private final DiffEquals<T> equals;

    public EqualsPredicate(T element, DiffEquals<T> equals) {
        this.element = element;
        this.equals = equals;
    }

    public boolean evaluate(T t) {
        boolean equal;
        if (t == null && element == null) {
            equal = true;
        } else if (t == null || element == null) {
            equal = false;
        } else {
            equal = equals.isEqual(element, t);
        }
        return equal;
    }
}
