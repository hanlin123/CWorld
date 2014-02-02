package com.cworld.navigation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import server.Client;
import server.ImageDetails;

import com.cworld.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.cworld.utility.*;


public class Sync_Activity extends Activity{

	private Button back;
	private Button sycn;
	private Button takePic;
	private Button choosePic;
	private ImageView canvas;
	private TextView city, timeTag, street, date;
	private Uri picUri;
	private Bitmap bitmap;
	private GPS geolocation;
	private Double latitude;
	private Double longitude;
	private Geocoder geocoder;
	List<Address> addresses;
	private String streetString, cityString, timeString;
	
	private static final int PICK_PICTURE = 1;
	private static final int TAKE_PICTURE = 2;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		 back = (Button)findViewById(R.id.sycn_cancel);
		 sycn = (Button)findViewById(R.id.sycn);
		 takePic = (Button)findViewById(R.id.take_pic);
		 choosePic = (Button)findViewById(R.id.choose_pic);
		 canvas = (ImageView)findViewById(R.id.sycn_pic);
		 city = (TextView)findViewById(R.id.sync_city);
		 timeTag = (TextView)findViewById(R.id.sync_time);
		 date = (TextView)findViewById(R.id.sync_date);
		 street = (TextView)findViewById(R.id.sync_loc);
		 
		 geolocation = new GPS();
		 LocationManager locationManager = 
				 (LocationManager) Sync_Activity.this.getSystemService(Context.LOCATION_SERVICE);
		 locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, geolocation);
		 geocoder = new Geocoder(this, Locale.getDefault());
		 
		 back.setOnClickListener(buttonClickListener);
		 takePic.setOnClickListener(buttonClickListener);
		 choosePic.setOnClickListener(buttonClickListener);
		 sycn.setOnClickListener(buttonClickListener);
	}
	
	private OnClickListener buttonClickListener = 
			new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent();
					if(v.getId() == R.id.sycn_cancel){
						intent.setClass(Sync_Activity.this, Root_Activity.class);
						Sync_Activity.this.startActivity(intent);
					}
					if(v.getId() == R.id.take_pic){
						//Camera.takePic(Sync_Activity.this, picUri);
						try{
							ContentValues content = new ContentValues();
							content.put(Media.TITLE, "image");
							content.put(Media.DESCRIPTION, "5434");
							picUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, content);
							Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							intent1.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
							startActivityForResult(intent1, TAKE_PICTURE);
						} catch (Exception e){
							e.printStackTrace();
						}
					}
					if(v.getId() == R.id.choose_pic){
						Camera.pickPic(Sync_Activity.this);
					}
					if(v.getId()==R.id.sycn){
						boolean sendOrNot = false;
						ImageDetails imageDetail = 
								new ImageDetails(null, streetString, timeString, Profile_Activity.authorName, cityString);
						if(bitmap !=null)
							sendOrNot = Client.sendImage(bitmap, imageDetail);
						
						if(sendOrNot){
							Toast toast = Toast.makeText(Sync_Activity.this, "Image is delivered.", Toast.LENGTH_SHORT);
							toast.show();
							
						}
						else{
							Toast toast = Toast.makeText(Sync_Activity.this, "Deliver failed. Try again later.", Toast.LENGTH_SHORT);
							toast.show();
						}
					}
				}
	};//buttonClickListener
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == PICK_PICTURE && resultCode == Activity.RESULT_OK){
			try{
				if(bitmap != null){
					bitmap.recycle();
				}
				InputStream stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				stream.close();
				canvas.setImageBitmap(bitmap);
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){
			
			canvas.setImageURI(null);
			canvas.setImageURI(picUri);
			try{
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		try{
			latitude = GPS.getLatitude();
			longitude = GPS.getLongitude();
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			String[] addr = GPS.getAddress(addresses);
			if(addr[0]!=null){
				streetString = addr[0];
				street.setText(streetString);
			}
			if(addr[1]!=null){
				cityString = addr[1];
				city.setText(cityString);
			}
			
			date.setText(TimeProvider.getDate());
			timeString = TimeProvider.getTime();
			timeTag.setText(timeString);
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
}
