package com.cworld.navigation;

import java.util.ArrayList;
import java.util.HashMap;

import com.cworld.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import server.*;

import com.cworld.imageLoader.ImageLoader;
import com.cworld.utility.TimeProvider;

public class Review_Activity extends Activity{
	private boolean commentOn = false;
	private String imageId;
	private String timeTag;
	private String cityName;
	private ImageView canvas;
	private Button back, showComment, sendComment;
	private EditText addComment;
	private ListView comments;
	private static ArrayList<HashMap<String,String>> commentsContent;
	private SimpleAdapter adapter;
	private LinearLayout commentAddingBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		comments = (ListView) findViewById(R.id.MyListView);
		comments.setVisibility(View.INVISIBLE);
		commentsContent = new ArrayList<HashMap<String,String>>();
		
		canvas = (ImageView) findViewById(R.id.imageView);
		back = (Button) findViewById(R.id.review_back);
		showComment = (Button) findViewById(R.id.comments);
		sendComment = (Button) findViewById(R.id.send_comment);
		addComment = (EditText) findViewById(R.id.add_comment);
		
		back.setOnClickListener(buttonClickListener);
		showComment.setOnClickListener(buttonClickListener);
		sendComment.setOnClickListener(buttonClickListener);
		
		commentAddingBar = (LinearLayout) findViewById(R.id.add_comment_bar);
		commentAddingBar.setVisibility(View.INVISIBLE);
		
		Intent intent = getIntent();
		imageId = intent.getStringExtra("imageId");
		cityName = intent.getStringExtra("cityName");
		loadImage(imageId);
		loadComment();
	}
	
	private void loadImage(String imageId){
		ImageObject imageObject = Client.getImage(imageId);
		ImageLoader imageLoader = new ImageLoader(canvas);
		imageLoader.loadingImage(imageObject.getImage());
		return;
	}

	private void loadComment(){
		/* setup comments for test */
		CommentObject[] comment = Client.getComments(imageId);
		if(comment !=null){
			for(int i=0; i<comment.length;i++){
				commentsContent.add(new HashMap<String, String>());
				commentsContent.get(i).put("author", comment[i].author);
				commentsContent.get(i).put("comments", comment[i].comment);
			}
			adapter = new SimpleAdapter(this, commentsContent,
				R.layout.entry_comment,
				new String[]{"author", "comments"},
				new int[]{R.id.entry_author, R.id.entry_comment});
			comments.setAdapter(adapter);
		}
	}
	private OnClickListener buttonClickListener = 
			new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(v.getId()==R.id.review_back){
						Intent intent = new Intent();
						intent.putExtra("cityName", cityName);
						intent.setClass(Review_Activity.this, Directory_Activity.class);
						Review_Activity.this.startActivity(intent);
					}
					if(v.getId()==R.id.comments){
						commentOn = !commentOn;
						if(commentOn){
						/* show comment */
							comments.setVisibility(View.VISIBLE);
							commentAddingBar.setVisibility(View.VISIBLE);
						}
						else{
						/* hide comment */
							comments.setVisibility(View.INVISIBLE);
							commentAddingBar.setVisibility(View.INVISIBLE);
						}
					}
					if(v.getId()==R.id.send_comment){
						Editable editComment = addComment.getText();
						timeTag = TimeProvider.getTime();
						CommentObject comment = new CommentObject(imageId, timeTag, 
								Profile_Activity.authorName, editComment.toString());
						Client.sendComment(comment);
						addComment.setText("");
						loadComment();
					}
				}
	};//buttonClickListener

}