package be.ictdynamic.dynarouteservice.common.lang;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Static class providing utility methods related to classes.
 * <p/>
 * This class mainly extends the Jakarta Commons Lang ClassUtils functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 26-feb-2007
 */
public final class ClassUtilities {

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private ClassUtilities() {
    }

    /**
     * Nullsafe method to get the class name from the specified object.
     *
     * @param o Object referencing the object to get the class name from, may be null.
     * @return String referencing the full class name of the specified object, null if specified object is null.
     */
    public static String getClassName(Object o) {
        return (o == null ? null : o.getClass().getName());
    }

    /**
     * Returns a standard name based on the incoming class to be used as attribute name in the different scopes.
     *
     * @param clazz Class referencing the class the attribute name is based on.
     * @return String containing a standard name to be used as attribute name.
     */
    public static String getUncapitalizedName(Class clazz) {
        return StringUtils.uncapitalize(clazz.getSimpleName());
    }

    public static String getUncapitalizedListName(Class clazz) {
        return ClassUtilities.getUncapitalizedName(clazz) + List.class.getSimpleName();
    }

    /**
     * Casts the specified object to the specified type, when assignable, otherwise returns null.
     *
     * @param object Object referencing the object to be cast to the specified type.
     * @param type   Class referencing the type to cast the object to.
     * @param <T>    Object implementation referencing the type to cast the object to.
     * @return Object implementation referencing the specified object cast to the specified type, when assignable, otherwise returns null.
     */
    public static <T> T castIfAssignable(Object object, Class<T> type) {
        T castObject = null;

        if (ClassUtilities.isAssignable(object, type)) {
            castObject = type.cast(object);
        }

        return castObject;
    }

    /**
     * Checks if the object is can be cast to the given Class. If the object is null, true will be returned
     *
     * @param o        The object
     * @param assignee the Class to try to assign into, returns false if null
     * @return true if assignment possible
     */
    public static boolean isAssignable(Object o, Class assignee) {
        return (o == null || ClassUtils.isAssignable(o.getClass(), assignee));
    }

    /**
     * Checks if the source class is assignable to any of the given classes
     *
     * @param source  The source class
     * @param targets The targets to check
     * @return true if the source class is assignable
     */
    public static boolean isAssignableToOneOf(Class source, Class... targets) {
        boolean assignable = false;
        if (targets != null && source != null) {
            for (int i = 0; i < targets.length && !assignable; i++) {
                assignable = ClassUtils.isAssignable(source, targets[i]);
            }
        }
        return assignable;
    }

    /**
     * Checks if the source object is assignable to any of the given classes
     *
     * @param source  The source object
     * @param targets The targets to check
     * @return true if the source class is assignable
     */
    public static boolean isAssignableToOneOf(Object source, Class... targets) {
        boolean assignable = false;
        if (source != null) {
            assignable = isAssignableToOneOf(source.getClass(), targets);
        }
        return assignable;
    }

    /**
     * Gets all implementing interfaces of the given class
     *
     * @param clazz The class to get the interfaces from
     * @return The interfaces
     */
    @SuppressWarnings({"unchecked"})
    public static Collection<Class<?>> getAllInterfaces(Class clazz) {
        return ClassUtils.getAllInterfaces(clazz);
    }

    /**
     * Gets all implementing interfaces of the given object
     *
     * @param object The object to get the interfaces from
     * @return The interfaces
     */
    public static Collection<Class<?>> getAllInterfaces(Object object) {
        Collection<Class<?>> interfaces;
        if (object != null) {
            interfaces = ClassUtilities.getAllInterfaces(object.getClass());
        } else {
            interfaces = Collections.emptyList();
        }
        return interfaces;
    }
}