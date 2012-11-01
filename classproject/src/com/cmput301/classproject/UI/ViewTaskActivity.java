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
import java.util.Observable;
import java.util.Observer;

import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;
import com.cmput301.classproject.R.id;
import com.cmput301.classproject.R.layout;
import com.cmput301.classproject.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

	private Task task = null;

	ListView submissionListView;
	ArrayAdapter<Submission> submissionViewAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.task = (Task) extras.getSerializable("Task");
		}

		if (task == null) {
			finish();
		} else {
			TextView temp;

			// TODO make this view look better
			temp = ((TextView) findViewById(R.id.task_view_name));
			temp.setText(task.getName());

			temp = ((TextView) findViewById(R.id.task_view_description));
			temp.setText(task.getDescription());

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

		submissionViewAdapter = new ArrayAdapter<Submission>(this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, new ArrayList<Submission>());
		submissionViewAdapter.setNotifyOnChange(true);
		submissionListView.setAdapter(submissionViewAdapter);

		// MVC model attach this view to our data model
		TaskManager.getInstance().addObserver(this);

		submissionViewAdapter.clear();
		submissionViewAdapter.addAll(task.getSubmissions());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_task, menu);
		return true;
	}

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

	public void update(Observable observable, Object data) {
		if (data != null && task != null) {
			@SuppressWarnings("unchecked")
			ArrayList<Task> tasks = (ArrayList<Task>) data;
			for (Task t : tasks) {
				// only update the task we have a reference to.
				if (t.getId() == this.task.getId()) {
					submissionViewAdapter.clear();
					submissionViewAdapter.addAll(t.getSubmissions());
					return;
				}
			}

		}
	}

}
