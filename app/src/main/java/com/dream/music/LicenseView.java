package com.dream.music;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.webkit.WebSettings;

public class LicenseView extends Activity
{
	WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.open_source_view);
		web = findViewById(R.id.open_source_viewWebView);
		web.setWebViewClient(new WebViewClient());
		web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		WebSettings webSettings = web.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		String url = "file:///android_asset/" + "LICENSE.txt";
		web.loadUrl(url);
	}
}
