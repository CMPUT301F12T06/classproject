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
import java.util.concurrent.ExecutionException;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cmput301.classproject.Model.Tasks.AddSubmission;
import com.cmput301.classproject.Model.Tasks.CheckConnection;
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
	
	public static ArrayList<Task> cachedTasks = null;
	public static String currentTask = null;

	private TaskManager() {
		observers = new ArrayList<Observer>();
		if (cachedTasks == null)
			cachedTasks = new ArrayList<Task>();
	}

	/**
	 * Adds an observer to our list of Observers so we can Notify them of any
	 * changes that are made
	 */
	public void addObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}

	/**
	 * Notifies all the observers of a change and passes in the ArrayList<Task>
	 * 
	 * @param data
	 *            the ArrayList<Task>
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
	 * @param task
	 *            The task to add
	 * @param mContext
	 *            The ApplicationContext in which it was called
	 * @return Code.SUCCESS or Code.FAILURE
	 */
	public Code addTask(Task task, Context mContext) {

		Code returnCode = Code.SUCCESS;
		// Have it add to the cached task and then sync it in the background if
		// it is connected
		cachedTasks.add(task);
		LocalStorage.getInstance().saveTasksFromStorage(cachedTasks);
		notifyAllObservers(cachedTasks);

		// Save it to the database in the background
		try {
			// Will be done in the background with no progress dialog
			boolean connected = false;
			connected = new CheckConnection(mContext).execute().get();
			if (connected) {
				new ModifyServerData(JSONServer.TaskType.AddTask, mContext)
						.execute(task);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnCode;
	}

	/**
	 * Adds a Submission to a task to the server. This will retrieve the
	 * specified task from it's id and add the submission to the task and update
	 * the task on the server with the new information
	 * 
	 * @param taskId
	 *            The id of the task
	 * @param submission
	 *            The Submission object to add
	 * @param mContext
	 *            The ApplicationContext in which it was called
	 * @return Code.SUCCESS or Code.FAILURE
	 */
	public Code addSubmission(String taskId, Submission submission,
			Context mContext) {

		// Add the submission to the local cached copy
		for (int i = 0; i < cachedTasks.size(); i++) {
			if (cachedTasks.get(i).getId().equals(taskId)) {
				cachedTasks.get(i).getSubmissions().add(submission);
			}
		}
		LocalStorage.getInstance().saveTasksFromStorage(cachedTasks);

		Code returnCode = Code.SUCCESS;
		notifyAllObservers(cachedTasks);

		// Save it in the background to the server
		try {
			boolean connected = false;
			connected = new CheckConnection(mContext).execute().get();
			if (connected) {
				// Will be done in background with no dialog. It will always
				// load cached
				// For speed
				new AddSubmission(mContext).execute(new SubmissionData(taskId,
						submission));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnCode;
	}

	/**
	 * This basically retrieves all the tasks from the server and notifies The
	 * observers that a change has happened
	 * 
	 * @param mContext
	 *            The ApplicationContext in which it was called
	 * @return Code.SUCCESS or Code.FAILURE
	 */
	public Code sync(Context mContext) {
		// TODO add connection logic and locale file storage stuff logic
		// Will attempt to load from local storage first
		Code returnCode = Code.FAILURE;
		cachedTasks = (ArrayList<Task>) LocalStorage.getInstance()
				.loadTasksFromStorage();
		if ((cachedTasks != null))
			notifyAllObservers(cachedTasks);

		// Sync in the background
		try {
			new CheckConnection(mContext).execute();
		} catch (Exception ex) {

		}

		return returnCode;
	}

	/**
	 * Singleton method, returns the task manager if it exists, otherwise it creates it
	 * @return The TaskManager instance
	 */
	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}
	
	/**
	 * @return whether the device is connected to the Internet
	 */
	public boolean isConnected(Context context){
		ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if(conManager == null)
			return false;
		
		NetworkInfo network = conManager.getActiveNetworkInfo();
		
		if(network == null)
			return false;
		
		return network.isConnected();
	}

	/**
	 * 
	 * @param appRef The application Reference to set on the task manager
	 */
	public void setApplicatonReference(Application appRef) {
		this.appRef = appRef;
	}

}
