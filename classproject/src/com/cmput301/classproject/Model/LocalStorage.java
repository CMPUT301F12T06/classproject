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

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import android.app.Application;
import android.content.Context;

/**
 * Singleton - Used to save and load Tasks on locale disk for offline access
 */
public class LocalStorage {

	private static final String USERNAME_STORAGE_FILE = "UsernameStorage";
	private static final String TASK_STORAGE_FILE = "TaskStorage";

	private Application appRef = null;
	private static LocalStorage instance = null;

	private LocalStorage() {
	}

	/**
	 * This object is singleton
	 * 
	 * @return The LocalStorage singleton instance
	 */
	public static LocalStorage getInstance() {
		if (instance == null) {
			instance = new LocalStorage();
		}
		return instance;
	}

	public void setApplicatonReference(Application appRef) {
		this.appRef = appRef;
	}

	/**
	 * This will retrieve the username from Local Storage
	 * 
	 * @return A list of Tasks
	 */
	public String loadUsernameFromStorage() {
		return (String) load(USERNAME_STORAGE_FILE);
	}

	/**
	 * This will retrieve the logEntries array to a file for Local Storage
	 * 
	 * @return A list of Tasks
	 */
	@SuppressWarnings("unchecked")
	public List<Task> loadTasksFromStorage() {
		Object data = load(TASK_STORAGE_FILE);
		return (List<Task>) data;
	}

	/**
	 * This method is used to load an object to a file
	 * 
	 * @param filename
	 *            - the filename to load the object from
	 * @return List<Task> - the serialized file that was loaded
	 */
	@SuppressWarnings({ "unchecked" })
	private Object load(String filename) {
		Object output = null;
		try {
			ObjectInputStream in = new ObjectInputStream(
					appRef.openFileInput(filename));
			if (in != null)
				output = in.readObject();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			output = null;
		}
		return output;
	}

	/**
	 * This will save a username to Local Storage
	 * 
	 * @return A list of Tasks
	 */
	public void saveTasksFromStorage(String username) {
		save(USERNAME_STORAGE_FILE, username);
	}

	/**
	 * This will save the logEntries array to a file
	 * 
	 * @param tasks
	 *            The array of tasks
	 */
	public void saveTasksFromStorage(List<Task> tasks) {
		save(TASK_STORAGE_FILE, tasks);
	}

	/**
	 * This method is used to save an object to a file
	 * 
	 * @param filename
	 *            - the filename to save the object to
	 * @param obj
	 *            - the serialized file to be save
	 */
	private void save(String filename, Object obj) {
		try {
			ObjectOutput out = new ObjectOutputStream(appRef.openFileOutput(
					filename, Context.MODE_PRIVATE));
			out.writeObject(obj);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
