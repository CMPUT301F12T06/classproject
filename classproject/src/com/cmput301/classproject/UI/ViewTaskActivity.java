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
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.ApplicationCore;
import com.cmput301.classproject.Model.LocalStorage;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;
import com.cmput301.classproject.Model.Tasks.JSONServer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class ViewTaskActivity extends Activity implements Observer {

	private final static Logger LOGGER = Logger
			.getLogger(ViewTaskActivity.class.getName());

	private ArrayList<Submission> submissionArray = new ArrayList<Submission>();
	private Task task = null;
	private String taskId = null;

	/**
	 * Wrapper for submission array.
	 * @author lemon97
	 *
	 */
	public class FilteredSubmissionArray extends ArrayAdapter<Submission> {
		public FilteredSubmissionArray(Context context, int resource,
				int textViewResourceId, List<Submission> objects) {
			super(context, resource, textViewResourceId, objects);
		}
	}

	/**
	 * Name: addSubmission  
	 * Description: Wrapper for adding a submission, all filtering
	 * based on submission permission is done here.
	 * @
	 * @param t
	 * @param s
	 */
	public void addSubmission(Task t, Submission s) {

		String username = LocalStorage.getInstance().loadUsernameFromStorage();

		switch (s.getAccess()) {

		case Public:
			submissionArray.add(s);
			break;
		case Private:
			if (s.getAuthor().equals(username)) {
				submissionArray.add(s);
			} else {
				submissionArray.remove(s);
			}
			break;

		case Creator:
			if (s.getAuthor().equals(username)
					|| t.getCreator().equals(username)) {
				submissionArray.add(s);
			} else {
				submissionArray.remove(s);
			}
			break;
		}
	}

	ListView submissionListView;
	FilteredSubmissionArray submissionViewAdapter2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		getActionBar().setDisplayHomeAsUpEnabled(false);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.task = (Task) extras.getSerializable("Task");
		}

		if (task == null) {
			finish();
		} else {
			taskId = task.getId();
			TaskManager.currentTask = task.getId();
			TextView temp;

			// TODO make this view look better
			temp = ((TextView) findViewById(R.id.task_view_name));
			temp.setText(task.getName());

			temp = ((TextView) findViewById(R.id.task_view_description));
			temp.setText(task.getDescription());

			temp = ((TextView) findViewById(R.id.task_view_creator));
			temp.setText(task.getCreator());

			temp = ((TextView) findViewById(R.id.task_view_requires));
			String requires = "";
			if (task.getRequires() == 0)
				requires = "Nothing";
			if ((task.getRequires() & Submission.ACCESS_PHOTO) > 0)
				requires += "Photo ";
			if ((task.getRequires() & Submission.ACCESS_AUDIO) > 0)
				requires += "Audio ";
			if ((task.getRequires() & Submission.ACCESS_TEXT) > 0)
				requires += "Text ";
			temp.setText(requires);

			temp = ((TextView) findViewById(R.id.task_view_access));
			if (task.isPublicAccess())
				temp.setText("Public");
			else
				temp.setText("Private");

		}

		final ViewTaskActivity selfRef = this;

		submissionListView = (ListView) findViewById(R.id.task_view_submission_list);
		submissionListView
				.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		submissionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		submissionListView.setStackFromBottom(false);
		submissionListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Submission submission = (Submission) submissionListView
						.getItemAtPosition(position);
				if (submission != null) {
					Intent intent = new Intent(selfRef,
							SubmissionViewActivity.class);
					intent.putExtra("Submission", submission);
					startActivity(intent);
				}

			}
		});

		submissionViewAdapter2 = new FilteredSubmissionArray(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, submissionArray);
		submissionViewAdapter2.setNotifyOnChange(true);
		submissionListView.setAdapter(submissionViewAdapter2);

		// MVC model attach this view to our data model
		TaskManager.getInstance().addObserver(this);

		for (Submission s : task.getSubmissions()) {
			addSubmission(task, s);
		}
		submissionViewAdapter2.notifyDataSetChanged();

		if (task.getSubmissions().size() == 0) {
			ApplicationCore.displayToastMessageLong(this,
					getString(R.string.view_task_no_submission_msg));
		}
	}

	/**
	 * onCreateOptionsMenu Description: Sets up menu at top of activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

	/**
	 * Name: onOptionsItemSelected Description: Fix moving back using back
	 * button issue.
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Name: okayClicked Description: User is finished viewing the Task Upon
	 * clicking Okay they will be returned to the MainActivity
	 * 
	 * @param v
	 */
	public void okayClicked(View v) {
		finish();
	}

	/**
	 * Name: addSubmissionHandler Description: This will navigate the user to
	 * the AddSubmissionActivity
	 * 
	 * @param v
	 */
	public void addSubmissionHandler(View v) {
		if (task == null) {
			finish(); // TODO display error
		} else {
			Intent intent = new Intent(this, AddSubmissionActivity.class);
			intent.putExtra("Task", task);
			startActivity(intent);
		}
	}

	/**
	 * Name: update Description: This is our view update. Model updates are sent
	 * to the view via this method.
	 */
	public void update(Observable observable, Object data) {
		if (data != null && taskId != null) {

			@SuppressWarnings("unchecked")
			ArrayList<Task> tasks = (ArrayList<Task>) data;
			for (Task t : tasks) {
				submissionArray.clear();
				// only update the task we have a reference to.
				if (t.getId().equals(taskId)) {

					for (Submission s : t.getSubmissions()) {
						addSubmission(t, s);
					}
					submissionViewAdapter2.notifyDataSetChanged();
					return;
				}
			}

		}
	}
}
