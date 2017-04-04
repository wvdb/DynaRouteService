package be.ictdynamic.dynarouteservice.common.collections;

import org.apache.commons.collections15.iterators.ObjectArrayIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.owasp.esapi.Randomizer;
import org.owasp.esapi.reference.DefaultRandomizer;

/**
 * ObjectArrayListIterator implementation that iterates the source array randomly.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @param <E>   Object implementation referencing the element type of the array to iterate.
 * @since 19-jul-2010
 */
public class RandomizedArrayIterator<E> extends ObjectArrayIterator<E> {
    private static final Randomizer RANDOMIZER = DefaultRandomizer.getInstance();

    private ObjectArrayIterator<Integer> indices;

    /**
     * Constructor.
     *
     * @param array       Array of Object implementations referencing the array to be iterated.
     * @param randomCount boolean flag with value true to allow a random number of iterations, false when all elements of the array must be iterated randomly.
     */
    public RandomizedArrayIterator(E[] array, boolean randomCount) {
        super(array);

        int count = (randomCount ? RANDOMIZER.getRandomInteger(1, array.length + 1) : array.length);
        Integer[] indexArray = new Integer[count];
        int amountGenerated = 0;

        while (amountGenerated < indexArray.length) {
            int next = RANDOMIZER.getRandomInteger(0, array.length);

            if (!ArrayUtils.contains(indexArray, next)) {
                indexArray[amountGenerated++] = next;
            }
        }
        this.indices = new ObjectArrayIterator<Integer>(indexArray);
    }

    @Override
    public boolean hasNext() {
        return this.getIndices().hasNext();
    }

    @Override
    public E next() {
        return this.getArray()[this.getIndices().next()];
    }

    @Override
    public void reset() {
        this.getIndices().reset();
    }

    private ObjectArrayIterator<Integer> getIndices() {
        return indices;
    }

}
