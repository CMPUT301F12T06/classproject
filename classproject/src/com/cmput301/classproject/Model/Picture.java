package com.cmput301.classproject.Model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Picture implements Serializable{

	private byte[] data;
	public Picture(Bitmap bitmap){
		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
		data = byteArrayBitmapStream.toByteArray();
	}
	
	public Bitmap getBitmap(){
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
}
