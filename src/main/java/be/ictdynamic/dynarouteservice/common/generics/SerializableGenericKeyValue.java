package be.ictdynamic.dynarouteservice.common.generics;

import org.apache.commons.collections15.keyvalue.DefaultKeyValue;

import java.io.Serializable;
import java.util.Map;

/**
 * Class SerializableGenericKeyValue.
 *
 * @author Yves Cieters
 * @since 10/09/2014
 */
public class SerializableGenericKeyValue<K, V> extends DefaultKeyValue<K, V> implements Serializable {
    private static final long serialVersionUID = -1616654569392077743L;

    public SerializableGenericKeyValue() {
        super();
    }

    public SerializableGenericKeyValue(K key, V value) {
        super(key, value);
    }

    public SerializableGenericKeyValue(SerializableGenericKeyValue<K, V> pair) {
        super(pair);
    }

    public SerializableGenericKeyValue(Map.Entry<K, V> entry) {
        super(entry);
    }
}
