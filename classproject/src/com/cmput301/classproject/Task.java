package com.cmput301.classproject;

import java.util.ArrayList;

public class Task {
	private int id;
	private ArrayList<Submission> submissions;
	private String name;
	private String description;
	private Byte requires;
	private int access;
	
	public Task() {
		//TODO - implement Constructor
	}
	
	public int getId() {
		return id;
	}
	
	//TODO this should be updated by JSON server class only
	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<Submission> getSubmissions() {
		return submissions;
	}
	
	public void setSubmissions(ArrayList<Submission> submissions) {
		this.submissions = submissions;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Byte getRequires() {
		return requires;
	}
	
	public void setRequires(Byte requires) {
		this.requires = requires;
	}
	
	public int getAccess() {
		return access;
	}
	
	public void setAccess(int access) {
		this.access = access;
	}
	
	
}
