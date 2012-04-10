package com.scilla.text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Helvetica extends TextView {

	public Helvetica(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public Helvetica(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public Helvetica(Context context) {
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