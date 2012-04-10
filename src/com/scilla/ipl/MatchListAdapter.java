package com.scilla.ipl;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.scilla.util.SLog;

public class MatchListAdapter extends SimpleCursorAdapter {

	private int viewlayout;
	private LayoutInflater mInflater;
	private Context ctx;

	private static class ViewHolder {
		TextView team, venue, date, time;
		ImageView teamimage1, teamimage2;
	}

	public MatchListAdapter(Context context, Cursor cursor, String[] from,
			int[] to) {
		super(context, R.layout.listviewdata, cursor, from, to);
		this.ctx = context;
		try {
			this.viewlayout = R.layout.listviewdata;
			mInflater = LayoutInflater.from(context);
		} catch (Exception e) {
			SLog.error(this.getClass(),
					"CarListAdapter(constructor)" + e.getMessage(), e);
		}
	}

	@Override
	public void bindView(View view, Context context, Cursor match) {
		SLog.debug(this.getClass(), "bindView");
		try {
			ViewHolder holder = (ViewHolder) view.getTag();

			if (holder == null) {
				holder = new ViewHolder();
				holder.teamimage1 = (ImageView) view
						.findViewById(R.id.imgteam1);
				holder.teamimage2 = (ImageView) view
						.findViewById(R.id.imgteam2);
				holder.team = (TextView) view.findViewById(R.id.teamvsteam);
				holder.time = (TextView) view.findViewById(R.id.time);
				holder.date = (TextView) view.findViewById(R.id.date);
				holder.venue = (TextView) view.findViewById(R.id.venuename);
				view.setTag(holder);
			}
			holder.team.setText(match.getString(match
					.getColumnIndex(IplProvider.Columns.TEAMS)));
			holder.venue.setText(match.getString(match
					.getColumnIndex(IplProvider.Columns.VENUE)));
			holder.date.setText(match.getString(match
					.getColumnIndex(IplProvider.Columns.DATE)));
			holder.time.setText(" ,"
					+ " "
					+ match.getString(match
							.getColumnIndex(IplProvider.Columns.TIME)) + " PM");

			holder.teamimage1.setImageBitmap(BitmapFactory.decodeStream(ctx
					.getResources()
					.getAssets()
					.open("images/"
							+ match.getString(match
									.getColumnIndex(IplProvider.Columns.TEAM))
							+ ".png")));

			holder.teamimage2.setImageBitmap(BitmapFactory.decodeStream(ctx
					.getResources()
					.getAssets()
					.open("images/"
							+ match.getString(match
									.getColumnIndex(IplProvider.Columns.TEAM1))
							+ ".png")));

		} catch (Exception e) {
			SLog.error(this.getClass(), "imageset" + e.getMessage());
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View v;
		v = mInflater.inflate(viewlayout, null);
		return v;
	}

}