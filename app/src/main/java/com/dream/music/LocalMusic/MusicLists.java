package com.dream.music.LocalMusic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.dream.music.R;
import com.dream.music.util.FileUtil;
import java.io.File;
import java.util.ArrayList;

public class MusicLists extends Activity
{
	ListView lvw;
	ArrayList<String> lus = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localmusic);
		lvw = findViewById(R.id.localmusicListView);
		String path = FileUtil.readFile("/sdcard/.dreams/MusicKing/save_path.info");
		FileUtil.listDir(path,lus);
		MyAdapter mar = new MyAdapter(lus);
		lvw.setAdapter(mar);
		lvw.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					// TODO: Implement this method
					File f = new File(lus.get(p3).toString());
					FileUtil.writeFile("/sdcard/player.info",f.getPath());
					startActivity(new Intent(getApplicationContext(),PlayerActivity.class));
				}
			});
	}
	
	class MyAdapter extends BaseAdapter
	{
		ArrayList<String> lia = new ArrayList<>();
		
		public MyAdapter(ArrayList<String> liaa){
			this.lia = liaa;
		}
		@Override
		public int getCount()
		{
			// TODO: Implement this method
			return lia.size();
		}

		@Override
		public Object getItem(int p1)
		{
			// TODO: Implement this method
			return lia.get(p1);
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
			TextView arti = v.findViewById(R.id.music_aTextView);
			TextView t2 = v.findViewById(R.id.music_aTextView2);
			ImageView ing1 = v.findViewById(R.id.music_aImageView);
			arti.setVisibility(View.GONE);
			File file = new File(lia.get(p1).toString());
			t2.setText(file.getName());
			try{
				MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
				mediaMetadataRetriever.setDataSource(file.getPath());
				byte[] cover = mediaMetadataRetriever.getEmbeddedPicture();
				Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
				ing1.setImageBitmap(bitmap);
			}catch(Exception e){
				ing1.setImageResource(R.drawable.ic_launcher);
			}
			return v;
		}
	}
}
