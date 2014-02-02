package com.cworld.utility;

import java.util.Calendar;

public class TimeProvider {

	
	
	public static String getTime(){
		Calendar calendar = Calendar.getInstance();
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute = calendar.get(Calendar.MINUTE);
		String time = hour.toString()+":"+minute.toString();
		return time;
	}
	
	public static String getDate(){
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH);
		Integer day = calendar.get(Calendar.DAY_OF_MONTH);
		String date = month.toString()+"/"+day.toString()+"/"+year.toString();
		return date;
	}
}