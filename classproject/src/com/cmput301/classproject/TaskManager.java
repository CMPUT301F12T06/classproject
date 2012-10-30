package com.cmput301.classproject;

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
public class TaskManager extends Application {

	private Application appRef = null;
	private static TaskManager instance = null;

	private TaskManager() {
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
