package com.cmput301.classproject;

import android.app.Application;

public class JSONServer extends Application {
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	
	/**
	 * Name: 		checkConnection
	 * Description:	this will check the connection to the server
	 * @return
	 */
	public boolean checkConnection() {
		return false;
		
	}
	
	/**
	 * Name: 		addTask
	 * Description:	this will add the task in JSON
	 * 				format to the server
	 */
	public void addTask() {
		
		
	}
	
	/**
	 * Name: 		getTask
	 * Description:	This will retrieve a task in JSON form
	 * 				convert it to a "Task" object and return it
	 * @return
	 */
	public Task getTask() {
		
		return null;
		
	}
	
	/**
	 * Name: 		updateTask
	 * Description:	This will update the task with the provided
	 * 				Task object information
	 */
	public void updateTask(Task t) {
		
	}
}
