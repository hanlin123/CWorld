package com.cworld.navigation;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import server.*;
import com.cworld.R;
import com.cworld.imageLoader.EntryAdaptor;

public class Directory_Activity extends ListActivity{
	private TextView title;
	private Button cancel;
	private ImageDetails[] picListFromServer;
	private String cityName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directory);
		title = (TextView) findViewById(R.id.directory_title);
		title.setTextColor(Color.WHITE);
		title.setBackgroundColor(Color.GRAY);
		cancel = (Button) findViewById(R.id.directory_cancel);
		
		Intent intent = getIntent();
		cityName = intent.getStringExtra("cityName");
		title.setText(cityName);
		cancel.setOnClickListener(buttonClickListener);
		
		updatePicList();
	}
	private void updatePicList(){
		picListFromServer = Client.getImageList(cityName);
		EntryAdaptor adapter = new EntryAdaptor(this, picListFromServer);
		ListView listView = getListView();
		listView.setAdapter(adapter);
	}
	private OnClickListener buttonClickListener = 
			new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent();
					if(v.getId() == R.id.directory_cancel){
						intent.setClass(Directory_Activity.this, Root_Activity.class);
						Directory_Activity.this.startActivity(intent);
					}
				}
		
	};//buttonClickListener
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		ImageDetails imageDetails = picListFromServer[position];
		Intent intent = new Intent();
		intent.setClass(Directory_Activity.this, Review_Activity.class);
		intent.putExtra("imageId", imageDetails.getImageId());
		intent.putExtra("cityName", cityName);
		intent.putExtra("timeTag", imageDetails.getTime());
		intent.putExtra("author", imageDetails.getAuthor());
		Directory_Activity.this.startActivity(intent);
	}
}
