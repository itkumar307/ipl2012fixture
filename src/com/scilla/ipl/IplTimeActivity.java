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

public class IplTimeActivity extends ListActivity {
	/** Called when the activity is first created. */
	Spinner timeSpinner, dateSpinner;
	String time, date;
	Uri value;
	String[] dataArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datetimesearch);
		dateSpinner = (Spinner) findViewById(R.id.spinner1);
		timeSpinner = (Spinner) findViewById(R.id.spinner2);
		Button click = (Button) findViewById(R.id.button2);
		Button click1 = (Button) findViewById(R.id.button1);
		cursorCreate();
		setAdapter();
		AdView adView = (AdView) this.findViewById(R.id.ad);
		adView.loadAd(new AdRequest());
		dateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long row) {
				date = dataArray[position];
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}

		});
		timeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long row) {
				// String[] placeArray
				// =getResources().getStringArray(R.array.TIMEARRAY);
				if (position == 1)
					time = "8";
				else
					time = "4";
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}

		});
		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				value = Uri.withAppendedPath(IplProvider.TIME_URI, time);
				setListAdapter();

			}
		});
		click1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				value = Uri.withAppendedPath(IplProvider.DATE_URI_SEARCH, date);
				setListAdapter();

			}
		});
	}

	private void setAdapter() {
		try {
			ArrayAdapter<CharSequence> time = ArrayAdapter.createFromResource(
					this, R.array.TIMEARRAY,
					android.R.layout.simple_spinner_item);
			time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			timeSpinner.setAdapter(time);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, dataArray);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dateSpinner.setAdapter(adapter);
		} catch (Exception e) {
			SLog.debug(this.getClass(),
					"error in set adapter" + e.getMessage(), e);
		}
	}

	private void setListAdapter() {

		try {
			Cursor teamCursor = managedQuery(value, null, null, null, null);
			SLog.debug(this.getClass(),
					"teamCursorcount:" + teamCursor.getCount());
			MatchListAdapter adapter = new MatchListAdapter(this, teamCursor,
					new String[] { IplProvider.Columns._ID },
					new int[] { R.id.teamvsteam });
			SLog.debug(this.getClass(), "setListview: carListAdapter created");
			setListAdapter(adapter);
		} catch (Exception e) {
			SLog.debug(this.getClass(), "setListAdapter" + e.getMessage(), e);
		}

	}

	void cursorCreate() {
		Cursor timeCursor = null;
		try {
			timeCursor = getContentResolver().query(IplProvider.DATE_URI, null,
					null, null, null);
			int i = 0;
			timeCursor.moveToFirst();
			dataArray = new String[timeCursor.getCount()];
			do {
				dataArray[i] = timeCursor.getString(timeCursor
						.getColumnIndex(IplProvider.Columns.DATE));
				i++;
			} while (timeCursor.moveToNext());
		} catch (Exception e) {
			SLog.error(this.getClass(), "error in get data" + e.getMessage());
		} finally {
			if (timeCursor != null)
				timeCursor.close();
		}
	}
}