package com.scilla.util;

import android.util.Log;

/*
 * 
 * Just use the setprop using adb shell to change the LOG LEVEL.
 * log.tag.<TAG> <LEVEL>
 *
 * Example:
 * 
 * adb shell
 * # setprop log.tag.* DEBUG
 * 
 */

public class SLog {

	public static void debug(final Class<?> myClass, final String msg) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.d(tag,  msg);
	}

	public static void info(final Class<?> myClass, final String msg) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.i(tag,  msg);
	}

	public static void warn(final Class<?> myClass, final String msg) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.w(tag,  msg);
	}

	public static void error(final Class<?> myClass, final String msg) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.e(tag,  msg);
	}

	public static void debug(final Class<?> myClass, final String msg,
			final Exception e) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.d(tag, "message :" + msg + ": exception :" + e, e);
	}

	public static void info(final Class<?> myClass, final String msg,
			final Exception e) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.i(tag, "message :" + msg , e);
	}

	public static void warn(final Class<?> myClass, final String msg,
			final Exception e) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.w(tag, "message :" + msg, e);
	}

	public static void error(final Class<?> myClass, final String msg,
			final Exception e) {
		final String tag = tagTruncate(myClass.getSimpleName());
		Log.e(tag, "message :" + msg , e);
	}

	/*
	 * Android's Tag allows only 23 chars long. We need to truncate to less than
	 * 23 chars to keep Android's Log utility happy.
	 */
	public static String tagTruncate(String str) {
		if (str.length() > 22) {
			String trun = str.substring(0, 21);
			return trun;
		} else {
			return str;
		}
	}
}
