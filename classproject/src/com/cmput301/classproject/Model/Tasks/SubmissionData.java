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
	
	public SubmissionData(String taskId, Submission submission) {
		this.submission = submission;
		this.taskid = taskId;
	}
	
	public Submission getSubmission() {
		return submission;
	}
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	
	public String getTaskId() {
		return taskid;
	}
	
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	
	
}
