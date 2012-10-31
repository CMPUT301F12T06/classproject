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

	private FakeJSONServer fakeServer; //TODO REMOVE
	
	
	private JSONServer() {
		fakeServer = new FakeJSONServer(); //TODO REMOVE
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
		return fakeServer.isConnected(); //TODO REMOVE
		
	}
	
	/**
	 * Name: 		addTask
	 * Description:	this will add the task in JSON
	 * 				format to the server
	 */
	public Code addTask(Task task) {
		//TODO update task id from server response
		return fakeServer.addTask(task); //TODO REMOVE
	}
	
	/**
	 * Name: 		getTask
	 * Description:	This will retrieve all tasks in JSON form
	 * 				convert it to a "Task" list and return it
	 * 				It is null if something went wrong.
	 * @return
	 */
	public List<Task> getAllTasks() {
		
		return fakeServer.getAllTasks();//TODO REMOVE
		
	}
	
	public Code addSubmission(int taskId, Submission submission) {
		// get latest task via taskId
		// add submission to task
		// run updateTask on our task 
		return  fakeServer.addSubmission(taskId, submission); //TODO REMOVE
	}

}
