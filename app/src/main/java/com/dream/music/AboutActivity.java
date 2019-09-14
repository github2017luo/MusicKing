package com.dream.music;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;

public class AboutActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}
	
	public void read_info(View v){
		startActivity(new Intent(getApplicationContext(),LicenseView.class));
	}
	
	public void offi_web(View v){
		Intent i = new Intent();
		i.setAction(Intent.ACTION_VIEW);
		i.setData(Uri.parse("https://github.com/DreamedWorker/MusicKing"));
		startActivity(i);
	}
}
