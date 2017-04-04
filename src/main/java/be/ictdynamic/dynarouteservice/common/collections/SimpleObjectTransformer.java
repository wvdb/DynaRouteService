package be.ictdynamic.dynarouteservice.common.collections;

import be.ictdynamic.common.beanutils.ConvertUtilities;
import org.apache.commons.collections15.Transformer;

/**
 * Class SimpleObjectTransformer.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <I> Input
 * @param <T> Output
 * @since 6-jul-2007 - 9:00:57
 */
public class SimpleObjectTransformer<I, T> implements Transformer<I, T> {

    /**
     * The type.
     */
    private final Class<T> type;

    /**
     * Instantiates a new simple object transformer.
     *
     * @param type the type
     */
    public SimpleObjectTransformer(Class<T> type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    public T transform(Object value) {
        return ConvertUtilities.parse(ConvertUtilities.toString(value), type);
    }
}