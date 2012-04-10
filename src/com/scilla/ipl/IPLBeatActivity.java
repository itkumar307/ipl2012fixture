package com.scilla.ipl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.scilla.util.Eula;

public class IPLBeatActivity extends Activity implements Eula.OnEulaAgreedTo {

	private Context context;
	private ScillaPreference appPreference;
	private int homeScreenWaitTimeInt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		context = getApplicationContext();
		appPreference = new ScillaPreference(context);
		homeScreenWaitTimeInt = context.getResources().getInteger(
				R.integer.HOMESCREEN_WAITTIME);
		 displayEula();

	}

	private void displayEula() {
		if (appPreference.getEulaAccepted()) {
			goToNextActivity(homeScreenWaitTimeInt);
		} else {
			Eula.show(this, getString(R.string.EULA_TITLE),
					getString(R.string.EULA_ACCEPT),
					getString(R.string.EULA_REFUSE), appPreference,
					getString(R.string.EULA_FILENAME));
		}
	}

	@Override
	public void onEulaAgreedTo() {
		goToNextActivity(homeScreenWaitTimeInt);

	}

	public void goToNextActivity(int homeScreenWaitTimeInt) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				gotoMenuPage();
			
			}
		}, homeScreenWaitTimeInt);
	}
	private void gotoMenuPage() {
		Intent mainIntent = new Intent(IPLBeatActivity.this,
				IplMenuActivity.class);
		startActivity(mainIntent);
		this.finish();	
	}
	
}