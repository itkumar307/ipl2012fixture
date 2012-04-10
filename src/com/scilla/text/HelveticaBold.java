package com.scilla.text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.scilla.ipl.R;

public class HelveticaBold extends TextView {

	public HelveticaBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	public HelveticaBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		setTextAppearance(context, R.style.boldText);
	}

	public HelveticaBold(Context context) {
		super(context);
		init();
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/HelveticaNeue-Light.ttf");
			setTypeface(tf);
		}
	}

}