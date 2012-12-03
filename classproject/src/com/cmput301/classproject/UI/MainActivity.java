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
package com.cmput301.classproject.UI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.LocalStorage;
import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * MainActivity - Displays a list of tasks and allows the user to filter task
 * lists by criteria.
 * 
 * @author Thomas Polasek
 * 
 */
public class MainActivity extends Activity implements Observer {

	/**
	 * This class is a an ArrayWrapper. It is used by the MainActivity to filter
	 * all tasks based on a specific criteria. All 4 buttons on the MainActivity
	 * interact with the class.
	 * 
	 * @author Thomas Polasek
	 * 
	 */
	public class FilteredTaskAdapter extends ArrayAdapter<Task> {

		Collection<? extends Task> buffer = null;

		public FilteredTaskAdapter(Context context, int resource,
				int textViewResourceId, List<Task> objects) {
			super(context, resource, textViewResourceId, objects);
			buffer = objects; // TODO use data proxy
		}

		public void addAllTask(Collection<? extends Task> collection) {
			buffer = collection;
		}

		/**
		 * ArrayAdapter .add method wrapper. Used to add our task based on a
		 * specific criteria.
		 * 
		 * @param object
		 * @return
		 */
		private boolean addTask(Task object) {

			if (object.isPublicAccess()) {
				this.add(object);
				return true;
			} else if (username != null && object.getCreator().equals(username)) {
				this.add(object);
				return true;
			}
			return false;
		}

		/**
		 * Filter constraint provided by a FilterType
		 * 
		 */
		public void filterByConstraint() {
			switch (filterConstraint) {
			case NAME:
				taskViewAdapter.filterAndLoadByName(filterName);
				break;
			case RANDOM:
				taskViewAdapter.filterAndLoadRandom();
				break;
			case PUBLIC:
				taskViewAdapter.loadAll();
				break;
			}
		}

		/**
		 * Filter by a specific name
		 * 
		 * @param name
		 */
		public void filterAndLoadByName(String name) {
			this.clear();

			if (name == null)
				return;

			for (Task t : buffer) {
				if (t.getCreator().toLowerCase().equals(name.toLowerCase())) {
					this.addTask(t);
				}
			}
		}

		/**
		 * Filter by public criteria
		 * 
		 */
		public void loadAll() {
			this.clear();
			for (Task t : buffer) {
				this.addTask(t);
			}
		}

		/**
		 * Filter by random criteria
		 * 
		 */
		public void filterAndLoadRandom() {
			this.clear();

			if (buffer.size() == 0)
				return;

			Random r = new Random();
			for (int i = 0; i < 200; i++) {
				for (Task t : buffer) {
					if (r.nextInt() % 13 == 3) {
						if (this.addTask(t))
							return;
					}
				}
			}

		}
	}

	/**
	 * Enum used to filter by a criteria
	 * 
	 * @author Thomas Polasek
	 * 
	 */
	private static enum FilterType {
		PUBLIC, RANDOM, NAME
	};

	ListView mainTaskListView;
	FilteredTaskAdapter taskViewAdapter;
	FilterType filterConstraint = FilterType.PUBLIC;
	String filterName = null;
	String username = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final MainActivity selfRef = this;

		username = LocalStorage.getInstance().loadUsernameFromStorage();
		if (username == null) {
			getUsernameDialog(selfRef);
			username = LocalStorage.getInstance().loadUsernameFromStorage();
		}
		if (username != null) {
			selfRef.setTitle("Logged in as: " + username);
		}

