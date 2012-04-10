package com.scilla.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Displays an EULA ("End User License Agreement") that the user has to accept
 * before using the application. Your application should call
 * {@link Eula#show(android.app.Activity)} in the onCreate() method of the first
 * activity. If the user accepts the EULA, it will never be shown again. If the
 * user refuses, {@link android.app.Activity#finish()} is invoked on your
 * activity.
 */
public class Eula {

	/**
	 * callback to let the activity know when the user has accepted the EULA.
	 */
	public static interface OnEulaAgreedTo {

		/**
		 * Called when the user has accepted the eula and the dialog closes.
		 */
		void onEulaAgreedTo();
	}

	/**
	 * Displays the EULA if necessary. This method should be called from the
	 * onCreate() method of your main Activity.
	 * 
	 * @param activity
	 *            The Activity to finish if the user rejects the EULA.\
	 * @param preferences
	 *            appEulaPreference object
	 * @param eula_filename
	 *            eula file name
	 * @param eulaTitleMsg
	 *            dialogTitleMsg
	 * @param acceptButtonText
	 *            dialog accept button text
	 * @param cancelButtonText
	 *            dialog cancel button text
	 * @return Whether the user has agreed already.
	 */
	public static boolean show(final Activity activity, String eulaTitleMsg,
			String acceptButtonText, String cancelButtonText,
			final AppPreferences preferences, String eula_filename) {
		if (!preferences.getEulaAccepted()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					activity);
			builder.setTitle(eulaTitleMsg);
			builder.setCancelable(true);
			builder.setPositiveButton(acceptButtonText,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							preferences.setEulaAccepted(true);
							SLog.debug(this.getClass(), "EULA accepted");
							if (activity instanceof OnEulaAgreedTo) {
								((OnEulaAgreedTo) activity).onEulaAgreedTo();
							}
						}
					});
			builder.setNegativeButton(cancelButtonText,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							preferences.setEulaAccepted(false);
							SLog.debug(this.getClass(), "EULA declined");
							refuse(activity, dialog);
						}
					});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					SLog.debug(this.getClass(), "EULA cancelled");
					refuse(activity, dialog);
				}
			});
			builder.setMessage(readEula(activity, eula_filename));
			builder.create().show();
			return false;
		}
		return true;
	}

	private static void refuse(Activity activity, DialogInterface dialog) {
		SLog.debug(activity.getClass(), "Refusing Activity by EULA");
		dialog.dismiss();
		activity.finish();
	}

	private static CharSequence readEula(Activity activity, String filename) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(activity.getAssets()
					.open(filename)), 8192);
			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = in.readLine()) != null)
				buffer.append(line).append('\n');
			return buffer;
		} catch (IOException e) {
			return "";
		} finally {
			closeStream(in);
		}
	}

	/**
	 * Closes the specified stream.
	 * 
	 * @param stream
	 *            The stream to close.
	 */
	private static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// Ignore
			}
		}
	}
}
