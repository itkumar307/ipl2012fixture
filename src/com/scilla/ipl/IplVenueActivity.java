package com.scilla.ipl;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.scilla.util.SLog;

public class IplVenueActivity extends ListActivity {
	Spinner venueSpinner;
	String place;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.venueplace);
		venueSpinner = (Spinner) findViewById(R.id.spinner2);
		Button click = (Button) findViewById(R.id.button1);
		setAdapter();
		AdView adView = (AdView) this.findViewById(R.id.ad);
		adView.loadAd(new AdRequest());
		venueSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long row) {
				String[] placeArray = getResources().getStringArray(
						R.array.VENUS);
				place = placeArray[position];
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}

		});
		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setListAdapter();

			}
		});
	}

	private void setAdapter() {
		try {
			ArrayAdapter<CharSequence> place = ArrayAdapter.createFromResource(
					this, R.array.VENUS, android.R.layout.simple_spinner_item);
			place.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			venueSpinner.setAdapter(place);
		} catch (Exception e) {
			SLog.debug(this.getClass(), "setAdapter" + e.getMessage(), e);
		}
	}

	private void setListAdapter() {
		try {
			Uri placeList = Uri.withAppendedPath(IplProvider.PLACE_URI, place);
			Cursor teamCursor = managedQuery(placeList, null, null, null, null);
			SLog.debug(this.getClass(),
					"teamCursorcount:" + teamCursor.getCount());
			MatchListAdapter adapter = new MatchListAdapter(this, teamCursor,
					new String[] { IplProvider.Columns._ID },
					new int[] { R.id.teamvsteam });
			SLog.debug(this.getClass(), "setListview: carListAdapter created");
			setListAdapter(adapter);
		} catch (Exception e) {
			SLog.debug(this.getClass(),
					"setListAdapter" + e.getMessage(), e);
		}

	}

}