package com.cmput301.classproject;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;


//Singleton object - We extend application so we can use the
//ApplicationContext and treat this like a singleton
//remember to add this to the android manifest 
//<application name="com.cmput301.classproject.TaskManager">
//I HAVE ADDED IT TO THE MANIFEST
public class TaskManager extends Application {
	
	private ArrayList<Task> list = new ArrayList<Task>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}

}
