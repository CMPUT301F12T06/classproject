package com.cmput301.classproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.cmput301.classproject.JSONServer.Code;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

//Singleton object - We extend application so we can use the
//ApplicationContext and treat this like a singleton.
// Each additional singleton used in this project has a reference
// to this application object so we can do operations like local file
// storage amoung other singletons.
public class TaskManager extends Observable {

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
		
		//TODO add creator field to task created using the phone serial
		
		//TODO add connection logic and locale file storage stuff logic
		Code returnCode = JSONServer.getInstance().addTask(task);
		if(returnCode == Code.SUCCESS){
			this.notifyAllObservers(JSONServer.getInstance().getAllTasks());
		}		
		return returnCode;
	}
	
	public Code sync(){
		//TODO add connection logic and locale file storage stuff logic
		Code returnCode = JSONServer.getInstance().sync();
		if(returnCode == Code.SUCCESS){
			this.notifyAllObservers(JSONServer.getInstance().getAllTasks());
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
