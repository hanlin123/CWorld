package server;

import android.graphics.drawable.Drawable;

public class ImageObject {
	private Drawable image;
	private ImageDetails imgDetails;


	public void setImage(Drawable image){
		this.image = image;
	}
	public Drawable getImage(){
		return this.image;
	}
	
	public void setImageDetails(ImageDetails details){
		this.imgDetails = details;
	}
	public ImageDetails getImageDetails(){
		return this.imgDetails;
	}

}
