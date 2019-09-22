package com.dream.music.LocalMusic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.dream.music.R;
import com.dream.music.util.FileUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends Activity
{
	ImageView ivw;
    TextView tvw,tvw2,tvw3;
    SeekBar sbr;
    String musicPath;
    Timer _timer = new Timer();
    TimerTask t;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    MediaPlayer mp = new MediaPlayer();
    Switch sch;
    int PLAY_MODE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		ivw = findViewById(R.id.playerImageView);
        tvw = findViewById(R.id.playerTextView);
        sbr = findViewById(R.id.playerSeekBar);
        tvw2 = findViewById(R.id.playerTextView2);
        tvw3 = findViewById(R.id.playerTextView3);
        sch = findViewById(R.id.playerSwitch);
        musicPath = FileUtil.readFile("/sdcard/player.info");
        FileUtil.deleteFile("/sdcard/player.info");
        loadRes();
        events();
        if(mp.isPlaying()){
            t = new TimerTask(){

                @Override
                public void run()
                {
                    // TODO: Implement this method
                    runOnUiThread(new Runnable(){

                            @Override
                            public void run()
                            {
                                // TODO: Implement this method
                                tvw2.setText(String.valueOf(mp.getCurrentPosition()));
                            }
                        });
                }
            };
            _timer.schedule(t,(int)20,(int)20);
        }
	}
	
	@Override
    public void onBackPressed()
    {
        // TODO: Implement this method
        super.onBackPressed();
        mp.stop();//当返回按钮被触碰时停止播放器。
    }
	
	private void events()
    {
        // TODO: Implement this method
        sbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //获取拖动结束之后的位置
                    int progress = seekBar.getProgress();
                    //跳转到某个位置播放
                    mp.seekTo(progress);
                }
            });
        sch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton p1, boolean p2)
                {
                    // TODO: Implement this method
                    if(p2){
                        PLAY_MODE = 1;
                        FileUtil.writeFile("/sdcard/.dreams/CloudDL/playMode.info","1");
                        mp.setLooping(true);
                    }else{
                        PLAY_MODE = 2;
                        FileUtil.writeFile("/sdcard/.dreams/CloudDL/playMode.info","2");
                        mp.setLooping(false);
                    }
                }
            });
    }
	
	private void loadRes()
    {
        // TODO: Implement this method
        try{
			MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
			mediaMetadataRetriever.setDataSource(musicPath);
			byte[] cover = mediaMetadataRetriever.getEmbeddedPicture();
			Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
			ivw.setImageBitmap(bitmap);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"加载歌曲封面时出错：" + e.getMessage(),Toast.LENGTH_SHORT).show();
            ivw.setImageResource(R.drawable.ic_launcher);
        }
        File f1 = new File(musicPath);
        tvw.setText(f1.getName());
        String modes = FileUtil.readFile("/sdcard/.dreams/CloudDL/playMode.info");
        if(modes.equals("1")){
            mp.setLooping(true);
            sch.setChecked(true);
        }else{
            mp.setLooping(false);
            sch.setChecked(false);
        }
        try
        {
            mp.setDataSource(musicPath);
            mp.prepare();
            sbr.setMax(mp.getDuration());
            tvw3.setText(String.valueOf(mp.getDuration()));
            mp.start();
            new MyThre().start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	public void playOrPause(View v){
        if(mp.isPlaying()){
            mp.pause();
        }else{
            mp.start();
        }
    }

    public void stopPlaying(View v){
        mp.stop();
    }
	
	class MyThre extends Thread{
        @Override
        public void run() {
            super.run();
            while(sbr.getProgress()<=sbr.getMax()){
                //获取当前音乐播放的位置
                int currentPosition = mp.getCurrentPosition();
                //让进度条动起来
                sbr.setProgress(currentPosition);
            }
        }
    }
}
