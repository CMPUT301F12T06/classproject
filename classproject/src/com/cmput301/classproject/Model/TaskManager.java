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
import java.util.Observable;
import java.util.Observer;
import android.app.Application;
import com.cmput301.classproject.Model.JSONServer.Code;

//Singleton object - We extend application so we can use the
//ApplicationContext and treat this like a singleton.
// Each additional singleton used in this project has a reference
// to this application object so we can do operations like local file
// storage amoung other singletons.
public class TaskManager extends Observable {

	@SuppressWarnings("unused")
	private Application appRef = null;
	private static TaskManager instance = null;
	private ArrayList<Observer> observers = null;

	private TaskManager() {
		observers = new ArrayList<Observer>();
	}

	// MVC model any view that uses the JSONServer data
	public void addObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}

	// Notify any views attached that our data model was updated.
	private void notifyAllObservers(Object data) {

		for (Observer observer : observers) {
			observer.update(this, data);
		}
	}

	public Code addTask(Task task) {

		// TODO add creator field to task created using the phone serial

		// TODO add connection logic and locale file storage stuff logic
		Code returnCode = JSONServer.getInstance().addTask(task);
		if (returnCode == Code.SUCCESS) {
			this.notifyAllObservers(JSONServer.getInstance().getAllTasks()); // updated
																				// our
																				// views
		}
		return returnCode;
	}

	public Code sync() {
		// TODO add connection logic and locale file storage stuff logic
		Code returnCode = JSONServer.getInstance().sync();
		if (returnCode == Code.SUCCESS) {
			this.notifyAllObservers(JSONServer.getInstance().getAllTasks()); // updated
																				// our
																				// views
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
