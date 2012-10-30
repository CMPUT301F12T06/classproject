package com.cmput301.classproject;

import android.provider.MediaStore;

public class Submission {

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

	public Submission() {
		// TODO - Implement
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
