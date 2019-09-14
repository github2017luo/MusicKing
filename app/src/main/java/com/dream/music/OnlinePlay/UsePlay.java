package com.dream.music.OnlinePlay;

import android.app.*;
import android.widget.*;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.dream.music.R;
import com.dream.music.util.FileUtil;

public class UsePlay extends Activity
{
	private Player mPlayer;
	ImageView img;
	SeekBar sekb;
	TextView tvw1;
	String a = "";
	JSONObject ja = new JSONObject();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online_play);
		sekb = findViewById(R.id.seekBar);
		img = findViewById(R.id.online_playImageView);
		tvw1 = findViewById(R.id.online_playTextViewSinger);
		a = FileUtil.readFile("/sdcard/.dreams/MusicKing/playList.json");
		ja = JSONObject.parseObject(a);
		mPlayer = new Player(sekb);
		readCache();
	}

	private void readCache()
	{
		// TODO: Implement this method
		tvw1.setText(ja.getString("name"));
		Glide.with(getApplicationContext()).load(ja.getString("pic")).into(img);
		new Thread(new Runnable(){

                @Override
                public void run()
                {
                    // TODO: Implement this method
                    mPlayer.playUrl(ja.getString("urls"));
                }
            }).start();
	}
	
	public void play(View v){
        if(mPlayer.Playing()){
            mPlayer.pause();
        }else{
            mPlayer.play();
        }
    }
	
	public void dl_music(View v){
		DownloadManager dmr = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
		Uri uri = Uri.parse(ja.getString("urls"));
		DownloadManager.Request dmrt = new DownloadManager.Request(uri);
		dmrt.setDestinationInExternalFilesDir(getApplicationContext(),null,ja.getString("name") + ".mp3");
		//dmrt.setDestinationInExternalPublicDir("MusicKing", ja.getString("name") + ".mp3");
		dmr.enqueue(dmrt);
	}
	
	@Override
    public void onBackPressed()
    {
        // TODO: Implement this method
        super.onBackPressed();
        mPlayer.stop();
    }
}
