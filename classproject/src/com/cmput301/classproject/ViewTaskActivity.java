package com.cmput301.classproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class ViewTaskActivity extends Activity {

	private Task task = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.task = (Task) extras.getSerializable("Task");
		}
		
		if(task == null){
			finish();
		}
		else{
			
		}
		
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

	}

	/**
	 * Name: addSubmissionHandler Description: This will navigate the user to
	 * the AddSubmissionActivity
	 * 
	 * @param v
	 */
	public void addSubmissionHandler(View v) {
		Intent intent = new Intent(this, AddSubmissionActivity.class);
		startActivity(intent);
	}

}
