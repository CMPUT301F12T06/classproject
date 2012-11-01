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
 **/
package com.cmput301.classproject.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Application;

// Singleton
public class JSONServer {

	public static enum Code {
		FAILURE, SUCCESS
	}

	private Application appRef = null;
	private static JSONServer instance = null;

	private FakeJSONServer fakeServer; // TODO REMOVE

	private JSONServer() {

		fakeServer = new FakeJSONServer(); // TODO REMOVE
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
	 * Name: isConnected Description: this will check the connection to the json
	 * server
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return fakeServer.isConnected(); // TODO REMOVE

	}

	/**
	 * Name: addTask Description: this will add the task in JSON format to the
	 * server
	 */
	public Code addTask(Task task) {
		// TODO update task id from server response
		return fakeServer.addTask(task); // TODO REMOVE
	}

	/**
	 * Name: getTask Description: This will retrieve all tasks in JSON form
	 * convert it to a "Task" list and return it It is null if something went
	 * wrong.
	 * 
	 * @return
	 */
	public List<Task> getAllTasks() {

		return fakeServer.getAllTasks();// TODO REMOVE

	}

	/**
	 * Name: This syncs our local tasks to the most recent server version
	 * 
	 * @return
	 */
	public Code sync() {
		return fakeServer.sync();
	}

	public Code addSubmission(int taskId, Submission submission) {
		// get latest task via taskId
		// add submission to task
		// run updateTask on our task
		return fakeServer.addSubmission(taskId, submission); // TODO REMOVE
	}

}
