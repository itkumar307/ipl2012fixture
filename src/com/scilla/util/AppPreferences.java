package com.scilla.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
	private static final String PREFERENCE_EULA_ACCEPTED = "eula.accepted";
	private static final String PREFERENCE_EULA_VERSION = "eula_version";

	private static final String DATA_USAGE = "dataUsage";
	private static final String INTERNET_USAGE = "internetUsage";

	protected SharedPreferences appSharedPrefs;
	protected Editor prefsEditor;
	protected Context context;

	public enum ConnType {
		APP_WIFI(0), APP_DATA(1);

		private final int mConnType;

		ConnType(int connType) {
			mConnType = connType;
		}

		public int getConnType() {
			return mConnType;
		}
		
		public static ConnType getConnType(int connType) {
	          for(ConnType s : ConnType.values()){
	        	 if(s.getConnType() == connType){
	        		 return s;
	        	 }
	          }
	          throw new RuntimeException("Invalid value for connType.");
		}
	}

	public AppPreferences(Context context, String prefsString) {
		this.context = context.getApplicationContext();
		String AppPrefsString = getAppPrefsString(prefsString);
		SLog.debug(this.getClass(), "Shared Preferences String: "
				+ AppPrefsString);
		this.appSharedPrefs = context.getSharedPreferences(AppPrefsString,
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	public boolean getEulaAccepted() {
		return appSharedPrefs.getBoolean(PREFERENCE_EULA_ACCEPTED, false);
	}

	public void setEulaAccepted(boolean result) {
		prefsEditor.putBoolean(PREFERENCE_EULA_ACCEPTED, result);
		prefsEditor.commit();
	}

	public String getEulaVersion() {
		return appSharedPrefs.getString(PREFERENCE_EULA_VERSION, "1.0");
	}

	public void setEulaVersion(String version) {
		prefsEditor.putString(PREFERENCE_EULA_VERSION, version);
		prefsEditor.commit();
	}

	public void setInternetUsageOption(ConnType cType){
		prefsEditor.putString(INTERNET_USAGE, cType.toString());
		SLog.debug(this.getClass(), "User changed internet usage pref to " + cType.toString());
		prefsEditor.commit();		
	}
	
	public ConnType getInternetUsageOption(){
		String cTypeString = appSharedPrefs.getString(INTERNET_USAGE,
				ConnType.APP_DATA.toString());
		SLog.debug(this.getClass(), "User preference internet usage " + cTypeString);
		return ConnType.valueOf(cTypeString);		
	}

	private String getAppPrefsString(String prefsString) {
		return context.getPackageName() + "." + prefsString;
	}
	
	public void setDatausage(long dataUsage) {
		prefsEditor.putLong(DATA_USAGE, dataUsage);
		prefsEditor.commit();
	}

	public Long getDatausage() {
		return appSharedPrefs.getLong(DATA_USAGE, 0);
	}
}
