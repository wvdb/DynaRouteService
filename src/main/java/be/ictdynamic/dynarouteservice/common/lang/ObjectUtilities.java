package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.dynarouteservice.common.beanutils.ConvertUtilities;
import be.ictdynamic.dynarouteservice.common.beanutils.ReflectionUtilities;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Class ObjectUtilities.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 26-jul-2007
 */
public final class ObjectUtilities {

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private ObjectUtilities() {
    }

    /**
     * Clone.
     *
     * @param serializable the serializable
     * @return the s
     */
    @SuppressWarnings({"unchecked"})
    public static <S extends Serializable> S clone(S serializable) {
        return (serializable == null ? null : (S) SerializationUtils.clone(serializable));
    }

    /**
     * Returns a String of at least the specified length, containing the representation of the specified number, left-padded with zeroes if necessary.
     *
     * @param nr     Number referencing the number to get the String representation from.
     * @param length int referencing the minimum length of the returning String.
     * @return String referencing the String representation of the specified number.
     */
    public static String toStringOfSize(Number nr, int length) {
        return StringUtils.leftPad(ConvertUtilities.toString(nr), length, '0');
    }

    /**
     * Returns a default value if the object passed is null.
     * <p/>
     * Generic version of the ObjectUtils.defaultIfNull method.
     *
     * @param object       Object referencing the object to be tested, may be null.
     * @param defaultValue Object referencing the default value to return, may be null.
     * @return Object referencing the specified object if it is not null, the specified defaultValue otherwise.
     * @see org.apache.commons.lang3.ObjectUtils
     */
    public static <T> T defaultIfNull(T object, T defaultValue) {
        return (object != null ? object : defaultValue);
    }

    /**
     * Tests the equality of the given objects Following the effective java guidelines. http://java.sun.com/docs/books/effective/index.html
     * The properties parameter sets which properties should be checked of the objects.
     *
     * @param o1         The first object
     * @param o2         The second object
     * @param properties The properties to check
     * @return true if the objects are equal, false otherwise
     */
    @SuppressWarnings({"PMD.OnlyOneReturn", "PMD.CompareObjectsWithEquals"})
    public static boolean equals(Object o1, Object o2, String... properties) {
        // voor PMD: dit laten
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1 == o2) {
            return true;
        }
        if (o1.getClass() != o2.getClass()) {
            return false;
        }

        boolean result = false;
        if (!ArrayUtils.isEmpty(properties)) {
            EqualsBuilder builder = new EqualsBuilder();
            for (String property : properties) {
                builder.append(
                        ReflectionUtilities.getProperty(o1, property),
                        ReflectionUtilities.getProperty(o2, property)
                );
            }
            result = builder.isEquals();
        }
        return result;
    }

    /**
     * Tests the equality of the given objects Following the effective java guidelines. http://java.sun.com/docs/books/effective/index.html
     * The properties parameter sets which properties should be checked of the objects. In this method, the class type is not checked
     *
     * @param o1         The first object
     * @param o2         The second object
     * @param properties The properties to check
     * @return true if the objects are equal, false otherwise
     */
    @SuppressWarnings({"PMD.OnlyOneReturn", "PMD.CompareObjectsWithEquals"})
    public static boolean equalsProperties(Object o1, Object o2, String... properties) {
        // voor PMD: dit laten
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1 == o2) {
            return true;
        }

        boolean result = false;
        if (!ArrayUtils.isEmpty(properties)) {
            EqualsBuilder builder = new EqualsBuilder();
            for (String property : properties) {
                builder.append(
                        ReflectionUtilities.getProperty(o1, property),
                        ReflectionUtilities.getProperty(o2, property)
                );
            }
            result = builder.isEquals();
        }
        return result;
    }

    /**
     * Calculates the hashcode of the given object using the given properties
     *
     * @param o          The object to calculate the hashcode for
     * @param properties The properties to use in the calculation
     * @return The hashcode
     */
    public static int hashCode(Object o, String... properties) {
        HashCodeBuilder builder = new HashCodeBuilder();
        for (String property : properties) {
            builder.append(ReflectionUtilities.getProperty(o, property));
        }
        return builder.toHashCode();
    }
}