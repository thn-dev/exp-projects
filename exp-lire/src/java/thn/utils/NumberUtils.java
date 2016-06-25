package thn.utils;

/**
 * Provides number related utility methods
 */
public class NumberUtils {
	/**
	 * Attempt to convert an {@link Object} to an {@link Integer}
	 *
	 * @param value The value to be converted
	 * @return the {@link Integer} value
	 */
	public static Integer toInteger(final Object value) {
		Integer intValue = null;

		if (value instanceof Integer) {
			intValue = (Integer) value;
		}

		if (value instanceof Double) {
			intValue = toLong(value).intValue();
		}

		if (value instanceof Long) {
			intValue = ((Long) value).intValue();
		}

		if (value instanceof String) {
			intValue = Integer.parseInt((String) value);
		}

		return intValue;
	}

	/**
	 * Attempt to convert an {@link Object} to a {@link Long}
	 *
	 * @param value The value to be converted
	 * @return the {@link Long} value
	 */
	public static Long toLong(final Object value) {
		Long longValue = null;

		if (value instanceof Long) {
			longValue = (Long) value;
		}

		if (value instanceof Integer) {
			longValue = ((Integer) value).longValue();
		}

		if (value instanceof Double) {
			longValue = Math.round((Double) value);
		}

		if (value instanceof String) {
			longValue = Long.parseLong((String) value);
		}

		return longValue;
	}

	/**
	 * Attempt to convert an {@link Object} to a {@link Double}
	 *
	 * @param value The value to be converted
	 * @return the {@link Double} value
	 */
	public static Double toDouble(final Object value) {
		Double doubleValue = null;

		if (value instanceof Double) {
			doubleValue = (Double) value;
		}

		if (value instanceof Integer) {
			doubleValue = ((Integer) value).doubleValue();
		}

		if (value instanceof Long) {
			doubleValue = ((Long) value).doubleValue();
		}

		if (value instanceof String) {
			doubleValue = Double.parseDouble((String) value);
		}

		return doubleValue;
	}
}
