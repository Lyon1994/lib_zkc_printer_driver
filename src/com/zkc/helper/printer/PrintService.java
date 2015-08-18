package com.zkc.helper.printer;

import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Print Service for image,GBK text,Unicode text
 * @author xuxl
 * @DateTime 2014-10-07 18:09:17
 */
public class PrintService{

	/**
	 * Image Width<br/>
	 * 58mm paper please set imageWidth=48<br/>
	 * 80mm paper please set imageWidth=72
	 */
	int imageWidth=48;
	
	/**
	 * change the text to gbk byte array
	 * @param textStr String text
	 * @return gbk byte array
	 */
	public byte[] getText(String textStr) {
		// TODO Auto-generated method stubbyte[] send;
		byte[] send=null;
		try {
			send = textStr.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			send = textStr.getBytes();
		}
		return send;
	}

	/**
	 * change the bitmap to byte array
	 * @param bitmap
	 * @return byte array
	 */
	public byte[] getImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		bitmap=resizeImage(bitmap, imageWidth * 8, mHeight);

		mWidth = bitmap.getWidth();
		mHeight = bitmap.getHeight();
		int[] mIntArray = new int[mWidth * mHeight];
		bitmap.getPixels(mIntArray, 0, mWidth, 0, 0, mWidth, mHeight);
		byte[]  bt =PrinterLib.getBitmapData(mIntArray, mWidth, mHeight);
		bitmap.recycle();
		return bt;
	}

	/**
	 * change the text to unicode byte array
	 * @param textStr String text
	 * @return unicode byte array
	 */
	public byte[] getTextUnicode(String textStr) {
		// TODO Auto-generated method stub
		byte[] send=string2Unicode(textStr);
		return send;
	}
	
	/**
	 * resize the bitmap
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();

		if(width>w)
		{
			float scaleWidth = ((float) w) / width;
			float scaleHeight = ((float) h) / height;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleWidth);
			Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
					height, matrix, true);
			return resizedBitmap;
		}else{
			Bitmap resizedBitmap = Bitmap.createBitmap(w, height, Config.RGB_565);
			Canvas canvas = new Canvas(resizedBitmap);
			Paint paint = new Paint();
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(bitmap, (w-width)/2, 0, paint);
			return resizedBitmap;
		}
	}
	
	@SuppressWarnings("unused")
	private static byte[] string2Unicode(String s) {   
	    try {   
	      StringBuffer out = new StringBuffer("");   
	      byte[] bytes = s.getBytes("unicode");   
	    	byte[] bt=new byte[bytes.length-2];
	      for (int i = 2,j=0; i < bytes.length - 1; i += 2,j += 2) {   
	    	  bt[j]= (byte)(bytes[i + 1] & 0xff);   
	    	  bt[j+1] = (byte)(bytes[i] & 0xff);    
	      }   
	      return bt;   
	    }   
	    catch (Exception e) {   
	      try {
			byte[] bt=s.getBytes("GBK");
			return bt;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	    }   
	  }
}
