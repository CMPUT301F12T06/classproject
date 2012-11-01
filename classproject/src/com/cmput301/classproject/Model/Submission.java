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
 **/
package com.cmput301.classproject.Model;

import java.io.Serializable;

import android.provider.MediaStore;

public class Submission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3396630733841446186L;
	private String author;
	private String summary;
	private MediaStore.Images images;
	private MediaStore.Audio audio;
	private String text;
	private Byte access;
	private double timestamp;

	public static int ACCESS_PHOTO = (1 << 0); 
	public static int ACCESS_AUDIO = (1 << 1); 
	public static int ACCESS_TEXT = (1 << 2); 

	public Submission(String summary) {
		this.summary = summary;
		// TODO - Implement
	}

	public String toString(){
		return summary; //TODO implement
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public MediaStore.Images getImages() {
		return images;
	}

	public void setImages(MediaStore.Images images) {
		this.images = images;
	}

	public MediaStore.Audio getAudio() {
		return audio;
	}

	public void setAudio(MediaStore.Audio audio) {
		this.audio = audio;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	public Byte getAccess() {
		return access;
	}

	public void setAccess(Byte access) {
		this.access = access;
	}

}
