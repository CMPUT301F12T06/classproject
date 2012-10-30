package com.cmput301.classproject;

import android.app.Application;

//Singleton object - We extend application so we can use the
//ApplicationContext and treat this like a singleton.
// Each additional singleton used in this project has a reference
// to this application object so we can do operations like local file
// storage amoung other singletons.
public class TaskManager extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		// pass our single Application instance to other singleton classes 
		JSONServer.getInstance().setApplicatonReference(this);
		LocalStorage.getInstance().setApplicatonReference(this);
	}

}
