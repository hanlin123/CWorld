package com.cworld.navigation;


import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;
import server.*;

import com.cworld.R;


public class Root_Activity extends ListActivity {
	
	private Button see;
	private Button newPic;
	private ImageButton profile;
	private static ArrayList<HashMap<String,String>> cities;
	private static String[] cityListFromServer;
	private SimpleAdapter adapter;
	private TextView userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);
		
		testAuthorName();
		
		see = (Button) findViewById(R.id.see);
		newPic = (Button) findViewById(R.id.takePic);
		profile = (ImageButton) findViewById(R.id.profilePic);
		userName = (TextView) findViewById(R.id.title);
		
		String greetUser = "Hello, "+Profile_Activity.authorName+"!";
		userName.setText(greetUser);
		cities = new ArrayList<HashMap<String, String>>();
		see.setOnClickListener(clickButtonListener);
		newPic.setOnClickListener(clickButtonListener);
		profile.setOnClickListener(clickButtonListener);
		
		updateCityList();
		
	}

	protected void updateCityList(){
		cityListFromServer = Client.getCityList();
		cities.clear();
		for(int i=0; i<cityListFromServer.length;i++){
			cities.add(new HashMap<String, String>());
			cities.get(i).put("cityName", cityListFromServer[i]);
		}
		adapter = new SimpleAdapter(this,cities,
				R.layout.entry_root,
				new String[]{"cityName"},
				new int[]{R.id.entry_cityName});
		setListAdapter(adapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		String cityName = cities.get(position).get("cityName");
		Intent intent = new Intent();
		intent.setClass(Root_Activity.this, Directory_Activity.class);
		intent.putExtra("cityName", cityName);
		Root_Activity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.root_, menu);
		return true;
	}

	private OnClickListener clickButtonListener = 
			new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					if(v.getId() == R.id.see){
						updateCityList();
					}
					if(v.getId()==R.id.takePic){
						intent.setClass(Root_Activity.this, Sync_Activity.class);
						Root_Activity.this.startActivity(intent);
					}
					if(v.getId()==R.id.profilePic){
						intent.setClass(Root_Activity.this, Profile_Activity.class);
						Root_Activity.this.startActivity(intent);
					}
				}
	};

	private void testAuthorName(){
		if(Profile_Activity.authorName == null){
			Intent intent = new Intent();
			intent.setClass(Root_Activity.this, Profile_Activity.class);
			Root_Activity.this.startActivity(intent);
		}
	}

}
