package com.cworld.utility;


import java.util.List;

import java.util.StringTokenizer;


import android.location.Address;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPS implements LocationListener{

	private static double latitude, longitude;
	
	
	
	public static String[] getAddress(List<Address> addresses){
		String street=null, city=null, nation = null;
		try{
			
			street=addresses.get(0).getAddressLine(0);
			city=getShortCityName(addresses.get(0).getAddressLine(1));
			nation=addresses.get(0).getAddressLine(2);
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		} catch(IndexOutOfBoundsException e){
			System.out.println("latitude: "+latitude+"\nlongitude: "+longitude);
		}
		String[] addr = {street, city, nation};
		return addr;
	}
	public static double getLatitude(){
		return latitude;
	}
	public static double getLongitude(){
		return longitude;
	}
	private static String getShortCityName(String longCityName){
		String shortName;
		StringTokenizer divider = new StringTokenizer(longCityName, ",");
		shortName = divider.nextToken();
		return shortName;
	}
	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
