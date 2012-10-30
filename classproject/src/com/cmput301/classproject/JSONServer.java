package com.cmput301.classproject;

import java.util.List;

import android.app.Application;

// Singleton
public class JSONServer {
	
	public static enum Code {
	    FAILURE, SUCCESS
	}
	
	private Application appRef = null;
	private static JSONServer instance = null;

	private JSONServer() {
	}

	public static JSONServer getInstance() {
		if (instance == null) {
			instance = new JSONServer();
		}
		return instance;
	}

	public void setApplicatonReference(Application appRef) {
		this.appRef = appRef;
	}
	
	/**
	 * Name: 		isConnected
	 * Description:	this will check the connection to the json server
	 * @return
	 */
	public boolean isConnected() {
		return false;
		
	}
	
	/**
	 * Name: 		addTask
	 * Description:	this will add the task in JSON
	 * 				format to the server
	 */
	public Code addTask(Task task) {
		//TODO update task id from server response
		return Code.SUCCESS;
	}
	
	/**
	 * Name: 		getTask
	 * Description:	This will retrieve all tasks in JSON form
	 * 				convert it to a "Task" list and return it
	 * 				It is null if something went wrong.
	 * @return
	 */
	public List<Task> getAllTasks() {
		
		return null;
		
	}
	
	/**
	 * Name: 		updateTask
	 * Description:	This will update the task with the provided
	 * 				Task object information
	 */
	public Code updateTask(Task t) {
	
		return Code.SUCCESS;
	}
	
	public Code addSubmission(int taskId, Submission submission) {
		// get latest task via taskId
		// add submission to task
		// run updateTask on our task 
		return Code.SUCCESS;
	}

}
