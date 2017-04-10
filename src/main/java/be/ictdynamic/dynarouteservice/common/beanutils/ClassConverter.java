package be.ictdynamic.dynarouteservice.common.beanutils;

/**
 * WrappingConverter implementation to convert a String into a Class implementation.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 14-nov-2007
 */
public class ClassConverter extends WrappingConverter<Class> {

    /**
     * Constructor.
     */
    public ClassConverter() {
        super(new org.apache.commons.beanutils.converters.ClassConverter());
    }

    /**
     * Converts the specified input object into a string representation.
     *
     * @param input Object implementation referencing the object to be converted into a string representation.
     * @return String referencing the string representation of the specified object.
     */
    @Override
    protected String toString(Class input) {
        return input.getName();
    }
}