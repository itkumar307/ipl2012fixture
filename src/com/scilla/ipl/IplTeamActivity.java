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

public class IplTeamActivity extends ListActivity {
	/** Called when the activity is first created. */
	private Button click, click2Team;
	private String team, team1;
	Spinner teamSpinner, team1Spinner, team2Spinner;
	Uri teamList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.teamsearch);
		click = (Button) findViewById(R.id.button1);
		click2Team = (Button) findViewById(R.id.button2);
		teamSpinner = (Spinner) findViewById(R.id.spinner1);
		team1Spinner = (Spinner) findViewById(R.id.spinner2);
		team2Spinner = (Spinner) findViewById(R.id.spinner3);
		setAdapter();
		AdView adView = (AdView) this.findViewById(R.id.ad);
		adView.loadAd(new AdRequest());
		teamSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long row) {
				String[] mani = getResources().getStringArray(R.array.TEAM);
				team = mani[position];
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}

		});
		team1Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long row) {
				String[] mani = getResources().getStringArray(R.array.TEAM);
				team = mani[position];
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}

		});
		team2Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long row) {
				String[] mani = getResources().getStringArray(R.array.TEAM);
				team1 = mani[position];
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}

		});
		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				teamList = Uri.withAppendedPath(IplProvider.TEAM_URI, team);
				setListAdapter();

			}
		});
		click2Team.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					teamList = Uri.withAppendedPath(IplProvider.TEAM_URI1, team
							+ "/" + team1);
					setListAdapter();
				} catch (Exception e) {
					SLog.error(this.getClass(), "click2Team" + e.getMessage());
				}

			}
		});
	}

	private void setAdapter() {
		try {
			ArrayAdapter<CharSequence> team = ArrayAdapter.createFromResource(
					this, R.array.TEAM, android.R.layout.simple_spinner_item);
			team.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			teamSpinner.setAdapter(team);
			team1Spinner.setAdapter(team);
			team2Spinner.setAdapter(team);
		} catch (Exception e) {
			SLog.error(this.getClass(), "setAdapter" + e.getMessage());
		}

	}

	private void setListAdapter() {
		Cursor teamCursor = null;
		try {
			teamCursor = managedQuery(teamList, null, null, null, null);
			SLog.debug(this.getClass(),
					"teamCursorcount:" + teamCursor.getCount());
			MatchListAdapter adapter = new MatchListAdapter(this, teamCursor,
					new String[] { IplProvider.Columns._ID },
					new int[] { R.id.teamvsteam });
			SLog.debug(this.getClass(), "setListview: carListAdapter created");
			setListAdapter(adapter);
		} catch (Exception e) {
			SLog.error(this.getClass(), "setListAdapter" + e.getMessage());
		} finally {
			//if (teamCursor != null)
				//teamCursor.close();
		}
	}

}