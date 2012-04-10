package com.scilla.ipl;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.scilla.util.SLog;

public class IplPlayerActivity extends Activity {
	InputStream inputStream;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_details);
		String a = getIntent().getExtras().getString("playername");
		if (a.equals("csk")) {
			this.inputStream = getResources().openRawResource(
					R.raw.chennai_super_kings);
			setAdapter(this.inputStream);
		} else if (a.equals("dc")) {
			this.inputStream = getResources().openRawResource(
					R.raw.deccan_chargers);
			setAdapter(this.inputStream);
		} else if (a.equals("dd")) {
			this.inputStream = getResources().openRawResource(
					R.raw.delhi_daredevils);
			setAdapter(this.inputStream);
		} else if (a.equals("kkr")) {
			this.inputStream = getResources().openRawResource(
					R.raw.kolkata_knight_riders);
			setAdapter(this.inputStream);
		} else if (a.equals("kp")) {
			this.inputStream = getResources().openRawResource(
					R.raw.kings_punjab);
			setAdapter(this.inputStream);
		} else if (a.equals("mi")) {
			this.inputStream = getResources().openRawResource(
					R.raw.mumbai_indians);
			setAdapter(this.inputStream);
		} else if (a.equals("pw")) {
			this.inputStream = getResources().openRawResource(
					R.raw.pune_warriors_india);
			setAdapter(this.inputStream);
		} else if (a.equals("rr")) {
			this.inputStream = getResources().openRawResource(
					R.raw.rajasthan_royals);
			setAdapter(this.inputStream);
		} else if (a.equals("rcb")) {
			this.inputStream = getResources().openRawResource(
					R.raw.bang_royal_challengers);
			setAdapter(this.inputStream);
		}
	}

	private void setAdapter(InputStream input) {
		try {
			PlayersParse localPlayersParser = new PlayersParse();
			localPlayersParser.parse(this.inputStream);
			List<TeamDetails> localList = localPlayersParser.getList();
			PlayerArrayAdapter localPlayersArrayAdapter = new PlayerArrayAdapter(
					getApplicationContext(), R.layout.team_details, localList);
			((ListView) findViewById(R.id.playerslist))
					.setAdapter(localPlayersArrayAdapter);
		} catch (Exception e) {
			SLog.error(this.getClass(), "displayplayer" + e.getMessage());
		}
	}

}
