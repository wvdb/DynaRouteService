package be.ictdynamic.dynarouteservice.common.lang;

/**
 * Static class containing constants and methods for date formatting.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 25 -mrt-2008
 */
public final class DateFormatUtilities {

	/**
	 * String constant defining the pattern to display a date entity to the day.
	 */
	public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";

	/**
	 * String constant defining the pattern to display a date time entity to the minute.
	 */
	public static final String DATE_TIME_FORMAT_PATTERN = "dd/MM/yyyy HH:mm";

	/**
	 * String constant defining the pattern to display a date time entity to the milli-second.
	 */
	public static final String DATE_TIME_MILLIS_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss,SSS";
	/**
	 * The constant DATE_TIME_SEC_FORMAT_PATTERN.
	 */
	public static final String DATE_TIME_SEC_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss";

	/**
	 * String constant defining the pattern to display a time entity to the minute.
	 */
	public static final String TIME_FORMAT_PATTERN = "HH:mm";

	/**
	 * String constant defining the pattern to display a time entity to the millisecond.
	 */
	public static final String TIME_MILLIS_FORMAT_PATTERN = "HH:mm:ss,SSS";

	/**
	 * String constant defining the default value of a time entity to the minute.
	 */
	public static final String TIME_DEFAULT_VALUE = "00:00";

	/**
	 * String constant defining the pattern to display dates in filenames.
	 */
	public static final String DATE_REVERSED_TIME_FORMAT_PATTERN_FILENAME = "yyyy-MM-dd HH'h'mm";

	/**
	 * The constant DATE_FORMAT_PATTERN_FILENAME.
	 */
	public static final String DATE_FORMAT_PATTERN_FILENAME = "dd-MM-yyyy";
	/**
	 * String constant defining the pattern to display dates in reverse format, e.g. for sorting.
	 */
	public static final String DATE_REVERSED_FORMAT_PATTERN_FILENAME = "yyyyMMdd";

	/**
	 * String constant defining the pattern to be able to sort a date using strings.
	 */
	public static final String DATE_SORTSTRING_FORMAT_PATTERN = "yyyyMMdd";

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private DateFormatUtilities() {
    }
}