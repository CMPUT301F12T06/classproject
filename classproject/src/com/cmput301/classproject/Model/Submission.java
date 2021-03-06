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

import android.graphics.Bitmap;

/**
 * Each time a task is fulfilled a Submission object is created to present the
 * fulfilled task entry
 */
public class Submission implements Serializable {

	public static enum Permission {
		Private, Public, Creator
	}

	private static final long serialVersionUID = -3396630733841446186L;
	private String author;
	private String summary;
	private String textSubmission;
	private Permission access;
	private long timestamp;

	/**
	 * Our picture class is a bitmap wrapper, we use this internally for storing
	 * and loading images for any given submission.
	 */
	private ArrayList<Picture> images = new ArrayList<Picture>();
	// TODO add audio support

	public static int ACCESS_PHOTO = (1 << 0);
	public static int ACCESS_AUDIO = (1 << 1);
	public static int ACCESS_TEXT = (1 << 2);

	/**
	 * Creates a submission object for a task. This constructor should be used
	 * in the Submission Add Activity and in Test Cases.
	 * 
	 * @param summary
	 *            The summary text
	 * @param author
	 *            Author of the submission
	 * @param textSubmission
	 *            Submitted text for the submission
	 * @param bitmaps
	 *            Submitted photos for the submission
	 * @param access
	 *            Public, Private, or Creator submission sharing
	 * @param timestamp
	 *            Timestamp for when the submission is created
	 */
	public Submission(String summary, String author, String textSubmission,
			ArrayList<Bitmap> bitmaps, Permission access, long timestamp) {
		this.summary = summary;
		this.author = author;
		this.access = access;
		this.timestamp = timestamp;

		if (textSubmission != null)
			this.textSubmission = textSubmission;
		else
			this.textSubmission = "N/A";

		if (bitmaps != null && bitmaps.size() > 0)
			this.setImages(bitmaps);

	}

	/**
	 * Sets the images for this submission.
	 * @param bitmaps The bitmaps containing the pictures for this submission
	 */
	private void setImages(ArrayList<Bitmap> bitmaps) {
		images.clear();
		for (Bitmap bitmap : bitmaps) {
			images.add(new Picture(bitmap));
		}
	}
	
	/**
	 * Returns a string representation of this submission
	 */
	public String toString(){
		return summary;
	}

	/**
	 * 
	 * @return who created this submission
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 
	 * @return Summary of this submission
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * This deserializes the pictures and turns them into an arraylist of bitmaps
	 * @return
	 */
	public ArrayList<Bitmap> getImages() {
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		// Converts it into the picture object
		for (Picture picture : images) {
			bitmaps.add(picture.getBitmap());
		}
		return bitmaps;
	}

	/**
	 * 
	 * @return The text submission for this submission
	 */
	public String getTextSubmission() {
		return textSubmission;
	}

	/**
	 * 
	 * @return The timestamp for when the submission was submitted
	 */
	public double getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @return The permission object of this submission
	 */
	public Permission getAccess() {
		return access;
	}
}
