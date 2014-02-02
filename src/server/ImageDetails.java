package server;

import java.io.Serializable;

public class ImageDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imageId;  // leave empty here
	private String address;
	private String time;
	private String author;
	private String city; 
//	public ArrayList<String> comments;

	public ImageDetails(String imageId, String address, String time, String author, String city){
		this.imageId = imageId;
		this.address = address;
		this.time = time;
		this.author = author;
		this.city = city;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	
	public void setImageId(String imageId){
		this.imageId = imageId;
	}
	public String getImageId(){
		return imageId;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	public String getTime(){
		return time;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	public String getAuthor(){
		return author;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	public String getCity(){
		return city;
	}
	
};