		mainTaskListView = (ListView) findViewById(R.id.main_task_list_view);
		mainTaskListView
				.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		mainTaskListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mainTaskListView.setStackFromBottom(false);
		mainTaskListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Task t = (Task) mainTaskListView.getItemAtPosition(position);
				if (t != null) {
					Intent intent = new Intent(selfRef, ViewTaskActivity.class);
					intent.putExtra("Task", t);
					startActivity(intent);
				}
			}
		});

		taskViewAdapter = new FilteredTaskAdapter(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, new ArrayList<Task>());
		taskViewAdapter.setNotifyOnChange(true);
		mainTaskListView.setAdapter(taskViewAdapter);

		// MVC model attach this view to our data model
		TaskManager.getInstance().addObserver(this);
		TaskManager.getInstance().sync(this);
	}

	/**
	 * Dialog that appears once for the installed lifetime of the program.
	 * Requests the user for a username that is used in add tasks and
	 * submissions.
	 * 
	 */
	public void getUsernameDialog(final MainActivity selfRef) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false);
		alert.setTitle("Username");
		alert.setMessage("Enter your username: (this is only set once)");

		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				Random r = new Random();
				if (value == null || value.length() < 1) {
					value = "rand" + String.valueOf(r.nextInt() % 1000);
				}
				username = value;
				selfRef.setTitle("Logged in as: " + value);
				LocalStorage.getInstance().saveTasksFromStorage(value);
				taskViewAdapter.filterByConstraint();
			}
		});
		alert.show();
	}

	/**
	 * Dialog for requesting the user for a username to filter tasks by.
	 * 
	 * @param selfRef
	 */
	public void getCreatorFilter(final MainActivity selfRef) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false);
		alert.setTitle("Filter by creator");
		alert.setMessage("Enter the task creator you wish to filter tasks by:");

		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				if (value == null || value.length() < 1) {
					value = "";
				}
				filterName = value;
				filterConstraint = FilterType.NAME;
				taskViewAdapter.filterAndLoadByName(filterName);
			}
		});
		alert.show();
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
		filterConstraint = FilterType.PUBLIC;
		taskViewAdapter.loadAll();

		((Button) findViewById(R.id.view_public_tasks_id))
				.setTextColor(Color.CYAN);
		((Button) findViewById(R.id.view_specific_creator_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_your_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_random_creator_tasks_id))
				.setTextColor(Color.WHITE);

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
		filterConstraint = FilterType.NAME;
		filterName = LocalStorage.getInstance().loadUsernameFromStorage();
		taskViewAdapter.filterAndLoadByName(filterName);

		((Button) findViewById(R.id.view_public_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_specific_creator_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_your_tasks_id))
				.setTextColor(Color.CYAN);
		((Button) findViewById(R.id.view_random_creator_tasks_id))
				.setTextColor(Color.WHITE);
	}

	/**
	 * Name: handleRandomTasks Description: This will called when the Random
	 * button is clicked on the Main UI window
	 * 
	 * Selects a set of 3 random tasks
	 * 
	 * @param v
	 */
	public void handleRandomTasks(View v) {
		filterConstraint = FilterType.RANDOM;
		taskViewAdapter.filterAndLoadRandom();

		((Button) findViewById(R.id.view_public_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_specific_creator_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_your_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_random_creator_tasks_id))
				.setTextColor(Color.CYAN);
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
		getCreatorFilter(this);
		filterConstraint = FilterType.NAME;
		taskViewAdapter.filterAndLoadByName(filterName);

		((Button) findViewById(R.id.view_public_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_specific_creator_tasks_id))
				.setTextColor(Color.CYAN);
		((Button) findViewById(R.id.view_your_tasks_id))
				.setTextColor(Color.WHITE);
		((Button) findViewById(R.id.view_random_creator_tasks_id))
				.setTextColor(Color.WHITE);
	}

	/**
	 * Name: onSyncHandler Description: The user will manually sync their
	 * content with the content on the server to allow for offline viewing
	 * 
	 * @param v
	 */
	public void onSyncHandler(View v) {
		TaskManager.getInstance().sync(this);
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

	/**
	 * Name: update Description: This is our view update. Model updates are sent
	 * to the view via this method.
	 */
	@SuppressWarnings("unchecked")
	public void update(Observable observable, Object data) {
		taskViewAdapter.clear();
		if (data != null) {
			taskViewAdapter.addAllTask((ArrayList<Task>) data);
			taskViewAdapter.filterByConstraint();
		}

	}
}
