package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.Closure;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * Closure implementation that appends a toString representation of each element in a Collection.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <T>   Object implementation referencing the object to get the toString representation for.
 * @since 14-mei-2008
 */
public class ToStringClosure<T> implements Closure<T> {

    public static final int STRING_BUILDER_INITIAL_CAPACITY = 16;
    /**
     * StringBuilder referencing the appended toString representations.
     */
    private StringBuilder builder;

    /**
     * String referencing an optional toString pattern to apply to each element.
     */
    private String toStringPattern;

    /**
     * Constructor.
     */
    public ToStringClosure() {
        this(STRING_BUILDER_INITIAL_CAPACITY, null);
    }

    /**
     * Constructor.
     *
     * @param capacity int referencing the initial capacity of the internal StringBuilder.
     */
    public ToStringClosure(int capacity) {
        this(capacity, null);
    }

    /**
     * Constructor.
     *
     * @param toStringPattern String referencing an optional toString pattern to apply to each element.
     */
    public ToStringClosure(String toStringPattern) {
        this(STRING_BUILDER_INITIAL_CAPACITY, toStringPattern);
    }

    /**
     * Constructor.
     *
     * @param capacity        int referencing the initial capacity of the internal StringBuilder.
     * @param toStringPattern String referencing an optional toString pattern to apply to each element.
     */
    public ToStringClosure(int capacity, String toStringPattern) {
        this.setToString(new StringBuilder(capacity));
        this.setToStringPattern(toStringPattern);
    }

    /**
     * {@inheritDoc}
     */
    public void execute(T object) {
        if (StringUtils.isNotEmpty(this.getToStringPattern())) {
            this.getToString().append(this.applyPattern(object));
        } else {
            this.getToString().append(this.toString(object));
        }
    }

    /**
     * Returns the toString representation of the specified object, without a predefined format.
     *
     * @param object Object referencing the object to get the toString representation for.
     * @return String referencing the toString representation of the specified object, without a predefined format.
     */
    protected String toString(T object) {
        return object.toString();
    }

    /**
     * Returns the toString representation of the specified object, using a predefined format.
     *
     * @param object Object referencing the object to get the toString representation for.
     * @return String referencing the toString representation of the specified object, using a predefined format.
     */
    protected String applyPattern(T object) {
        return this.toString(object);
    }

    /**
     * Utility method to apply the preset toString pattern to the specified arguments.
     *
     * @param args Array of Objects referencing the arguments supplied to the preset toString pattern, may be null.
     * @return String referencing a toString representation based on the preset pattern and the specified arguments.
     */
    protected String applyPattern(Object... args) {
        return MessageFormat.format(this.getToStringPattern(), args);
    }

    /**
     * Returns the appended toString representations.
     *
     * @return StringBuilder referencing the appended toString representations.
     */
    private StringBuilder getToString() {
        return builder;
    }

    /**
     * Sets the appended toString representations.
     *
     * @param toString StringBuilder referencing the appended toString representations.
     */
    private void setToString(StringBuilder toString) {
        this.builder = toString;
    }

    /**
     * Returns an optional toString pattern to apply to each element.
     *
     * @return String referencing an optional toString pattern to apply to each element.
     */
    private String getToStringPattern() {
        return toStringPattern;
    }

    /**
     * Sets an optional toString pattern to apply to each element.
     *
     * @param toStringPattern String referencing an optional toString pattern to apply to each element.
     */
    private void setToStringPattern(String toStringPattern) {
        this.toStringPattern = toStringPattern;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#builder()
     */
    @Override
    public String toString() {
        return this.getToString().toString();
    }
}