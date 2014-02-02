package server;

import java.io.Serializable;

public class CommentObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String imageId;
	public String time;
	public String author;
	public String comment;


	public CommentObject(String imageId, String time, String author, String comment){
		this.imageId = imageId;
		this.time = time;
		this.author = author;
		this.comment = comment;
	}
	
};