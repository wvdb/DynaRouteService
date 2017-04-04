package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang3.Validate;

/**
 * Transformer implementation that casts the incoming object to the specified type.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <I> From this type
 * @param <T> To this type
 * @since 15-nov-2007
 */
public class CastObjectTransformer<I, T extends I> implements Transformer<I, T> {

    /**
     * Class referencing the type to castElements the incoming object to.
     */
    private Class<T> type;

    /**
     * Constructor.
     *
     * @param type Class referencing the type to castElements the incoming object to.
     */
    public CastObjectTransformer(Class<T> type) {
        this.setType(type);
    }

    /**
     * Transforms the input object (leaving it unchanged) into some output object.
     * <p/>
     * This particular implementation extracts the description from the specified Categorie object.
     *
     * @param object Object referencing the input object to be transformed (but is left unchanged).
     * @return Object referencing the transformed object, i.e. the description of the incoming Categorie.
     */
    public T transform(I object) {
        return this.getType().cast(object);
    }

    /**
     * Returns the type to castElements the incoming object to.
     *
     * @return Class referencing the type to castElements the incoming object to.
     */
    private Class<T> getType() {
        return type;
    }

    /**
     * Sets the type to castElements the incoming object to.
     *
     * @param type Class referencing the type to castElements the incoming object to.
     */
    private void setType(Class<T> type) {
        Validate.notNull(type, "The specified type set for CastObjectTransformer is required.");
        this.type = type;
    }
}