package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.common.beanutils.ConvertUtilities;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Entity referencing a Belgian account number.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 20-okt-2008
 */
public class RekeningNummer implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 5360280474914547652L;

    public static final long DIVISOR_VALUE = 97L;
    public static final int NUMMER_DEEL_VAL1 = 3;
    public static final int NUMMER_DEEL_VAL2 = 7;
    public static final int LENGTH_CHECK_VAL = 2;

    /**
     * String referencing the first part of the number of the account.
     */
    private String nummerDeel1;

    /**
     * String referencing the second part of the number of the account.
     */
    private String nummerDeel2;

    /**
     * String referencing the check number to validate the account number.
     */
    private String check;

    /**
     * Constructor.
     */
    public RekeningNummer() {
        super();
    }

    /**
     * Constructor.
     *
     * @param rekeningnummer String referencing the String representation of the rekeningnummer to be set.
     */
    public RekeningNummer(String rekeningnummer) {
        this(RekeningNummer.parse(rekeningnummer));
    }

    /**
     * Constructor.
     *
     * @param nummerDeel1 String referencing the first part of the number of the account.
     * @param nummerDeel2 String referencing the second part of the number of the account.
     * @param check       String referencing the check number to validate the account number.
     */
    public RekeningNummer(String nummerDeel1, String nummerDeel2, String check) {
        this.check = check;
        this.nummerDeel1 = nummerDeel1;
        this.nummerDeel2 = nummerDeel2;
    }

    /**
     * Constructor.
     *
     * @param parts Array of Strings referencing the parts to compose the rekeningnummer.
     */
    private RekeningNummer(String... parts) {
        if (!ArrayUtils.isEmpty(parts)) {
            this.setNummerDeel1(parts[0]);
            if (parts.length > 1) {
                this.setNummerDeel2(parts[1]);
            }
            if (parts.length > 2) {
                this.setCheck(parts[2]);
            }
        }
    }

    /**
     * Parses the incoming rekeningnummer string representation into different rekeningnummer parts.
     *
     * @param rekeningnummer String referencing the string representation of the rekeningnummer to be parsed.
     * @return Array of Longs referencing the parsed rekeningnummer.
     */
    private static String[] parse(String rekeningnummer) {
        String[] parts = null;

        if (StringUtils.isNotEmpty(rekeningnummer)) {
            parts = StringUtils.split(rekeningnummer, '-');
        }

        return parts;
    }

    /**
     * Returns whether this rekeningnummer represents a valid one.
     *
     * @return boolean flag with value true if this rekeningnummer represents a valid one, false when not.
     */
    public boolean isValid() {
        boolean valid = false;

        if ((StringUtils.length(this.getNummerDeel1()) == NUMMER_DEEL_VAL1) && (StringUtils.length(this.getNummerDeel2()) == NUMMER_DEEL_VAL2) && (StringUtils.length(this.getCheck()) == LENGTH_CHECK_VAL)) {
            long number = 0L;
            if (StringUtils.isNotEmpty(this.getNummerDeel1()) && StringUtils.isNotEmpty(this.getNummerDeel2())) {
                number = ConvertUtilities.parseLong(this.getNummerDeel1() + this.getNummerDeel2());
            }
            long divisor = DIVISOR_VALUE;
            long checker = 0L;
            if (StringUtils.isNotEmpty(this.getCheck())) {
                checker = ConvertUtilities.parseLong(this.getCheck());
            }

            if (number != 0L) {
                long mod = number % divisor;

                if (mod == 0) {
                    valid = (checker == divisor);
                } else {
                    valid = (mod == checker);
                }
            }
        }

        return valid;
    }

    /**
     * Returns whether this rekeningnummer is emtpy or not (i.e. all his parts are empty strings).
     *
     * @return boolean flag with value true if this rekeningnummer is empty, false when not.
     */
    public boolean isEmpty() {
        return StringUtils.isEmpty(this.getNummerDeel1()) && StringUtils.isEmpty(this.getNummerDeel2()) && StringUtils.isEmpty(this.getCheck());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtils.join(Arrays.asList(this.getNummerDeel1(), this.getNummerDeel2(), this.getCheck()), '-');
    }

    public String getDecorated() {
        String decoratedRekeningnummer = "Niet ingevuld";
        if (StringUtils.isNotEmpty(this.getNummerDeel1())) {
            decoratedRekeningnummer = this.toString();
        }
        return decoratedRekeningnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RekeningNummer nummer = (RekeningNummer) o;

        return StringUtils.equals(this.getNummerDeel1(), nummer.getNummerDeel1()) && StringUtils.equals(this.getNummerDeel2(), nummer.getNummerDeel2()) && StringUtils.equals(this.getCheck(), nummer.getCheck());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;

        result = this.getNummerDeel1().hashCode();
        result = 29 * result + this.getNummerDeel2().hashCode();

        return 29 * result + this.getCheck().hashCode();
    }

    /**
     * Gets the nummer deel1.
     *
     * @return the nummer deel1
     */
    public String getNummerDeel1() {
        return nummerDeel1;
    }

    /**
     * Sets the nummer deel1.
     *
     * @param nummerDeel1 the new nummer deel1
     */
    public void setNummerDeel1(String nummerDeel1) {
        this.nummerDeel1 = nummerDeel1;
    }

    /**
     * Gets the nummer deel2.
     *
     * @return the nummer deel2
     */
    public String getNummerDeel2() {
        return nummerDeel2;
    }

    /**
     * Sets the nummer deel2.
     *
     * @param nummerDeel2 the new nummer deel2
     */
    public void setNummerDeel2(String nummerDeel2) {
        this.nummerDeel2 = nummerDeel2;
    }

    /**
     * Gets the check.
     *
     * @return the check
     */
    public String getCheck() {
        return check;
    }

    /**
     * Sets the check.
     *
     * @param check the new check
     */
    public void setCheck(String check) {
        this.check = check;
    }
}