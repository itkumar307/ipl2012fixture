package com.scilla.ipl;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.scilla.util.SLog;

public class LiveScoreActivity extends Activity {
	InputStream inputStream;
	String type;
	WebView webView;
	final Activity activity = this;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.livescorewebview);
		type = getIntent().getStringExtra("type");

		if (type.equals("livescore")) {
			showWebView("http://m.espncricinfo.com");
		} else if (type.equals("teamnews")) {
			showWebView("http://www.iplt20.com/mobile/news");
		} else {
			showWebView("http://www.iplt20.com/mobile/home");
		}
	}

	private void showWebView(String url) {
		try {
			webView = (WebView) findViewById(R.id.webView1);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					activity.setTitle("Loading...");
					activity.setProgress(progress * 100);

					if (progress == 100)
						activity.setTitle(R.string.app_name);
				}
			});
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					// Handle the error
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			SLog.debug(getClass(), "company add" + url);
			webView.loadUrl(url);

		} catch (Exception e) {
			SLog.debug(getClass(), "uri wrong" + e.getMessage(), e);

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
