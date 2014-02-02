package com.cworld.utility;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

public class Camera {

	private static final int PICK_PICTURE = 1;
	private static final int TAKE_PICTURE = 2;
	
	public static void pickPic(Activity activity){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		activity.startActivityForResult(intent, PICK_PICTURE);
	}
	
	public static void takePic(Activity activity, Uri picUri){
		try{
			ContentValues content = new ContentValues();
			content.put(Media.TITLE, "image");
			content.put(Media.DESCRIPTION, "5434");
			picUri = activity.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, content);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
			activity.startActivityForResult(intent, TAKE_PICTURE);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
