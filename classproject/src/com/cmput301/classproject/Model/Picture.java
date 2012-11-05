/**
License: GPL 2.0

Copyright (C) 2012 Benson Trinh, Thomas Polasek, Remco Uittenbogerd, Clinton Wong

This program is free software; you can redistribute it and/or modify it under the 
terms of the GNU General Public License as published by the Free Software Foundation;

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; 
if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
MA 02110-1301, USA.

Picture.java allows us to properly serialize and de-serialize the picture object. Using the
default Bitmap will serialize properly but upon de-serialization it errors.
 **/
package com.cmput301.classproject.Model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Used to seriailize/deserialize bitmap images found in Task Submissions
 */
public class Picture implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1776458544071063784L;
	private byte[] data;

	/**
	 * Create a picture by taking a Bitmap and Serializing the data into a
	 * ByteArray
	 * 
	 * @param bitmap
	 *            The Bitmap
	 */
	public Picture(Bitmap bitmap) {
		double aspectRatio = bitmap.getHeight() / bitmap.getWidth();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 400,
				(int) (400 * aspectRatio), true);

		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();

		scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 20,
				byteArrayBitmapStream);
		data = byteArrayBitmapStream.toByteArray();
	}

	/**
	 * Retrieves the Bitmap from a byte array.
	 * 
	 * @return the Bitmap object
	 */
	public Bitmap getBitmap() {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
}
