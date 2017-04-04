package be.ictdynamic.dynarouteservice.common.lang;

import org.joda.time.*;

import java.sql.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class DateRange.
 *
 * @author Yves Cieters
 * @since 20/04/2015
 */
public class DateRange implements Iterable<Date> {

    private Date fromDate;
    private Date toDate;

    public DateRange(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Interval getInterval() {
        return new Interval(new DateTime(getFromDate()), new DateTime(getToDate()));
    }

    public int getNumberOfDays() {
        return Days.daysIn(getInterval()).plus(1).getDays();
    }

    public int getNumberOfWorkingDays() {
        int numberOfWorkingDays = 0;
        for (Date date : this) {
            // iterate over the days in the requested calculation period
            LocalDate localDate = new LocalDate(date);
            if (localDate.getDayOfWeek() <= DateTimeConstants.FRIDAY && !DateUtilities.PUBLIC_HOLIDAYS.contains(date)) {
                // add date to number of working days if the date is not in the weekend and the date is not a public holiday
                numberOfWorkingDays++;
            }
        }
        return numberOfWorkingDays;
    }

    @Override
    public Iterator<Date> iterator() {
        return new DateRangeIterator(getFromDate(), getToDate());
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    private static final class DateRangeIterator implements Iterator<Date> {
        private Date current;
        private final Date end;

        private DateRangeIterator(Date start,Date end) {
            this.current = start;
            this.end = end;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Date next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Date ret = current;
            current = DateUtilities.daysFromDate(1, current);
            if (current.compareTo(end) > 0) {
                current = null;
            }
            return ret;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
