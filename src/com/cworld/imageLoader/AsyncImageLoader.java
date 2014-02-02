package com.cworld.imageLoader;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import server.*;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;


public class AsyncImageLoader {

	private Map<String, SoftReference<Drawable>> imageCache =
			new HashMap<String, SoftReference<Drawable>>();
	
	
	public Drawable loadDrawable(final String imageId,
			final ImageLoader imageLoader){
		// Check if the image has already existed in the cache
		if(imageCache.containsKey(imageId)){
			// If the cache has the URL record, try to load the softRef
			SoftReference<Drawable> softReference=imageCache.get(imageId);
			// Return the Drawable if the image exists in the cache
			if(softReference.get() != null){
				return softReference.get();
			}
		}
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				imageLoader.loadingImage((Drawable) msg.obj);
			}
		};
		// If the URL is not found or the Drawable has been collected,
		// create a new thread to download image
		new Thread(){
			public void run() {
				
				Drawable drawable=loadImageFromImageId(imageId);

				//record the downloaded image in the HashMap with a softRef
				imageCache.put(imageId, new SoftReference<Drawable>(drawable));

				// Notice the parent that the image is ready to load
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			};
		}.start();
		return null;
	}/* End of loadDrawable */


	// Download image from the server 
	protected synchronized Drawable loadImageFromImageId(String imageId) {
		try {
			//use the URL to download the image and then create a Drawable Object
			ImageObject imageObject = Client.getThumbnailImage(imageId);
			return imageObject.getImage();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}/* End of loadImageFromUrl */
	
	
}
		

