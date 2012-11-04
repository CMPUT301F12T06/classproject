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
