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

	/**
	 * Returns a string representation of this task
	 */
	public String toString() {
		return "Task: " + this.name;
	}

	/** 
	 * @param taskId The taskid to set for this task
	 */
	// TODO this should be updated by JSON server class only
	public void setId(String taskId) {
		this.taskId = taskId;
	}
	/**
	* 
	* @return The id for the task
	*/
	public String getId() {
		return taskId;
	}

	/**
	 * 
	 * @return The creator for this task
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 
	 * @return arraylist of submission objects for this task
	 */
	public ArrayList<Submission> getSubmissions() {
		return submissions;
	}

	/**
	 * This will overwrite all the submission currently in this task
	 * @param submissions The list of submissions for this task
	 */
	public void setSubmissions(ArrayList<Submission> submissions) {
		this.submissions = submissions;
	}

	/**
	 * Adds a single submission for this task
	 * @param submission The submission to add
	 */
	public void addSubmission(Submission submission) {
		if (this.submissions != null)
			this.submissions.add(submission);
	}

	/**
	 * 
	 * @return The name for this task
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return The description for this task
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return What this kind of submission this task requires
	 */
	public int getRequires() {
		return requires;
	}

	/**
	 * 
	 * @return whether or not this task is publicly accessible
	 */
	public boolean isPublicAccess() {
		return publicAccess;
	}

}
