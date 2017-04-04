package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Serializable and KeyValue interfaces implementation.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 4-mrt-2009
 */
public class SerializableKeyValue implements Serializable {
    private static final long serialVersionUID = 3241552803825449518L;
    /**
     * String referencing the key for the entry, may be null.
     */
    private String key;
    /**
     * String referencing the value for the entry, may be null.
     */
    private String value;

    /**
     * Constructor.
     */
    public SerializableKeyValue() {
    }

    /**
     * Constructor.
     *
     * @param key   String referencing the key for the entry, may be null.
     * @param value String referencing the value for the entry, may be null.
     */
    public SerializableKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key for the entry, may be null.
     *
     * @return String referencing the key for the entry, may be null.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key for the entry, may be null.
     *
     * @param key String referencing the key for the entry, may be null.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the value for the entry, may be null.
     *
     * @return String referencing the value for the entry, may be null.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value for the entry, may be null.
     *
     * @param value String referencing the value for the entry, may be null.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
