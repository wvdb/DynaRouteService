package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.Assert;

/**
 * BaseConverter implementation wrapping another Converter to decorate it with null and empty checks.
 * <p/>
 * It also provides a default implementation of the toString functionality.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <T> The type to be converted
 * @since 26-apr-2007
 */
public class WrappingConverter<T> extends BaseConverter<T> {

    /**
     * Converter implementation referencing the converter being decorated by this class.
     */
    private Converter converter;

    /**
     * Constructor.
     *
     * @param converter Converter implementation referencing the converter being decorated by this class.
     */
    WrappingConverter(Converter converter) {
        this.setConverter(converter);
    }

    /**
     * Parses the specified input string into an object of the specified data type.
     *
     * @param type  Class referencing the data type the specified input string must be parsed into.
     * @param input String referencing the input string to be parsed the specified data type.
     * @return Object referencing the string parse result in the specified data type, null when input is null or when an exception is thrown.
     */
    @SuppressWarnings({"unchecked"})
    protected T parse(Class<T> type, String input) {
        return (T) this.getConverter().convert(type, input);
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    protected String toString(T input) {
        return ObjectUtils.toString(input);
    }

    /**
     * Returns the converter being decorated by this class.
     *
     * @return Converter implementation referencing the converter being decorated by this class.
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * Sets the converter being decorated by this class.
     *
     * @param converter Converter implementation referencing the converter being decorated by this class.
     */
    private void setConverter(Converter converter) {
        Assert.notNull(converter, "The converter being decorated by this class, cannot be null.");
        this.converter = converter;
    }
}