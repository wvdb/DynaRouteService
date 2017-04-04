package be.ictdynamic.dynarouteservice.common.lang;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Represents the Leeftijd in years and months.
 *
 * @author Erik Verkelkeren, Erik Verkelkeren
 * @version $Revision$
 * @since 26-jan-2004
 */
public class Leeftijd {

    public static final int BUFFER_SIZE = 20;
    public static final int HASH_MULTIPLIER = 29;
    /**
     * The years.
     */
    private int years;

    /**
     * The months.
     */
    private int months;

    /**
     * Constructs a new instance of Leeftijd.
     *
     * @param years  The int value of years
     * @param months The  int value of months
     */
    public Leeftijd(int years, int months) {
        this.setYears(years);
        this.setMonths(months);
    }

    /**
     * Gets the value of years.
     *
     * @return The resulting int value
     */
    public int getYears() {
        return years;
    }

    /**
     * Gets the value of months.
     *
     * @return The resulting int value
     */
    public int getMonths() {
        return months;
    }

    /**
     * Sets the value of years.
     *
     * @param years The int value of years
     */
    private void setYears(int years) {
        this.years = years;
    }

    /**
     * Sets the value of months.
     *
     * @param months The int value of months
     */
    private void setMonths(int months) {
        this.months = months;
    }

    /**
     * Returns the Leeftijd as a String in format jj jaar, mm maand(en).
     *
     * @return String containing the leeftijd in format jj jaar, mm maand(en).
     */
    public String getLeeftijdAsString() {
        StringBuilder leeftijdContent = new StringBuilder(BUFFER_SIZE);

        leeftijdContent.append(getYears()).append(" jaar");
        if (this.getMonths() > 0) {
            leeftijdContent.append(", ").append(this.getMonths());
            if (this.getMonths() > 1) {
                leeftijdContent.append(" maanden");
            } else {
                leeftijdContent.append(" maand");
            }
        }

        return leeftijdContent.toString();
    }

    /**
     * Calculates the period (in years and months) between the date of birth and the date of the screening.
     *
     * @param birthDate     Date object representing the date of birth
     * @param birthYear     Integer object representing the year of birth that will be used when the birthDate is null
     * @param calculationDate Date object representing the date of screening
     * @return Leeftijd object containing the period (in years and months) between the date of birth and the given date
     */
    public static Leeftijd calculateLeeftijd(Date birthDate, Long birthYear, Date calculationDate) {
        DateTime calDateTime = null;
        Leeftijd leeftijd = null;

        if (calculationDate != null) {
            calDateTime = new DateTime(calculationDate);
        }

        if (birthDate != null) {
            DateTime birthDateTime = new DateTime(birthDate);
            GregorianCalendar calBirth = new GregorianCalendar();
            calBirth.setTime(birthDate);

            Period period = new Period(birthDateTime, calDateTime);

            leeftijd = new Leeftijd(period.getYears(), period.getMonths());
        } else if (birthYear != null && birthYear > 0) {
            if (calDateTime == null) {
                calDateTime = new DateTime();
            }
            leeftijd = new Leeftijd((int) (calDateTime.getYear() - birthYear), 0);
        }

        return leeftijd;
    }

    /**
     * Checks if this object is equal to the given object.
     *
     * @param o The Object value of o
     * @return The resulting boolean value
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Leeftijd leeftijd = (Leeftijd) o;

        return this.getMonths() == leeftijd.getMonths() && this.getYears() == leeftijd.getYears();
    }

    /**
     * Calculates the hashCode of this object.
     *
     * @return The resulting int value
     */
    public int hashCode() {
        int result;
        result = this.getYears();
        return HASH_MULTIPLIER * result + this.getMonths();
    }

    /**
     * Returns a string representation of the object used for logging and debugging purposes.
     *
     * @return String containing a string representation of the object.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}