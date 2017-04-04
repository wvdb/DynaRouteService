package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.common.collections.CollectionUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class containing all capital letters of the alphabet.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 15-okt-2007
 */
public class Alphabet implements Iterable<String> {

    public static final int INITIAL_CAPACITY = 30;
    public static final int CAPACITY_MARGIN = 5;
    public static final int INDEX_OF_FIRST_ELEMENT = 0;
    /**
     * List of String referencing the letters this alphabet contains.
     */
    private final List<String> letters = new ArrayList<String>(INITIAL_CAPACITY);

    /**
     * Constructor.
     */
    public Alphabet() {
        for (int i = 'A'; i <= 'Z'; i++) {
            this.getLetters().add(Character.toString((char) i));
        }
    }

    /**
     * Returns the letters this alphabet contains.
     *
     * @return List of String referencing the letters this alphabet contains.
     */
    private List<String> getLetters() {
        return letters;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<String> iterator() {
        return this.getLetters().iterator();
    }

    /**
     * Prepends (i.e. sets first in the list) the specified letter.
     *
     * @param letter String referencing the letter to be prepended.
     */
    public void prepend(String letter) {
        this.getLetters().add(INDEX_OF_FIRST_ELEMENT, letter);
    }

    /**
     * Appends (i.e. sets last in the list) the specified letter.
     *
     * @param letter String referencing the letter to be appended.
     */
    public void append(String letter) {
        this.getLetters().add(letter);
    }

    /**
     * Inserts the specified letter at the specified index.
     *
     * @param letter String referencing the letter to be inserted.
     * @param index  int referencing the index to insert the letter into.
     */
    public void insert(String letter, int index) {
        this.getLetters().add(index, letter);
    }

    /**
     * Returns the size (the amount of letters) of this alphabet.
     *
     * @return int referencing the size (the amount of letters) of this alphabet.
     */
    public int size() {
        return CollectionUtilities.size(this.getLetters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.getLetters().size() + CAPACITY_MARGIN);
        for (String letter : this.getLetters()) {
            builder.append(letter);
        }
        return builder.toString();
    }
}