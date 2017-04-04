package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.common.collections.CollectionUtilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class containing methods for Arrays.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 19-mei-2008
 */
public final class ArrayUtilities {

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private ArrayUtilities() {
    }

    public static byte[] copy(byte[] source) {
        byte[] copy = null;

        if (source != null) {
            copy = Arrays.copyOf(source, source.length);
        }

        return copy;
    }

    public static <E> E[] copy(E[] source) {
        E[] copy = null;

        if (source != null) {
            copy = Arrays.copyOf(source, source.length);
        }

        return copy;
    }

    /**
     * Returns the first element of the incoming array, if the array is not empty.
     *
     * @param array Array of Objects to return the first element from, if not empty
     * @return Object referencing the first element of the incoming array
     */
    public static <E> E firstElement(E[] array) {
        return ArrayUtilities.getElement(array, 0);
    }

    /**
     * Returns the last element of the incoming array, if the array is not empty.
     *
     * @param array Array of Objects to return the last element from, if not empty
     * @return Object referencing the last element of the incoming array
     */
    public static <E> E lastElement(E[] array) {
        return ArrayUtilities.getElement(array, CollectionUtils.size(array) - 1);
    }

    /**
     * Joins all given arrays into one array
     *
     * @param sources The source arrays
     * @param <E>     The type of elements in the arrays
     * @return The joinded array
     */
    @SuppressWarnings({"unchecked"})
    public static <E> E[] join(E[]... sources) {
        Collection<E> elements = new ArrayList<E>();

        for (E[] source : sources) {
            elements.addAll(Arrays.asList(source));
        }

        E[] array = null;
        if (CollectionUtils.isNotEmpty(elements)) {
            array = (E[]) Array.newInstance(CollectionUtilities.firstElement(elements).getClass(), elements.size());
            array = elements.toArray(array);
        }

        return array;
    }

    /**
     * Returns the index-th element of the incoming array, if the array is not empty.
     *
     * @param array Array of Objects to return the index-th element from, if not empty
     * @param index int referencing the position of the element to return, in the specified array.
     * @return Object referencing the index-th element of the incoming array
     */
    public static <E> E getElement(E[] array, int index) {
        E element = null;

        if (!ArrayUtils.isEmpty(array) && index < array.length) {
            element = array[index];
        }

        return element;
    }

    /**
     * Adds the.
     *
     * @param array  the array
     * @param object the object
     * @return the t[]
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T[] add(T[] array, T object) {
        return (T[]) ArrayUtils.add(array, object);
    }

    @SuppressWarnings({"unchecked"})
    public static <E> E[] addAll(E[] array, E... elements) {
        return (E[]) ArrayUtils.addAll(array, elements);
    }

    /**
     * Inserts the specified object into the specified array at the specified index.
     *
     * @param array  Array of Objects referencing the array to insert the specified object into.
     * @param object Object referencing the object to be inserted into the specified array.
     * @param index  int referencing the index at which the object must be inserted.
     * @return Array of Objects referencing the array with the inserted object.
     */
    public static <T> T[] insert(T[] array, T object, int index) {
        Assert.notNull(array, "Can not insert an object into a null array.");

        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        } else if (index > array.length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        // add element to array
        T[] inserted = ArrayUtilities.add(array, object);
        // save old object at index
        T old = inserted[index];
        // replace with new object
        inserted[index] = object;
        for (int i = (index + 1); i < inserted.length; i++) {
            T temp = inserted[i];
            inserted[i] = old;
            old = temp;
        }

        return inserted;
    }

    /**
     * Removes the.
     *
     * @param array the array
     * @param index the index
     * @return the t[]
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T[] remove(T[] array, int index) {
        return (T[]) ArrayUtils.remove(array, index);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T[] removeElements(T[] array, T... elements) {
        T[] filtered = array;
        for (T element : elements) {
            filtered = (T[]) ArrayUtils.removeElement(filtered, element);
        }
        return filtered;
    }

    /**
     * Transforms the given array into an array of another type
     *
     * @param input       The input array
     * @param outputType  The outpu type
     * @param transformer The transformation
     * @param <I>         The input type
     * @param <O>         The outpu type
     * @return The transformed output
     */
    @SuppressWarnings({"unchecked"})
    public static <I, O> O[] transform(I[] input, Class<O> outputType, Transformer<I, O> transformer) {
        Collection<O> output = new ArrayList<O>();

        if (input != null) {
            for (I i : input) {
                output.add(transformer.transform(i));
            }
        }

        return CollectionUtilities.toArray(output, outputType);
    }

    /**
     * Finds an element in the given array using the given predicate
     *
     * @param array     The array to search
     * @param predicate The predicate
     * @param <A>       The type of array elements
     * @return The first element if found
     */
    public static <A> A find(A[] array, Predicate<A> predicate) {
        return org.apache.commons.collections15.CollectionUtils.find(Arrays.asList(array), predicate);
    }
}