package com.cmput301.classproject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.support.v4.app.NavUtils;

public class AddSubmissionActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;

	private Task task = null; // reference to our task (this is passed by
								// taskviewactivity)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_submission);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			task = (Task) extras.get("Task");
		}

		if (task == null) {
			finish();
		} else {
			ApplicationCore.displayToastMessage(this, "Obtained Task Id: "
					+ task.getId());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_submission, menu);
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
	 * Name: onSharingChange Description: This will check the type of sharing
	 * permissions when the user changes any of the options in the radio.
	 * 
	 * Use a switch statement and the ((RadioButton) view).isChecked(); and
	 * view.getId() to check which option is checked
	 * 
	 * @param v
	 */
	public void onSharingChange(View v) {
		boolean checked = ((RadioButton) v).isChecked();

		switch (v.getId()) {
		case R.id.submission_private_sharing:
			if (checked) {

			}

			break;
		case R.id.submission_creator_sharing:
			if (checked) {

			}

			break;
		case R.id.submission_public_sharing:
			if (checked) {

			}

			break;
		}
	}

	/**
	 * Name: addPhotoHandler Description: This will delegate the camera app to
	 * take a photo or let the user browse a photo from their phone to add.
	 * Maybe give the user a popup option using Toast
	 * 
	 * @param v
	 */
	public void addPhotoHandler(View v) {
		// Intent cameraIntent = new
		// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// startActivityForResult(cameraIntent,
		// CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode ==
	// RESULT_OK)
	// {
	//
	// }
	//
	// }

	/**
	 * Name: addAudioHandler Description: Similar to the photo handler but This
	 * will be done in phase 3
	 * 
	 * @param v
	 */
	public void addAudioHandler(View v) {

	}

	/**
	 * Name: cancelSubmissionHandler Description: cancels Submission and returns
	 * to the view tasks activity
	 * 
	 * @param v
	 */
	public void cancelSubmissionHandler(View v) {
		finish();
	}

	/**
	 * Name: submitSubmissionHandler Description: communicates with the JSON
	 * Server to add a submission to the server and then return to the main
	 * activity
	 * 
	 * @param v
	 */
	public void submitSubmissionHandler(View v) {
		// TODO implement the submission adding in TaskManager
		// TaskManager should try to use the JSON server (look at the addTask
		// method in TaskManager for an exmaple)
		finish();

	}

}
