package be.ictdynamic.dynarouteservice.common.lang;

import be.ictdynamic.dynarouteservice.common.beanutils.ConvertUtilities;
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
public class IBANNummer implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 5360280474914547652L;
    public static final int HASH_MULTIPLIER = 29;
    public static final long DIVISOR_VALUE = 97L;

    /**
     * String referencing the first part of the number of the account.
     */
    private String nummerDeel1;

    /**
     * String referencing the second part of the number of the account.
     */
    private String nummerDeel2;

     /**
     * String referencing the third part of the number of the account.
     */
    private String nummerDeel3;

     /**
     * String referencing the fourth part of the number of the account.
     */
    private String nummerDeel4;



    /**
     * Constructor.
     */
    public IBANNummer() {
        super();
    }

    /**
     * Constructor.
     *
     * @param rekeningnummer String referencing the String representation of the rekeningnummer to be set.
     */
    public IBANNummer(String rekeningnummer) {
        this(IBANNummer.parse(rekeningnummer));
    }

    /**
     * Constructor.
     *
     * @param nummerDeel1 String referencing the first part of the number of the account.
     * @param nummerDeel2 String referencing the second part of the number of the account.
     * @param nummerDeel3 String referencing the third part of the number of the account.
     * @param nummerDeel4 String referencing the fourth part of the number of the account.
     */
    public IBANNummer(String nummerDeel1, String nummerDeel2, String nummerDeel3, String nummerDeel4) {
        this.nummerDeel1 = nummerDeel1;
        this.nummerDeel2 = nummerDeel2;
        this.nummerDeel3 = nummerDeel3;
        this.nummerDeel4 = nummerDeel4;
    }

    /**
     * Constructor.
     *
     * @param parts Array of Strings referencing the parts to compose the rekeningnummer.
     */
    private IBANNummer(String... parts) {
        if (!ArrayUtils.isEmpty(parts)) {
            this.setNummerDeel1(parts[0]);
            if (parts.length > 1) {
                this.setNummerDeel2(parts[1]);
            }
            if (parts.length > 2) {
                this.setNummerDeel3(parts[2]);
            }
            if (parts.length > 3) {
                this.setNummerDeel4(parts[3]);
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
        String[] parts = new String[4];

        if (StringUtils.isNotEmpty(rekeningnummer)) {
            for (int i = 0; i <= 3; i++) {
                parts[i] = StringUtils.defaultIfEmpty(StringUtils.substring(rekeningnummer, i * 4, i * 4 + 4), null);
            }
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

        if (isFormatValid()) {

            // nummerdeel 2, 3 en 4
            String basisRekeningnummer = this.getNummerDeel2() + this.getNummerDeel3() + this.getNummerDeel4();
            // landcode BE (B = 11, E = 14)
            String landCodeNummer = "1114";
            // 3e en 4e cijfer van nummerdeel 1
            String controleGetal = StringUtils.substring(this.getNummerDeel1(), 2, 4);

            // basisRekeningnummer + landCodeNummer + controleGetal
            long teControlerenGetal = ConvertUtilities.parseLong(basisRekeningnummer + landCodeNummer + controleGetal);
            long divisor = DIVISOR_VALUE;

            // teControlerenGetal mod 97 moet 1 zijn
            valid = teControlerenGetal % divisor == 1;
        }

        return valid;
    }

    protected boolean isFormatValid() {
        boolean isFormatValid = StringUtils.length(this.getNummerDeel1()) == 4 && StringUtils.equals("BE", StringUtils.substring(this.getNummerDeel1(), 0, 2));
        isFormatValid = isFormatValid && (StringUtils.length(this.getNummerDeel2()) == 4) && (StringUtils.length(this.getNummerDeel3()) == 4);
        isFormatValid = isFormatValid && (StringUtils.length(this.getNummerDeel4()) == 4);
        return isFormatValid;
    }

    /**
     * Returns whether this rekeningnummer is emtpy or not (i.e. all his parts are empty strings).
     *
     * @return boolean flag with value true if this rekeningnummer is empty, false when not.
     */
    public boolean isEmpty() {
        return StringUtils.isEmpty(this.getNummerDeel1()) && StringUtils.isEmpty(this.getNummerDeel2()) && StringUtils.isEmpty(this.getNummerDeel3()) && StringUtils.isEmpty(this.getNummerDeel4());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getNummerDeel1() + this.getNummerDeel2() + this.getNummerDeel3() + this.getNummerDeel4();
    }

    public String getDecorated() {
        String decoratedRekeningnummer = "Niet ingevuld";
        if (StringUtils.isNotEmpty(this.getNummerDeel1())) {
            decoratedRekeningnummer = StringUtils.join(Arrays.asList(this.getNummerDeel1(), this.getNummerDeel2(), this.getNummerDeel3(), this.getNummerDeel4()), ' ');
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

        final IBANNummer nummer = (IBANNummer) o;

        return StringUtils.equals(this.getNummerDeel1(), nummer.getNummerDeel1()) && StringUtils.equals(this.getNummerDeel2(), nummer.getNummerDeel2()) && StringUtils.equals(this.getNummerDeel3(), nummer.getNummerDeel3()) && StringUtils.equals(this.getNummerDeel4(), nummer.getNummerDeel4());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;

        result = this.getNummerDeel1().hashCode();
        result = HASH_MULTIPLIER * result + this.getNummerDeel2().hashCode();
        result = HASH_MULTIPLIER * result + this.getNummerDeel3().hashCode();

        return HASH_MULTIPLIER * result + this.getNummerDeel4().hashCode();
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
     * Gets the nummer deel3.
     *
     * @return the nummer deel3
     */
    public String getNummerDeel3() {
        return nummerDeel3;
    }

    /**
     * Sets the nummer deel3.
     *
     * @param nummerDeel3 the new nummer deel3
     */
    public void setNummerDeel3(String nummerDeel3) {
        this.nummerDeel3 = nummerDeel3;
    }

    /**
     * Gets the nummer deel4.
     *
     * @return the nummer deel4
     */
    public String getNummerDeel4() {
        return nummerDeel4;
    }

    /**
     * Sets the nummer deel4.
     *
     * @param nummerDeel4 the new nummer deel4
     */
    public void setNummerDeel4(String nummerDeel4) {
        this.nummerDeel4 = nummerDeel4;
    }
}