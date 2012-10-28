package com.cmput301.classproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Name: 		handlePublicTasks
     * Description: This will called when the Public Tasks button
     * 				is clicked on the Main UI window
     * 
     * 				This should query the server and retrieve all
     * 				public tasks and list them in the ListView
     * 				of the Main UI window
     * @param v
     */
    public void handlePublicTasks(View v) {
    	
    	//TODO - Implement handlePublicTasks
    	
    }
    
    /**
     * Name: 		handleYourTasks
     * Description: This will called when the Your Tasks button
     * 				is clicked on the Main UI window
     * 
     * 				This should query the server and return and list
     * 				the results on the ListView of the Main UI window
     * 				of the owners specifically posted tasks
     * @param v
     */
    public void handleYourTasks(View v) {
    	//TODO - Implement handleYourTasks
    }
    
    /**
     * Name: 		handleSpecificTasks
     * Description: This will called when the Specific button
     * 				is clicked on the Main UI window
     * 
     * 				This will prompt the user using a dialog to
     * 				enter the name of the user they wish to view
     * 				tasks from. Upon inputting a VALID user, the
     * 				list of users will be retrieved from the server
     * 				and listed in the ListView of the Main UI activity.
     * 
     * 				If it is an INVALID user, we will display an error
     * 				to the user and show an empty list to the user. 
     * @param v
     */
    public void handleSpecificTasks(View v) {
    	
    }
    
    /**
     * Name: 		onSyncHandler
     * Description: The user will manually sync their content
     * 				with the content on the server to allow
     * 				for offline viewing
     * @param v
     */
    public void onSyncHandler(View v) {
    	
    }
    
    
    /**
     * Name: 		addTaskHandler
     * Description: This will redirect the user to the
     *				Add Task activity
     * @param v
     */
    public void addTaskHandler(View v) {
    	Intent intent = new Intent(this, AddTaskActivity.class);
    	startActivity(intent);
    }
    
}
