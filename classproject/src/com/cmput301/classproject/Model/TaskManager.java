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


Singleton object - We extend application so we can use the
ApplicationContext and treat this like a singleton.
Each additional singleton used in this project has a reference
to this application object so we can do operations like local file
storage among other singletons.

TaskManager is reponsible for any task related things and uses the
JSONServer intensively. This includes, syncing with the server, adding
a new task to the server, adding a submission updating a task
and removing a task. 

@author Benson Trinh
 **/
package com.cmput301.classproject.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Application;
import android.content.Context;

import com.cmput301.classproject.Model.Tasks.AddSubmission;
import com.cmput301.classproject.Model.Tasks.JSONServer;
import com.cmput301.classproject.Model.Tasks.JSONServer.Code;
import com.cmput301.classproject.Model.Tasks.ModifyServerData;
import com.cmput301.classproject.Model.Tasks.ReceiveServerData;
import com.cmput301.classproject.Model.Tasks.SubmissionData;

//
public class TaskManager extends Observable {
	
	@SuppressWarnings("unused")
	private Application appRef = null;
	private static TaskManager instance = null;
	private ArrayList<Observer> observers = null;
	
	public static ArrayList<Task> cachedTasks = new ArrayList<Task>();
	public static String currentTask = null;
	
	private TaskManager() {
		observers = new ArrayList<Observer>();
	}

	/**
	 * Adds an observer to our list of Observers so we can
	 * Notify them of any changes that are made 
	 */
	public void addObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}

	/**
	 * Notifies all the observers of a change and passes
	 * in the ArrayList<Task>
	 * 
	 * @param data the ArrayList<Task>
	 */
	public void notifyAllObservers(Object data) {

		for (Observer observer : observers) {
			observer.update(this, data);
		}
	}
	
	
	/**
	 * Adds a task to the JSONServer. This calls the AsyncTask method
	 * ModifyServerData. 
	 * 
	 * @param task		The task to add
	 * @param mContext	The ApplicationContext in which it was called
	 * @return			Code.SUCCESS or Code.FAILURE
	 */
	public Code addTask(Task task, Context mContext) {

		Code returnCode = Code.SUCCESS;
		try {
			new ModifyServerData(JSONServer.TaskType.AddTask,mContext).execute(task);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnCode;
	}
	
	/**
	 * Adds a Submission to a task to the server. This will retrieve
	 * the specified task from it's id and add the submission to the 
	 * task and update the task on the server with the new information
	 * 
	 * @param taskId		The id of the task 
	 * @param submission	The Submission object to add
	 * @param mContext		The ApplicationContext in which it was called
	 * @return				Code.SUCCESS or Code.FAILURE
	 */
	public Code addSubmission(String taskId, Submission submission, Context mContext){
		
		Code returnCode = Code.SUCCESS;
		try {
			new AddSubmission(mContext).execute(new SubmissionData(taskId,submission));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnCode;
	}
	
	
	/**
	 * This basically retrieves all the tasks from the server and notifies
	 * The observers that a change has happened
	 * 
	 * @param mContext	The ApplicationContext in which it was called
	 * @return			Code.SUCCESS or Code.FAILURE
	 */
	public Code sync(Context mContext) {
		// TODO add connection logic and locale file storage stuff logic
		Code returnCode = JSONServer.getInstance().sync();
		if (returnCode == Code.SUCCESS) {
			new ReceiveServerData(JSONServer.TaskType.GetTasks,mContext).execute();
		}

		return returnCode;
	}

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}

	public void setApplicatonReference(Application appRef) {
		this.appRef = appRef;
	}

}
