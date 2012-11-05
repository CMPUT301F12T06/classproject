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
import java.util.ArrayList;

public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6419317506750925474L;
	private String taskId;
	private ArrayList<Submission> submissions;
	private String name;
	private String creator;
	private String description;
	private int requires; // logical OR based settings
	private boolean publicAccess; // if false then this is a private task

	public Task() {
		
	}
	public Task(String name, String description, String creator, int requires,
			boolean publicAccess) {
		this.taskId="-1";
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
				+ "\ncreator/" + creator + "\nid/" + this.taskId + "\nrequires/"
				+ Integer.toString(requires) + "\naccess/"
				+ Boolean.toString(publicAccess) + "\n";
	}

	// TODO this should be updated by JSON server class only
	public void setId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return taskId;
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
