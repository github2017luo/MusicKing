package com.dream.music;


import android.view.*;
import android.widget.*;
import fifthlight.musiccore.search.*;
import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.bumptech.glide.Glide;
import fifthlight.musiccore.Picture;
import fifthlight.musiccore.artist.Artist;
import fifthlight.musiccore.factory.NeteaseMusicFactory;
import fifthlight.musiccore.search.searchresult.SearchResult;
import fifthlight.musiccore.song.Song;
import fifthlight.musiccore.song.songquality.SongQuality;
import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import com.dream.music.util.FileUtil;
import android.content.Intent;
import com.dream.music.OnlinePlay.UsePlay;
import com.dream.music.LocalMusic.MusicLists;

public class MainActivity extends Activity 
{
	ListView lvw;
	List<Song> ls = new ArrayList<Song>();
	int quality = 0;
	SongQuality sqya = null;
	Button btn1;
	EditText edt1;
	//For nothing
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		lvw = findViewById(R.id.mainListView);
		edt1 = findViewById(R.id.mainEditText);
		btn1 = findViewById(R.id.mainButton);
		//init();
		event();
    }

	private void event()
	{
		// TODO: Implement this method
		btn1.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					ls.clear();
					init();
				}
			});
		lvw.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{
					// TODO: Implement this method
					Toast.makeText(getApplicationContext(),"你选择的音乐名称是：" + ls.get(p3).getName(),Toast.LENGTH_SHORT).show();
					Thread thr = null;
					thr = new Thread(new Runnable(){
							@Override
							public void run()
							{
								// TODO: Implement this method
								try
								{
									Set<SongQuality> ssq = ls.get(p3).getAvailableQualities();
									for(SongQuality sqy : ssq){
										quality = sqy.getBps();
										if(quality == 128000){
											sqya = sqy;
											break;}
									}
									Set<Picture> spea = ls.get(p3).getAlbum().getPictures();
									JSONObject ja = new JSONObject();
									ja.put("name",ls.get(p3).getName());
									ja.put("urls",ls.get(p3).getURL(sqya).toString());
									for(Picture pice : spea){
										ja.put("pic",pice.getURL(0,0).toString());
									}
									FileUtil.writeFile("/sdcard/.dreams/MusicKing/playList.json",ja.toString());
									runOnUiThread(new Runnable(){

											@Override
											public void run()
											{
												// TODO: Implement this method
												startActivity(new Intent(getApplicationContext(),UsePlay.class));
											}
										});
								}
								catch (IOException e)
								{e.printStackTrace();}
							}
						});
					thr.start();
					//thr.interrupt();
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case R.id.local_songs:
				startActivity(new Intent(getApplicationContext(),MusicLists.class));
				break;
			case R.id.about:
				startActivity(new Intent(getApplicationContext(),AboutActivity.class));
				break;
				default:
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void init()
	{
		// TODO: Implement this method
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method			
					try
					{
						NameSearch idse = new NameSearch(edt1.getText().toString());
						final SearchResult<Song> srs = NeteaseMusicFactory.getInstance().getSongs(idse);
						runOnUiThread(new Runnable(){

								@Override
								public void run()
								{
									// TODO: Implement this method
									MyAdapter mya = new MyAdapter(srs);
									lvw.setAdapter(mya);
								}
							});
					}
					catch (Exception e)
					{e.printStackTrace();}
				}
			}).start();
	}
	
	class MyAdapter extends BaseAdapter
	{
		SearchResult<Song> srsa = null;
		Set<SongQuality> setsqa = null;
		
		public MyAdapter(SearchResult<Song> srs){
			this.srsa = srs;
		}

		@Override
		public int getCount()
		{
			// TODO: Implement this method
			if(srsa.length() > 30){
				return 29;
			}else{
				return srsa.length();
			}
		}

		@Override
		public Object getItem(int p1)
		{
			// TODO: Implement this method
			return null;
		}

		@Override
		public long getItemId(int p1)
		{
			// TODO: Implement this method
			return p1;
		}

		@Override
		public View getView(int p1, View p2, ViewGroup p3)
		{
			// TODO: Implement this method
			View v = getLayoutInflater().inflate(R.layout.music_a,null);
			TextView t1 = v.findViewById(R.id.music_aTextView2);
			TextView t2 = v.findViewById(R.id.music_aTextView);
			ImageView img = v.findViewById(R.id.music_aImageView);		
			try
			{
				ls.add(srsa.getItems(0).get(p1));
				String artics = "";
				List<Artist> latsa = ls.get(p1).getArtists();
				for(int i = 0; i < latsa.size(); i ++){
					artics += latsa.get(i).getName();
					if(i < latsa.size() - 1){
						artics += "、";
					}
				}
				Set<Picture> spa = srsa.getItems(0).get(p1).getAlbum().getPictures();
				t1.setText(srsa.getItems(0).get(p1).getName());			
				t2.setText(artics);
				for(Picture pics : spa){
					Glide.with(getApplicationContext()).load(pics.getURL(200,200)).into(img);
				}
			}
			catch (IOException e)
			{e.printStackTrace();}
			return v;
		}	
	}
} 
