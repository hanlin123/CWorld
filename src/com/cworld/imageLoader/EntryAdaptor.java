package com.cworld.imageLoader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cworld.R;
import server.*;
 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class EntryAdaptor extends BaseAdapter{

	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private ArrayList<ImageDetails> info;
	private Context context;
	private AsyncImageLoader imageLoader = new AsyncImageLoader();
	
	
	public EntryAdaptor(Context context, ImageDetails[] info){
		this.context = context;
		this.info = new ArrayList<ImageDetails>();
		for(int i=0;i<info.length;i++){
			this.info.add(info[i]);
		}
	}
	
	/* Return the number of the entries in the Adapter */
	@Override
	public int getCount() {
		return info.size();
	}

	/* Return item object based on the position information */
	@Override
	public Object getItem(int position) {
		return info.get(position);
	}

	/* Return the ID of the item corresponding to the position */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*  */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = rowViews.get(position);
		if(rowView==null){
			LayoutInflater inflater = LayoutInflater.from(context);
			rowView = inflater.inflate(R.layout.entry_directoy, null);
			
			TextView time = (TextView)rowView.findViewById(R.id.entry_timeTag);
			TextView address = (TextView)rowView.findViewById(R.id.entry_address);
			TextView author = (TextView)rowView.findViewById(R.id.entry_author);
			ImageView picSample = (ImageView)rowView.findViewById(R.id.entry_picSample);
			ImageLoader imgL = new ImageLoader(picSample);
			
			ImageDetails imageDetails = (ImageDetails)getItem(position);
			time.setText(imageDetails.getTime());
			address.setText(imageDetails.getAddress());
			String imageAuthor = "by "+imageDetails.getAuthor();
			author.setText(imageAuthor);
			imageLoader.loadDrawable(imageDetails.getImageId(), imgL);
		
			rowViews.put(position, rowView);		
		}
		return rowView;
	}
}
