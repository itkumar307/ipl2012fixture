package com.scilla.ipl;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.scilla.util.SLog;

public class IplMenuActivity extends Activity {
	/** Called when the activity is first created. */
	private ImageView teamSearch;
	private ImageView stadiumSearch;
	private ImageView dateSearch;
	private ImageView csk, dc, dd, kkr, kp, mi, pw, rcb, rr;
	private ImageView image1, image2, image3, image4;
	private TextView tx1, tx2, tx3, tx4, vs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overallmenuoption);
		teamSearch = (ImageView) findViewById(R.id.menu_image_team);
		stadiumSearch = (ImageView) findViewById(R.id.menu_image_stadium);
		dateSearch = (ImageView) findViewById(R.id.menu_image_time);
		csk = (ImageView) findViewById(R.id.csk);
		dc = (ImageView) findViewById(R.id.dc);
		dd = (ImageView) findViewById(R.id.dd);
		kkr = (ImageView) findViewById(R.id.kkr);
		kp = (ImageView) findViewById(R.id.kp);
		mi = (ImageView) findViewById(R.id.mi);
		pw = (ImageView) findViewById(R.id.pw);
		rcb = (ImageView) findViewById(R.id.rcb);
		rr = (ImageView) findViewById(R.id.rr);
		tx1 = (TextView) findViewById(R.id.menu_text_first_team_one);
		tx2 = (TextView) findViewById(R.id.menu_text_first_team_two);
		tx3 = (TextView) findViewById(R.id.menu_text_second_team_one);
		tx4 = (TextView) findViewById(R.id.menu_text_second_team_two);
		vs = (TextView) findViewById(R.id.menu_vs_match2);
		image1 = (ImageView) findViewById(R.id.menu_image_first_team_one);
		image2 = (ImageView) findViewById(R.id.menu_image_first_team_two);
		image3 = (ImageView) findViewById(R.id.menu_image_second_team_one);
		image4 = (ImageView) findViewById(R.id.menu_image_second_team_two);

		AdView adView = (AdView) this.findViewById(R.id.ad);
		adView.loadAd(new AdRequest());

		teamSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(IplMenuActivity.this,
						IplTeamActivity.class);
				startActivity(mainIntent);

			}
		});
		stadiumSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(IplMenuActivity.this,
						IplVenueActivity.class);
				startActivity(mainIntent);
			}
		});
		dateSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(IplMenuActivity.this,
						IplTimeActivity.class);
				startActivity(mainIntent);

			}
		});
		csk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("csk");

			}
		});
		dc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("dc");
			}
		});
		dd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("dd");

			}
		});
		kkr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("kkr");

			}
		});
		kp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("kp");

			}
		});
		mi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("mi");

			}
		});
		pw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("pw");

			}
		});
		rcb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("rcb");

			}
		});
		rr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seePlayer("rr");

			}
		});
		setMatchplayed();
	}

	private void setMatchplayed() {
		Cursor teamCursor = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("MMMM d");
			teamCursor = managedQuery(
					Uri.withAppendedPath(IplProvider.DATE_URI_SEARCH,
							format.format(new Date().getTime())), null, null,
					null, null);
			int count = teamCursor.getCount();
			teamCursor.moveToFirst();
			if (count > 1) {
				image1.setImageBitmap(BitmapFactory.decodeStream(getResources()
						.getAssets()
						.open("images/"
								+ teamCursor.getString(teamCursor
										.getColumnIndex(IplProvider.Columns.TEAM))
								+ ".png")));
				tx1.setText(teamCursor.getString(teamCursor
						.getColumnIndex(IplProvider.Columns.TEAM)));
				image2.setImageBitmap(BitmapFactory.decodeStream(getResources()
						.getAssets()
						.open("images/"
								+ teamCursor.getString(teamCursor
										.getColumnIndex(IplProvider.Columns.TEAM1))
								+ ".png")));
				tx2.setText(teamCursor.getString(teamCursor
						.getColumnIndex(IplProvider.Columns.TEAM1)));
				teamCursor.moveToNext();
				image3.setImageBitmap(BitmapFactory.decodeStream(getResources()
						.getAssets()
						.open("images/"
								+ teamCursor.getString(teamCursor
										.getColumnIndex(IplProvider.Columns.TEAM))
								+ ".png")));
				tx3.setText(teamCursor.getString(teamCursor
						.getColumnIndex(IplProvider.Columns.TEAM)));
				image4.setImageBitmap(BitmapFactory.decodeStream(getResources()
						.getAssets()
						.open("images/"
								+ teamCursor.getString(teamCursor
										.getColumnIndex(IplProvider.Columns.TEAM1))
								+ ".png")));
				tx4.setText(teamCursor.getString(teamCursor
						.getColumnIndex(IplProvider.Columns.TEAM1)));
			} else {
				image1.setImageBitmap(BitmapFactory.decodeStream(getResources()
						.getAssets()
						.open("images/"
								+ teamCursor.getString(teamCursor
										.getColumnIndex(IplProvider.Columns.TEAM))
								+ ".png")));
				tx1.setText(teamCursor.getString(teamCursor
						.getColumnIndex(IplProvider.Columns.TEAM)));
				image2.setImageBitmap(BitmapFactory.decodeStream(getResources()
						.getAssets()
						.open("images/"
								+ teamCursor.getString(teamCursor
										.getColumnIndex(IplProvider.Columns.TEAM1))
								+ ".png")));
				tx2.setText(teamCursor.getString(teamCursor
						.getColumnIndex(IplProvider.Columns.TEAM1)));
				image3.setVisibility(View.GONE);
				tx3.setVisibility(View.GONE);
				image4.setVisibility(View.GONE);
				tx4.setVisibility(View.GONE);
				vs.setVisibility(View.GONE);

			}

		} catch (Exception e) {
			SLog.error(this.getClass(), "match today" + e.getMessage());
		} finally {
			if (teamCursor != null)
				teamCursor.close();
		}
	}

	private void seePlayer(String name) {
		Intent mainIntent = new Intent(IplMenuActivity.this,
				IplPlayerActivity.class);
		mainIntent.putExtra("playername", name);
		startActivity(mainIntent);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		SLog.debug(this.getClass(), "back");
		if (exitOnBackButton() && keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			SLog.debug(this.getClass(), "displayExitAlert");
			displayExitAlert();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected boolean hasRefreshButton() {
		return true;
	}

	protected boolean exitOnBackButton() {
		return true;
	}

	private void displayExitAlert() {
		AlertDialog dialog = new AlertDialog.Builder(IplMenuActivity.this)
				.create();
		dialog.setTitle(R.string.DIALOGMESSAGE);
		dialog.setButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		dialog.setButton2("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				IplMenuActivity.this.closeOptionsMenu();
			}
		});
		dialog.show();
	}

}
