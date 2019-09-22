package com.dream.music.SettingGroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.dream.music.R;
import android.widget.EditText;
import android.content.DialogInterface;
import com.dream.music.util.FileUtil;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class SettingActivity extends Activity
{
	LinearLayout llt1;
	Spinner sp1,sp2;
	AlertDialog.Builder adbr;
	String[] cores = {"网易云音乐"};
	String[] qualy = {"12800"};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		adbr = new AlertDialog.Builder(this);
		llt1 = findViewById(R.id.settingsLinearLayout1);
		sp1 = findViewById(R.id.settingsSpinner1);
		sp2 = findViewById(R.id.settingsSpinner2);
		init();
	}

	private void init()
	{
		// TODO: Implement this method
		llt1.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					final EditText edt = new EditText(getApplicationContext());
					edt.setTextColor(0xff000000);
					edt.setTextSize(14);
					edt.setText("/sdcard/");//初始自由路径
					adbr.setTitle("请输入要存储到的路径");
					adbr.setMessage("例如： /sdcard/DreamMusic/Download/ ");
					adbr.setView(edt);
					adbr.setCancelable(false);
					adbr.setPositiveButton("好", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								String a = edt.getText().toString();
								FileUtil.writeFile("/sdcard/.dreams/MusicKing/save_path.info",a);
								Toast.makeText(getApplicationContext(),"设置成功",Toast.LENGTH_SHORT).show();
							}
						});
					adbr.setNegativeButton("不",null);
					adbr.create().show();
				}
			});
		sp1.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,cores));
		sp2.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,qualy));
	}
}
