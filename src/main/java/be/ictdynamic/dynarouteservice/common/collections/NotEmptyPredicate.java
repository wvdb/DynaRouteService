package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Predicate implementation that returns true if the input is not null or if the string object is not empty.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <T> The type to predicate
 * @since 5-mrt-2007
 */
public final class NotEmptyPredicate<T> implements Predicate<T> {

    /**
     * NotEmptyPredicate referencing the single instance of this class.
     */
    private static final Predicate INSTANCE = new NotEmptyPredicate();

    /**
     * Returns the single instance of this class.
     *
     * @return NotEmptyPredicate referencing the single instance of this class.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> Predicate<T> getInstance() {
        return NotEmptyPredicate.INSTANCE;
    }

    /**
     * Private constructor to prevent this singleton class from being instantiated.
     */
    private NotEmptyPredicate() {
    }

    /**
     * Evaluates the predicate returning true if the object does not equal null or if the string object is not empty.
     *
     * @param object Object referencing the object to evaluate for null or empty.
     * @return boolean flag with value true if the evaluated object is not null or not empty, false when null or empty.
     */
    public boolean evaluate(T object) {
        return (object != null) && (StringUtils.isNotEmpty(ObjectUtils.toString(object)));
    }
}