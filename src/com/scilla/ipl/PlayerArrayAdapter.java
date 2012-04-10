package com.scilla.ipl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerArrayAdapter extends ArrayAdapter<TeamDetails> {

	private static final String ASSETS_DIR = "images/";
	private Context context;
	private TextView country;
	private ImageView playericon;
	private TextView playername;
	private List<TeamDetails> players = new ArrayList();
	private TextView type;

	public PlayerArrayAdapter(Context paramContext, int paramInt,
			List<TeamDetails> paramList) {
		super(paramContext, paramInt, paramList);
		this.context = paramContext;
		this.players = paramList;
	}

	public int getCount() {
		return this.players.size();
	}

	public TeamDetails getItem(int paramInt) {
		return (TeamDetails) this.players.get(paramInt);
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		View localView = paramView;
		if (localView == null)
			localView = ((LayoutInflater) getContext().getSystemService(
					"layout_inflater")).inflate(R.layout.teams_details_row_view, paramViewGroup,
					false);
		TeamDetails localTeamsDetails = getItem(paramInt);
		this.playericon = ((ImageView) localView.findViewById(R.id.playerimage));
		this.playername = ((TextView) localView.findViewById(R.id.playername));
		this.type = ((TextView) localView.findViewById(R.id.type));
		this.country = ((TextView) localView.findViewById(R.id.country));
		this.playername.setText(localTeamsDetails.playername);
		String str = "images/" + localTeamsDetails.playerid;
		try {
			Bitmap localBitmap = BitmapFactory.decodeStream(this.context
					.getResources().getAssets().open(str));
			this.playericon.setImageBitmap(localBitmap);
			this.type.setText(localTeamsDetails.type);
			this.country.setText(localTeamsDetails.country);
			return localView;
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}
}
