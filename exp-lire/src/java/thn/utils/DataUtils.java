package thn.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

public class DataUtils {

	// counter section
	private static long count;

	public static void resetCount() {
		count = 0L;
	}

	public static void incrementCount() {
		count++;
	}

	public static void addCount(long value) {
		count = count + value;
	}

	public static String getCount() {
		return (String.format("%,d", count));
	}

	// timer section
	private static long startTime;
	private static long endTime;

	public static void resetTime() {
		startTime = 0L;
		endTime = 0L;
	}

	public static void startTime() {
		startTime = System.currentTimeMillis();
	}

	public static void endTime() {
		endTime = System.currentTimeMillis();
	}

	public static long getTimeDiff() {
		return (endTime - startTime);
	}

	public static String getTime(final TimeUnit unit, final String displayUnit) {
		String timeInfo = null;

		switch(unit) {
			case MILLISECONDS:
				timeInfo = String.format("%,d %s", getTimeDiff(), displayUnit);
				break;

			default:
				timeInfo = String.format("%.,4f %s", convertTime(getTimeDiff(), unit), displayUnit);
				break;

		}
		return timeInfo;
	}

	public static double convertTime(final long timeInMillis, final TimeUnit unit) {
		double timeValue = 0.0;

		switch(unit) {
			case SECONDS:
				timeValue = (double)timeInMillis/DateUtils.MILLIS_PER_SECOND;
				break;

			case MINUTES:
				timeValue = (double)timeInMillis/DateUtils.MILLIS_PER_MINUTE;
				break;

			case HOURS:
				timeValue = (double)timeInMillis/DateUtils.MILLIS_PER_HOUR;
				break;

			case DAYS:
				timeValue = (double)timeInMillis/DateUtils.MILLIS_PER_DAY;
				break;

			case MILLISECONDS:
			default:
				timeValue = timeInMillis;
				break;
		}
		return timeValue;
	}

	// byte counter section
	private static long byteCount;

	public static void addByteCount(final long value) {
		byteCount = byteCount + value;
	}

	public static void resetByteCount() {
		byteCount = 0L;
	}

	public static String getDisplayByteCount() {
		return org.apache.commons.io.FileUtils.byteCountToDisplaySize(byteCount);
	}

	public static double toKB(final long bytes) {
		return ((double) bytes/org.apache.commons.io.FileUtils.ONE_KB);
	}

	public static double toMB(final long bytes) {
		return ((double) bytes/org.apache.commons.io.FileUtils.ONE_MB);
	}

	public static double toGB(final long bytes) {
		return ((double) bytes/org.apache.commons.io.FileUtils.ONE_GB);
	}

	public static double toTB(final long bytes) {
		return ((double) bytes/org.apache.commons.io.FileUtils.ONE_TB);
	}
}