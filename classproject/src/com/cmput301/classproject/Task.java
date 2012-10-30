package com.cmput301.classproject;

import java.util.ArrayList;

public class Task {
	private int id;
	private ArrayList<Submission> submissions;
	private String name;
	private String description;
	private int requires; // logical OR based settings
	private boolean publicAccess; // if false then this is a private task

	public Task(String name, String description, int requires,
			boolean publicAccess) {
		this.id = 0;
		this.name = name;
		this.description = description;
		this.name = name;
		this.requires = requires;
		this.publicAccess = publicAccess;
		this.submissions = new ArrayList<Submission>();
	}

	// TODO this should be updated by JSON server class only
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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
