package com.cmput301.classproject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements Observer {

	ListView mainTaskListView;
	ArrayAdapter<Task> taskViewAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final MainActivity selfRef = this;

		mainTaskListView = (ListView) findViewById(R.id.main_task_list_view);
		mainTaskListView
				.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		mainTaskListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mainTaskListView.setStackFromBottom(false);
		mainTaskListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Task t = (Task)mainTaskListView.getItemAtPosition(position);
				if(t != null){
					Intent intent = new Intent(selfRef, ViewTaskActivity.class);
					intent.putExtra("Task",t);
					startActivity(intent);
				}
				
			}}
		);
		
		
		taskViewAdapter = new ArrayAdapter<Task>(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, new ArrayList<Task>());
		taskViewAdapter.setNotifyOnChange(true);
		mainTaskListView.setAdapter(taskViewAdapter);
		
		// MVC model attach this view to our data model
		TaskManager.getInstance().addObserver(this);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Name: handlePublicTasks Description: This will called when the Public
	 * Tasks button is clicked on the Main UI window
	 * 
	 * This should query the server and retrieve all public tasks and list them
	 * in the ListView of the Main UI window
	 * 
	 * @param v
	 */
	public void handlePublicTasks(View v) {

		// TODO - Implement handlePublicTasks

	}

	/**
	 * Name: handleYourTasks Description: This will called when the Your Tasks
	 * button is clicked on the Main UI window
	 * 
	 * This should query the server and return and list the results on the
	 * ListView of the Main UI window of the owners specifically posted tasks
	 * 
	 * @param v
	 */
	public void handleYourTasks(View v) {
		// TODO - Implement handleYourTasks
	}

	/**
	 * Name: handleSpecificTasks Description: This will called when the Specific
	 * button is clicked on the Main UI window
	 * 
	 * This will prompt the user using a dialog to enter the name of the user
	 * they wish to view tasks from. Upon inputting a VALID user, the list of
	 * users will be retrieved from the server and listed in the ListView of the
	 * Main UI activity.
	 * 
	 * If it is an INVALID user, we will display an error to the user and show
	 * an empty list to the user.
	 * 
	 * @param v
	 */
	public void handleSpecificTasks(View v) {

	}

	/**
	 * Name: onSyncHandler Description: The user will manually sync their
	 * content with the content on the server to allow for offline viewing
	 * 
	 * @param v
	 */
	public void onSyncHandler(View v) {
		TaskManager.getInstance().sync();
	}

	/**
	 * Name: addTaskHandler Description: This will redirect the user to the Add
	 * Task activity
	 * 
	 * @param v
	 */
	public void addTaskHandler(View v) {
		Intent intent = new Intent(this, AddTaskActivity.class);
		startActivity(intent);
	}
	
	
	//MVC model this is updated when the data model (taskmanager) changes.
	@SuppressWarnings("unchecked")
	public void update(Observable observable, Object data) {
		taskViewAdapter.clear();
		if(data != null)
			taskViewAdapter.addAll((ArrayList<Task>)data);	
	}

}
