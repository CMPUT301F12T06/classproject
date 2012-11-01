package com.cmput301.classproject.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6419317506750925474L;
	private int id;
	private ArrayList<Submission> submissions;
	private String name;
	private String creator;
	private String description;
	private int requires; // logical OR based settings
	private boolean publicAccess; // if false then this is a private task

	public Task(String name, String description, String creator, int requires,
			boolean publicAccess) {
		this.creator = creator;
		this.name = name;
		this.description = description;
		this.name = name;
		this.requires = requires;
		this.publicAccess = publicAccess;
		this.submissions = new ArrayList<Submission>();
	}

	public String toString() {
		return "name/" + this.name + "\ndescription/" + description
				+ "\ncreator/" + creator + "\nid/" + id + "\nrequires/"
				+ Integer.toString(requires) + "\naccess/"
				+ Boolean.toString(publicAccess) + "\n";
	}

	// TODO this should be updated by JSON server class only
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getCreator() {
		return creator;
	}

	public ArrayList<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(ArrayList<Submission> submissions) {
		this.submissions = submissions;
	}

	public void addSubmission(Submission submission) {
		if (this.submissions != null)
			this.submissions.add(submission);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getRequires() {
		return requires;
	}

	public boolean isPublicAccess() {
		return publicAccess;
	}

}