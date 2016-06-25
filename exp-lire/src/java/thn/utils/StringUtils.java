package thn.utils;

/**
 * Provides string related utility methods
 */
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

public class StringUtils {
	private static final FastDateFormat BY_DAY = FastDateFormat.getInstance("yyyyMMdd");
	private static final FastDateFormat BY_YEAR = FastDateFormat.getInstance("yyyy");
	private static final FastDateFormat BY_MONTH = FastDateFormat.getInstance("yyyyMM");

	public static final String DASH_SEPARATOR = "-";

	public static String appendByYear(final String prefix) {
		return appendByDateFormat(prefix, DASH_SEPARATOR, BY_YEAR);
	}

	/**
	 * Append YYYY to the given string separated by the given separator
	 *
	 * @param prefix The prefix to be appended with YYYY string
	 * @param separator The separator that separates the prefix and YYYY string
	 * @return The appended string
	 */
	public static String appendByYear(final String prefix, final String separator) {
		return appendByDateFormat(prefix, separator, BY_YEAR);
	}

	public static String appendByMonth(final String prefix) {
		return appendByDateFormat(prefix, DASH_SEPARATOR, BY_MONTH);
	}

	/**
	 * Append YYYYMM to the given string separated by the given separator
	 *
	 * @param prefix The prefix to be appended with YYYYMM string
	 * @param separator The separator that separates the prefix and YYYYMM string
	 * @return The appended string
	 */
	public static String appendByMonth(final String prefix, final String separator) {
		return appendByDateFormat(prefix, separator, BY_MONTH);
	}

	/**
	 * Append YYYYMMDD to the given string separated by the given separator
	 *
	 * @param prefix The prefix to be appended with YYYYMMDD string
	 * @param separator The separator that separates the prefix and YYYYMMDD string
	 * @return The appended string
	 */
	public static String appendByDay(final String prefix, final String separator) {
		return appendByDateFormat(prefix, separator, BY_DAY);
	}

	/**
	 * Append a date value to the given string separated by the given separator
	 *
	 * @param prefix The prefix to be appended with a date value
	 * @param separator The separator that separates the prefix and the date value
	 * @param dateFormat The {@link FastDateFormat} instance to format a date value to a pre-defined format
	 * @return The appended string
	 */
	public static String appendByDateFormat(final String prefix, final String separator, final FastDateFormat dateFormat) {
		return append(prefix, separator, dateFormat.format(new Date()));
	}

	/**
	 * Append info to the given string separated by the given separator
	 *
	 * @param prefix The prefix to be appended with a date value
	 * @param separator The separator that separates the prefix and the info
	 * @param info The string info to be appended to the prefix
	 * @return The appended string
	 */
	public static String append(final String prefix, final String separator, final String info) {
		final String valueSeparator = (separator != null) ? separator : DASH_SEPARATOR;

		final String value = new StringBuilder(prefix)
				.append(valueSeparator).append(info)
				.toString();

		return value;
	}
}
