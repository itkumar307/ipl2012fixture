package com.scilla.ipl;

import android.app.Activity;
import android.content.Context;

import com.scilla.util.AppPreferences;

public class ScillaPreference extends AppPreferences {

	private static final String COMPANY_NAME = "companyName";

	public ScillaPreference(Context context) {
		super(context, context.getString(R.string.SHAREDPREF_NAME));
		this.context = context.getApplicationContext();
		this.appSharedPrefs = context.getSharedPreferences(context.getString(R.string.SHAREDPREF_NAME), Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	// company Name
	public String getCompanyName() {
		return appSharedPrefs.getString(COMPANY_NAME, context.getString(R.string.APP_NAME));
	}

	public void setCompanyName(String comapanyName) {
		prefsEditor.putString(COMPANY_NAME, comapanyName);
		prefsEditor.commit();
	}
}