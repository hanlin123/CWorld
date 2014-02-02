package server;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Client{

	private static String serverIP="128.237.201.174";
	private static Socket cliSock;



	public static boolean sendImage(Bitmap bitmap,ImageDetails imgDeet) {//function sends image to server
		boolean retval = false;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		byte[] buffer= out.toByteArray();
		try
		{
			cliSock=new Socket(serverIP,8070);
		}
		catch(Exception e)
		{
			Log.v("EXCEPTION",e.toString());
		}

		try
		{
			ObjectOutputStream os = new ObjectOutputStream(cliSock.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(cliSock.getInputStream());
			os.writeObject(new String("Sending Image"));//inform server of incoming image
			os.flush();
			os.writeObject(buffer);//send image
			os.flush();
			os.writeObject(imgDeet);//send image details as object
			os.flush();
			String obj=(String)is.readObject();
			if(obj.equalsIgnoreCase("done!"))
				retval = true;
			cliSock.close();
			
		}
		catch(Exception e)
		{
			Log.v("ERROR",e.toString());
		}
		return retval;
	}
	
	public static CommentObject[] getComments(String imageId) {//get list of comments for particular image
		CommentObject [] objs = null;
		try
		{
			cliSock=new Socket(serverIP,8070);
		}
		catch(Exception e)
		{
			Log.v("EXCEPTION",e.toString());
		}

		try
		{
			ObjectOutputStream os = new ObjectOutputStream(cliSock.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(cliSock.getInputStream());
			os.writeObject(new String("Demanding Comments"));//request comments from server
			os.writeObject(imageId);//send imageid to get comments for it
			os.flush();
			String x = (String)is.readObject();
			if(x.equalsIgnoreCase("Have Comments")) {//check for presence of comments
				objs = (CommentObject[])(is.readObject());//get comments 
			}
			cliSock.close();
		}
		catch(Exception e)
		{
			Log.v("ERROR",e.toString());
		}
		return objs;
	}
	
	public static void sendComment(CommentObject obj) {//sends a comment to the server
		try
		{
			cliSock=new Socket(serverIP,8070);
		}
		catch(Exception e)
		{
			Log.v("EXCEPTION",e.toString());
		}

		try
		{
			ObjectOutputStream os = new ObjectOutputStream(cliSock.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(cliSock.getInputStream());
			os.writeObject(new String("Sending Comment"));//inform server of incoming comment
			os.writeObject(obj);//send comment object
			os.flush();
			String ack=(String)is.readObject();
			//System.out.println(obj);
			cliSock.close();
		}
		catch(Exception e)
		{
			Log.v("ERROR",e.toString());
		}
	}

	public static ImageObject getThumbnailImage(String imgId) {
		ImageObject x = getImage(imgId);
		BitmapDrawable tmp = (BitmapDrawable)x.getImage();
		Bitmap resized = Bitmap.createScaledBitmap(tmp.getBitmap(), 50, 50, true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		resized.compress(Bitmap.CompressFormat.JPEG, 100, out);
		byte[] buffer= out.toByteArray();
		x.setImage(new BitmapDrawable(BitmapFactory.decodeByteArray(buffer, 0, buffer.length)));
		return x;
	}

	public static ImageObject getImage(String imgId) {//gets full image from server using imgid
		try
		{
			cliSock=new Socket(serverIP,8070);
		}
		catch(Exception e)
		{
			Log.v("EXCEPTION",e.toString());
		}

		try
		{
			ObjectOutputStream os = new ObjectOutputStream(cliSock.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(cliSock.getInputStream());
			os.writeObject(new String("Demanding Image"));//inform server of incoming image request
			os.flush();
			os.writeObject(imgId);//send imageid of required image
			os.flush();
			ImageObject imObj = new ImageObject();
			byte [] x = (byte[])(is.readObject());//get byte array of image
			imObj.setImage(new BitmapDrawable(BitmapFactory.decodeByteArray(x, 0, x.length)));//convert image to drawable and set image of the returning object
			imObj.setImageDetails((ImageDetails)(is.readObject()));
			cliSock.close();
			return imObj;
		}
		catch(Exception e)
		{
			Log.v("ERROR",e.toString());
		}
		return null;
	}

	public static ImageDetails[] getImageList(String city) {//get list of images in the city
		try
		{
			cliSock=new Socket(serverIP,8070);
		}
		catch(Exception e)
		{
			Log.v("EXCEPTION",e.toString());
		}

		try
		{
			ObjectOutputStream os = new ObjectOutputStream(cliSock.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(cliSock.getInputStream());
			os.writeObject(new String("Demanding Image List"));//notify server of incoming request for image list
			os.flush();
			os.writeObject(city);//send city name
			os.flush();
			ImageDetails [] list = (ImageDetails [])(is.readObject());//get image details list from server
			cliSock.close();
			return list;
		}
		catch(Exception e)
		{
			Log.v("ERROR",e.toString());
		}
		return null;
	}

	public static String[] getCityList() {//get list of all possible cities
		try
		{
			cliSock=new Socket(serverIP,8070);
		}
		catch(Exception e)
		{
			Log.v("EXCEPTION",e.toString());
		}

		try
		{
			ObjectOutputStream os = new ObjectOutputStream(cliSock.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(cliSock.getInputStream());
			os.writeObject(new String("Demanding Cities"));//notify server of incoming city list request
			os.flush();
			String [] list = (String [])(is.readObject());//get list of cities as string array from server
			cliSock.close();
			return list;
		}
		catch(Exception e)
		{
			Log.v("ERROR",e.toString());
		}
		return null;
	}


}