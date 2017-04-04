package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Static class providing utility methods related to maps.
 * <p/>
 * This class mainly invokes the Jakarta Commons Collections functionality, providing extensions.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 5-mrt-2007
 */
public final class MapUtilities {
    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private MapUtilities() {
    }

    /**
     * Removes all entries in the specified map that contain an empty or NULL value.
     *
     * @param map Map referencing the map to be checked for emtpy or NULL values.
     * @return Map referencing a new map containing all entries of the specified map that do not contain an empty or NULL value.
     */
    @SuppressWarnings({"unchecked"})
    public static <K, V> Map<K, V> filterEmptyValues(Map<K, V> map) {
        return MapUtilities.select(map, null, (Predicate<V>) NotEmptyPredicate.getInstance());
    }

    /**
     * Selects all entries from the specified map whose keys match the specified (first) predicate and whose values match the specified (second) predicate.
     *
     * @param map            Map referencing the map the entries are selected from.
     * @param keyPredicate   Predicate referencing the predicate the keys must match, may be null when no check is required.
     * @param valuePredicate Predicate referencing the predicate the values must match, may be null when no check is required.
     * @return Map referencing all entries from the specified map whose keys match the specified (first) predicate and whose values match the specified (second) predicate.
     */
    public static <K, V> Map<K, V> select(Map<K, V> map, Predicate<K> keyPredicate, Predicate<V> valuePredicate) {
        Map<K, V> selection = null;

        if (map != null) {
            selection = new HashMap<K, V>(map.size());

            Predicate<K> keyCheck = CollectionUtilities.getDefaultIfEmpty(keyPredicate);
            Predicate<V> valueCheck = CollectionUtilities.getDefaultIfEmpty(valuePredicate);
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (keyCheck.evaluate(entry.getKey()) && valueCheck.evaluate(entry.getValue())) {
                    selection.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return selection;
    }


    /**
     * Transform.
     *
     * @param map       the map
     * @param keyType   the key type
     * @param valueType the value type
     * @return the map< k, v>
     */
    public static <K, V, O> Map<K, V> transform(Map<O, O> map, Class<K> keyType, Class<V> valueType) {
        return transform(map, new SimpleObjectTransformer<O, K>(keyType), new SimpleObjectTransformer<O, V>(valueType));
    }

    /**
     * Transform.
     *
     * @param map              the map
     * @param keyTransformer   the key transformer
     * @param valueTransformer the value transformer
     * @return the map< k, v>
     */
    public static <K, V, J, W> Map<K, V> transform(Map<J, W> map, Transformer<J, K> keyTransformer, Transformer<W, V> valueTransformer) {
        return transform(map, new LinkedHashMap<K, V>(), keyTransformer, valueTransformer);
    }

    /**
     * Transform.
     *
     * @param source           the source
     * @param target           the target
     * @param keyTransformer   the key transformer
     * @param valueTransformer the value transformer
     * @return the map< k, v>
     */
    @SuppressWarnings({"unchecked"})
    public static <K, V, J, W> Map<K, V> transform(Map<J, W> source, Map<K, V> target, Transformer<J, K> keyTransformer, Transformer<W, V> valueTransformer) {
        if (source != null && target != null) {
            for (Map.Entry<J, W> entry : source.entrySet()) {
                target.put(keyTransformer.transform(entry.getKey()), valueTransformer.transform(entry.getValue()));
            }
        }
        return target;
    }

    /**
     * Transforms the specified array to a Map using the specified transformer to create the keys.
     * The elements in the specified collection are the values of the map.
     *
     * @param array            Array of Objects referencing the array to be transformed to a map.
     * @param keyTransformer   Transformer referencing the transformer responsible for the creation of the map's keys.
     * @param valueTransformer Transformer for transforming the value
     * @return Map referencing the resulting map.
     */
    public static <K, V, M> Map<K, M> toMap(V[] array, Transformer<V, K> keyTransformer, Transformer<V, M> valueTransformer) {
        return toMap(Arrays.asList(array), keyTransformer, valueTransformer);
    }

    /**
     * Transforms the specified array to a Map using the specified transformer to create the keys.
     * The elements in the specified collection are the values of the map.
     *
     * @param array          Array of Objects referencing the array to be transformed to a map.
     * @param keyTransformer Transformer referencing the transformer responsible for the creation of the map's keys.
     * @return Map referencing the resulting map.
     */
    public static <K, V> Map<K, V> toMap(V[] array, Transformer<V, K> keyTransformer) {
        return toMap(Arrays.asList(array), keyTransformer);
    }

    public static <K, V, M> Map<K, M> toMap(Collection<V> collection, Transformer<V, K> keyTransformer, Transformer<V, M> valueTransformer) {
        return toMap(collection, new LinkedHashMap<K, M>(), keyTransformer, valueTransformer);
    }

    /**
     * Transforms the specified collection to a Map using the specified transformer to create the keys.
     * The elements in the specified collection are the values of the map.
     *
     * @param collection     Collection of Objects referencing the collection to be transformed to a map.
     * @param keyTransformer Transformer referencing the transformer responsible for the creation of the map's keys.
     * @return Map referencing the resulting map.
     */
    public static <K, V> Map<K, V> toMap(Collection<V> collection, Transformer<? super V, K> keyTransformer) {
        Map<K, V> map = new LinkedHashMap<K, V>();

        if (collection != null) {
            for (V element : collection) {
                map.put(keyTransformer.transform(element), element);
            }
        }

        return map;
    }

    /**
     * Converts a collection of Mappable Objects into a map. The key of the map is the value returned from te getMapKey method of the Mappable interface.
     *
     * @param collection The collection to convert
     * @param <K>        The type of the key
     * @return The converted map
     */
    public static <K extends Comparable, V extends Mappable> Map<K, V> toMap(Collection<V> collection) {
        Map<K, V> map;
        if (collection != null) {
            map = toMap(collection, new MappableTransformer<K, V>());
            Assert.isTrue(collection.size() == map.size(), "Tried to convert a collection of Mappable objects to a map, however the resulting map's size is not equal to the original collection.");
        } else {
            map = new LinkedHashMap<K, V>();
        }
        return map;
    }

    /**
     * Transforms the specified collection to a Map using the specified transformer to create the keys.
     * The second transformer transforms the values.
     *
     * @param collection       Collection of Objects referencing the collection to be transformed to a map.
     * @param target           The target map
     * @param keyTransformer   Transformer referencing the transformer responsible for the creation of the map's keys.
     * @param valueTransformer Transformer for transforming the value
     * @return Map referencing the resulting map.
     */
    public static <K, V, M> Map<K, M> toMap(Collection<V> collection, Map<K, M> target, Transformer<V, K> keyTransformer, Transformer<V, M> valueTransformer) {
        Map<K, M> newTarget = target;

        if (newTarget == null) {
            newTarget = new LinkedHashMap<K, M>();
        }

        if (collection != null) {
            for (V element : collection) {
                newTarget.put(keyTransformer.transform(element), valueTransformer.transform(element));
            }
        }

        return newTarget;
    }

    /**
     * Filters the map using the given key predicate.
     *
     * @param map          The map to filter
     * @param keyPredicate The predicate to use as key filter
     * @return The filtered map
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, Predicate<K> keyPredicate) {
        Map<K, V> filtered = new HashMap<K, V>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (keyPredicate.evaluate(entry.getKey())) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }

    public static <K extends Comparable, V extends Mappable> Collection<K> toMapKeyCollection(Collection<V> collection) {
       Collection<K> keyCollection = new ArrayList<K>();

                if (collection != null) {
                    for (V element : collection) {
                        keyCollection.add((K) element.getMapKey());
                    }
                }

                return keyCollection;
    }

    /**
     * Transformer implementation to extract the key value for mappable objects.
     *
     * @param <K>   Comparable implementation for the key.
     * @param <V>   Mappable implementation for the value.
     */
    private static final class MappableTransformer<K extends Comparable, V extends Mappable> implements Transformer<V, K> {
        /**
         * Constructor.
         */
        MappableTransformer() {
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings({"unchecked"})
        public K transform(V object) {
            return (K) object.getMapKey();
        }
    }
}