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

Submission.java contains all the submission data for a certain task. It consists of the author,
summary, an ArrayList<Picture>, text, access and a timestamp. Audio will be implemented in 
the last phase and is currently disabled.

 **/
package com.cmput301.classproject.Model;

import java.io.Serializable;
import java.util.ArrayList;

import com.cmput301.classproject.UI.AddSubmissionActivity.SubmissionPermission;

import android.graphics.Bitmap;

/**
 * Each time a task is fulfilled a Submission object is created to present the
 * fulfilled task entry
 */
public class Submission implements Serializable {

	private static final long serialVersionUID = -3396630733841446186L;
	private String author;
	private String summary;
	private ArrayList<Picture> images = new ArrayList<Picture>();
	// TODO add audio support
	private String textSubmission;
	private SubmissionPermission access;
	private double timestamp;

	public static int ACCESS_PHOTO = (1 << 0);
	public static int ACCESS_AUDIO = (1 << 1);
	public static int ACCESS_TEXT = (1 << 2);

	public Submission(String summary, String author, String textSubmission,
			ArrayList<Bitmap> bitmaps, SubmissionPermission access) {
		this.summary = summary;
		this.author = author;
		this.access = access;

		if (textSubmission != null)
			this.textSubmission = textSubmission;
		else
			this.textSubmission = "N/A";

		if (bitmaps != null && bitmaps.size() > 0)
			this.setImages(bitmaps);

	}

	private void setImages(ArrayList<Bitmap> bitmaps) {
		images.clear();
		for (Bitmap bitmap : bitmaps) {
			images.add(new Picture(bitmap));
		}
	}

	public String toString() {
		return summary;
	}

	public String getAuthor() {
		return author;
	}

	public String getSummary() {
		return summary;
	}

	public ArrayList<Bitmap> getImages() {
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		// Converts it into the picture object
		for (Picture picture : images) {
			bitmaps.add(picture.getBitmap());
		}
		return bitmaps;
	}

	public String getTextSubmission() {
		return textSubmission;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public SubmissionPermission getAccess() {
		return access;
	}
}
