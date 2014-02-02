package com.cworld.navigation;

//in this activity ,there are two buttons one will show personal profile, the other will cancel the change to the profile
//people use EditText to modify the personal information
//the update function should be implemented in onclicked funtion

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.cworld.R;

public class Profile_Activity extends Activity{

	private EditText Name;
	private EditText Age;
	public static String authorName = null;
	public static String AgeString = null;

	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		 Button ApplyBtn = (Button)findViewById(R.id.ApplyBtn);
	     Button CancleBtn = (Button)findViewById(R.id.CancelBtn);
	     Name = (EditText)findViewById(R.id.Name);
	     Age = (EditText)findViewById(R.id.Age);

	     
	     if(authorName!=null)
	    	 Name.setText(authorName);
	     if(AgeString!=null)
	    	 Age.setText(AgeString);

	     
	     ApplyBtn.setOnClickListener(buttonClickListener);
	     CancleBtn.setOnClickListener(buttonClickListener);
	}
	
	OnClickListener buttonClickListener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent();
			if(v.getId()==R.id.ApplyBtn){
				Editable editName = Name.getText();
				Editable editAge = Age.getText();
				authorName = editName.toString();
				AgeString = editAge.toString();
			}
		
			intent.setClass(Profile_Activity.this, Root_Activity.class);
			Profile_Activity.this.startActivity(intent);
		}
	}; 
}
