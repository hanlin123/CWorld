package com.cworld.imageLoader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageLoader {

	private ImageView imageView;
	
	public ImageLoader(ImageView view){
		super();
		this.imageView = view;
	}
	
	public void loadingImage(Drawable imageDrawable){
		imageView.setImageDrawable(imageDrawable);
	}
}
