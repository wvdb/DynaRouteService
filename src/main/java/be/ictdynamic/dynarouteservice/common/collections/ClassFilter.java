package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;

/**
 * Predicate implementation that returns true if the input is assignable to the type stored in this predicate.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision$ $Date$
 * @since Commons Collections 3.0
 */
public final class ClassFilter implements Predicate<Class>, Serializable {
    private static final long serialVersionUID = -6682656911025165584L;

    /**
     * The type to compare to
     */
    private final Class iType;

    /**
     * Factory to create the class filter.
     *
     * @param type the type to check for, may not be null
     * @return the predicate
     * @throws IllegalArgumentException if the class is null
     */
    public static Predicate getInstance(Class type) {
        Validate.notNull(type, "The type to check assignability must not be null");
        return new ClassFilter(type);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     *
     * @param type the type to check for
     */
    public ClassFilter(Class type) {
        super();
        iType = type;
    }

    /**
     * Evaluates the predicate returning true if the input object is of the correct type.
     *
     * @param object the input object
     * @return true if input is of stored type
     */
    public boolean evaluate(Class object) {
        return ClassUtils.isAssignable(object, this.getType());
    }

    /**
     * Gets the type to compare to.
     *
     * @return the type
     * @since Commons Collections 3.1
     */
    public Class getType() {
        return iType;
    }
}
