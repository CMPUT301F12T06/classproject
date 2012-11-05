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

Task.java contains all the information about a task. This includes the taskId, an 
ArrayList<Submission>, name, creator, description, requirements to fullfilment, and
whether it is public or local
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

	/**
	 * Creates a task object. This constructor should be used in the Add Task
	 * Activity and in Test Cases.
	 * 
	 * @param name
	 *            The name of the task
	 * @param description
	 *            Contains detailed information about the task
	 * @param creator
	 *            Author of the task
	 * @param requires
	 *            Contains our list of task requirements (photo,audio,text) in
	 *            ORed format
	 * @param publicAccess
	 *            Public means the task is shared, Private means the task is
	 *            only for the creator
	 */
	public Task(String name, String description, String creator, int requires,
			boolean publicAccess) {
		this.taskId = "-1";
		this.creator = creator;
		this.name = name;
		this.description = description;
		this.name = name;
		this.requires = requires;
		this.publicAccess = publicAccess;
		this.submissions = new ArrayList<Submission>();
	}

	/**
	 * Used for creating a blank Task for the JSON server The JSON server must
	 * send a task object to get the Task Id.
	 */
	public Task() {
	}

	public String toString() {
		return "Task: " + this.name;
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
