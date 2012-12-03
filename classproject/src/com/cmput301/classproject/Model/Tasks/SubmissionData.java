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
 
This class is used by AddSubmission AsyncTask to add a submission to the server. It is
used to store the values given a JSON formatted string. Using this class makes it easier to
use Gson. 
 **/
package com.cmput301.classproject.Model.Tasks;

import com.cmput301.classproject.Model.Submission;

public class SubmissionData {
	private Submission submission;
	private String taskid;
	
	/**
	 * Creates a data submission object
	 * @param taskId The ID of the task for this submission
	 * @param submission to set for this data submission object
	 */
	public SubmissionData(String taskId, Submission submission) {
		this.submission = submission;
		this.taskid = taskId;
	}
	
	/**
	 * 
	 * @return The submission object
	 */
	public Submission getSubmission() {
		return submission;
	}
	
	/**
	 * 
	 * @param submission The submission object to set
	 */
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	
	/**
	 * 
	 * @return The task ID
	 */
	public String getTaskId() {
		return taskid;
	}
	
	/**
	 * 
	 * @param taskid Sets the task ID
	 */
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
}
