package be.ictdynamic.dynarouteservice.common.collections;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.UniquePredicate;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.TruePredicate;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Utility class containing methods for Collections.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 24-Jun-2003
 */
@SuppressWarnings({"PMD.TooManyMethods"})
public final class CollectionUtilities {

    /**
     * Private constructor to prevent this class from being instantiated.
     */
    private CollectionUtilities() {
    }

    /**
     * Returns the first element of the incoming collection, if the collection is not empty.
     *
     * @param c Collection of Objects to return the first element from, if not empty
     * @return Object referencing the first element of the incoming collection
     */
    public static <E> E firstElement(Collection<E> c) {
        return CollectionUtilities.getElement(c, 0);
    }

    /**
     * Returns the first given number of elements (sizeAllowed)of the incoming collection, if the collection is not empty.
     *
     * @param c           Collection of Objects to return the first element from, if not empty
     * @param sizeAllowed Integer the maximum number of elements in the Collection
     * @return Object referencing the first element of the incoming collection
     */
    public static void firstElements(Collection<?> c, Integer sizeAllowed) {
        if (sizeAllowed != null && sizeAllowed > 0 && CollectionUtilities.size(c) > sizeAllowed) {
            Iterator it = c.iterator();

            // forwarding to the last allowed element
            for (int i = 0; i < sizeAllowed; i++) {
                it.next();
            }

            // removing all remaining elements
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
    }

    /**
     * Returns the last element of the incoming collection, if the collection is not empty.
     *
     * @param c Collection of Objects to return the last element from, if not empty
     * @return Object referencing the last element of the incoming collection
     */
    public static <E> E lastElement(Collection<E> c) {
        return CollectionUtilities.getElement(c, CollectionUtilities.size(c) - 1);
    }

    /**
     * Returns the index-th element of the incoming collection, if the collection is not empty.
     *
     * @param c     Collection of Objects to return the index-th element from, if not empty
     * @param index int referencing the position of the element to return, in the specified collection.
     * @return Object referencing the index-th element of the incoming collection
     */
    @SuppressWarnings({"unchecked"})
    public static <E> E getElement(Collection<E> c, int index) {
        E element = null;

        if (CollectionUtils.isNotEmpty(c) && index < CollectionUtilities.size(c)) {
            element = (E) CollectionUtils.get(c, index);
        }

        return element;
    }

    /**
     * Nullsafe method adding all elements of the source collection to the destination collection.
     *
     * @param destination Collection of Objects to add the elements of the source collection to.
     * @param source      Collection of Objects containing the elements to be added to the destination collection.
     * @return boolean flag with value true if the destination collection has been changed, false when not.
     */
    public static <E> boolean addAllIgnoreNull(Collection<E> destination, Collection<E> source) {
        boolean changed = false;

        if (destination != null && source != null) {
            changed = destination.addAll(source);
        }

        return changed;
    }

    /**
     * Nullsafe method returning the number of elements the incoming collection contains.
     *
     * @param c Collection of Objects to count the number of elements from
     * @return int containing the number of the elements in the collection, or -1 if the collection is null
     */
    public static int size(Collection c) {
        int size = -1;

        if (c != null) {
            size = c.size();
        }

        return size;
    }

    /**
     * Nullsafe method returning the number of entries the incoming map contains.
     *
     * @param m Map of Map.Entry objects to count the number of entries from
     * @return int containing the number of the entries in the map, or -1 if the map is null
     */
    public static int size(Map m) {
        int size = -1;

        if (m != null) {
            size = m.size();
        }

        return size;
    }

    /**
     * Converts the specified Array to a Collection containing the same elements.
     *
     * @param source Array of Objects to be converted to a Collection.
     * @return Collection of Objects containing the elements of the specified Array.
     */
    public static <E> Collection<E> toCollection(E[] source) {
        Collection<E> collection = null;

        if (source != null) {
            collection = new ArrayList<E>();

            for (E next : source) {
                if (next != null) {
                    collection.add(next);
                }
            }
        }

        return collection;
    }

    /**
     * Converts the specified Array to an unmodifiable Collection containing the same elements.
     *
     * @param source Array of Objects to be converted to an unmodifiable Collection.
     * @return Collection of Objects containing the elements of the specified Array.
     */
    public static <E> Collection<E> unmodifiableCollection(E... source) {
        return Collections.unmodifiableCollection(Arrays.asList(source));
    }

    /**
     * Converts the specified Array to an unmodifiable List containing the same elements.
     *
     * @param source Array of Objects to be converted to an unmodifiable List.
     * @return List of Objects containing the elements of the specified Array.
     */
    public static <E> List<E> unmodifiableList(E... source) {
        return Collections.unmodifiableList(Arrays.asList(source));
    }


    /**
     * Removes all duplicates in the specified collection based on the equals method implementation.
     *
     * @param errors Collection of Object implementations referencing the collection to remove the duplicates from.
     * @param <E>    Object implementation.
     */
    public static <E> void removeDuplicates(Collection<E> errors) {
        CollectionUtils.filter(errors, UniquePredicate.getInstance());
    }

    /**
     * Returns the size of the specified collection. If the specified collection is null, this method returns the specified default size.
     *
     * @param c           Collection of Objects referencing the collection to retrieve the size from.
     * @param defaultSize int referencing the size to be returned when the collection is null.
     * @return int referencing the size of the collection, or the default size when the collection is null.
     */
    public static int defaultSizeIfNull(Collection c, int defaultSize) {
        return (c == null ? defaultSize : c.size());
    }

    /**
     * Converts all the values of the given Collection into the target type.
     *
     * @param collection The Collection to convert
     * @param type       The type to convert the Collection values into
     * @return A Collection containing the converted values
     */
    public static <T, I> Collection<T> transform(Collection<I> collection, final Class<T> type) {
        return transform(collection, new SimpleObjectTransformer<I, T>(type));
    }

    /**
     * Casts all elements in the specified collection to the specified type.
     *
     * @param collection Collection of elements to be cast to the specified type.
     * @param type       Class referencing the type to cast the elements to.
     * @return Collection containing the cast elements.
     */
    public static <I, T extends I> Collection<T> castElements(Collection<I> collection, final Class<T> type) {
        return transform(collection, new CastObjectTransformer<I, T>(type));
    }

    /**
     * Transform.
     *
     * @param collection  the collection
     * @param transformer the transformer
     * @return the collection< t>
     */
    public static <T, I> Collection<T> transform(Collection<I> collection, Transformer<? super I, T> transformer) {
        return transform(collection, new LinkedList<T>(), transformer);
    }

    /**
     * Converts all the values of the given array into the target type.
     *
     * @param collection The Collection to convert
     * @param type       The type to convert the Collection values into
     * @return A Collection containing the converted values
     */
    public static <T, I> Collection<T> transform(I[] collection, final Class<T> type) {
        return transform(collection, new SimpleObjectTransformer<I, T>(type));
    }

    /**
     * Casts all elements in the specified array to the specified type.
     *
     * @param collection Collection of elements to be cast to the specified type.
     * @param type       Class referencing the type to cast the elements to.
     * @return Collection containing the cast elements.
     */
    public static <I, T extends I> Collection<T> castElements(I[] collection, final Class<T> type) {
        return transform(collection, new CastObjectTransformer<I, T>(type));
    }

    /**
     * Transform.
     *
     * @param collection  the collection
     * @param transformer the transformer
     * @return the collection< t>
     */
    public static <T, I> Collection<T> transform(I[] collection, Transformer<? super I, T> transformer) {
        return transform(toCollection(collection), new LinkedList<T>(), transformer);
    }

    /**
     * Transform.
     *
     * @param source      the source
     * @param target      the target
     * @param transformer the transformer
     * @return the collection< t>
     */
    public static <T, I, G extends Collection<T>> G transform(Collection<I> source, G target, Transformer<? super I, T> transformer) {
        if (source != null && target != null) {
            for (I entry : source) {
                target.add(transformer.transform(entry));
            }
        }
        return target;
    }

    /**
     * Selects all entries from the specified collection whose values match the specified predicate.
     *
     * @param collection The collection to filter
     * @param predicate  The Predicate which will evaluate if an Object is filtered or not
     * @return A filtered Collection
     */
    public static <V> Collection<V> select(Collection<V> collection, Predicate<V> predicate) {
        Collection<V> selection = null;

        if (collection != null) {
            selection = new LinkedHashSet<V>(collection.size());

            Predicate<V> valueCheck = getDefaultIfEmpty(predicate);
            org.apache.commons.collections15.CollectionUtils.select(collection, valueCheck, selection);
        }

        return selection;
    }

    /**
     * Removes all elements from the source collection present in the remove collection that match the given comperator
     *
     * @param source     The source collection
     * @param remove     The objects to remove
     * @param comparator The comperator. When the compare method returns 0, the object will be removed from the source collection
     * @param <V>        The type of objects
     * @return A filtered collection
     */
    public static <V> Collection<V> remove(Collection<V> source, final Collection<V> remove, final Comparator<? super V> comparator) {
        return CollectionUtilities.select(source, new Predicate<V>() {
            public boolean evaluate(final V sourceObject) {
                return org.apache.commons.collections15.CollectionUtils.countMatches(remove, new Predicate<V>() {
                    public boolean evaluate(V removeObject) {
                        return comparator.compare(sourceObject, removeObject) == 0;
                    }
                }) == 0;
            }
        });
    }

    /**
     * Gets the default if empty.
     *
     * @param predicate the predicate
     * @return the default if empty
     */
    @SuppressWarnings({"unchecked"})
    public static <T> Predicate<T> getDefaultIfEmpty(Predicate<T> predicate) {
        return (Predicate<T>) ObjectUtils.defaultIfNull(predicate, TruePredicate.getInstance());
    }

    /**
     * Transform filter.
     *
     * @param collection  the collection
     * @param predicate   the predicate
     * @param transformer the transformer
     * @return the collection< t>
     */
    public static <T, I> Collection<T> transformFilter(Collection<I> collection, Predicate<I> predicate, Transformer<I, T> transformer) {
        return transformFilter(collection, new ArrayList<T>(), predicate, transformer);
    }

    /**
     * Transform filter.
     *
     * @param source      the source
     * @param target      the target
     * @param predicate   the predicate
     * @param transformer the transformer
     * @return the collection< t>
     */
    public static <T, I, G extends Collection<T>> G transformFilter(Collection<I> source, G target, Predicate<I> predicate, Transformer<I, T> transformer) {
        if (source != null && target != null) {
            for (I entry : source) {
                if (predicate.evaluate(entry)) {
                    target.add(transformer.transform(entry));
                }
            }
        }
        return target;
    }

    /**
     * Converts the given Collection into an array of the given type.
     *
     * @param collection The collection to convert
     * @param arrayType  The type of array to return
     * @return The new array
     */
    @SuppressWarnings({"unchecked"})
    public static <E> E[] toArray(Collection<E> collection, Class<? extends E> arrayType) {
        return collection.toArray((E[]) Array.newInstance(arrayType, collection.size()));
    }

    /**
     * Removes the element at position 'index' from the collection.
     *
     * @param collection The collection containing the elements
     * @param index      The index to remove
     * @return The removed element or null if the element was not found.
     */
    public static <E> E remove(Collection<E> collection, int index) {
        E toRemove = getElement(collection, index);
        if (toRemove != null) {
            collection.remove(toRemove);
        }
        return toRemove;
    }

    /**
     * Transform a Collections to a Map, which contains the unique keys with associated grouped values per each key.
     * This method can be compared with MapUtilities.toMap(..), which uses Tranformers {@code (eg. new Transformer<F, T>(){..}) instead of (eg. implements Function<F, T>)}
     *
     * @param elements      An Iterable Collections of elements eg. List.
     * @param keyFunction   The Key of which we want to group by on. It's an enumeric which implements Function.
     * @param valueFunction The Values of which we want to group by on. It's an enumeric which implements Function.
     * @return Transformed Map of given collection whereby the values are grouped per each key, pronounced as a 'Collection' as value.
     */
    public static <E, K, V> Map<K, Collection<V>> uniqueKeyToGroupedValuesMap(Collection<E> elements, Function<E, K> keyFunction, Function<E, V> valueFunction) {
        Map<K, Collection<V>> resultMap = new HashMap<K, Collection<V>>();

        if (CollectionUtils.isNotEmpty(elements)) {
            ImmutableListMultimap<K, E> keysToElements = Multimaps.index(elements, keyFunction);
            ListMultimap<K, V> keysToValuesLazy = Multimaps.transformValues(keysToElements, valueFunction);

            resultMap = keysToValuesLazy.asMap();
        }

        return resultMap;
    }
}