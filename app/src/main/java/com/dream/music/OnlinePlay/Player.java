package com.dream.music.OnlinePlay;

import android.media.*;
import android.media.MediaPlayer.*;
import java.util.*;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.SeekBar;
import java.io.IOException;

public class Player implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener
{
	public MediaPlayer mediaPlayer; // 媒体播放器
    private SeekBar seekBar; 
    private Timer mTimer = new Timer(); // 计时器
	
	// 初始化播放器
    public Player(SeekBar seekBar) {
        super();
        this.seekBar = seekBar;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 每一秒触发一次
        mTimer.schedule(timerTask, 0, 1000);
    }

    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0); // 发送消息
            }
        }
    };
	
	@SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
            }
        };
    };

    public void play() {
        mediaPlayer.start();
    }

    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 暂停
    public void pause() {
        mediaPlayer.pause();
    }

    // 停止
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean Playing(){
        return mediaPlayer.isPlaying();
    }


    @Override
    public void onBufferingUpdate(MediaPlayer p1, int p2)
    {
        // TODO: Implement this method
        seekBar.setSecondaryProgress(p2);
        int currentProgress = seekBar.getMax() * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
    }

    @Override
    public void onCompletion(MediaPlayer p1)
    {
        // TODO: Implement this method
    }

    @Override
    public void onPrepared(MediaPlayer p1)
    {
        // TODO: Implement this method
    }
}
