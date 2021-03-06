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

This is the GUI which shows the options the user has to add a submission to an exisiting task
 **/
package com.cmput301.classproject.UI;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.ApplicationCore;
import com.cmput301.classproject.Model.LocalStorage;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.support.v4.app.NavUtils;

/**
 * AddSubmissionActivity - activity used to add a submission
 * @author Thomas Polasek
 *
 */
public class AddSubmissionActivity extends Activity implements Observer {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private Task task = null; // reference to our task (this is passed by
								// taskviewactivity)

	ListView addedPhotos;
	ListView addedAudio;
	EditText submissionText;
	EditText submissionSummary;
	ArrayList<Bitmap> photosTaken = new ArrayList<Bitmap>();
	Submission.Permission submissionPermission = Submission.Permission.Public;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_submission);
		getActionBar().setDisplayHomeAsUpEnabled(false);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			task = (Task) extras.get("Task");
			if ((task.getRequires() & Submission.ACCESS_PHOTO) == 0)
			{
				findViewById(R.id.add_photo_id).setEnabled(false);
			}
			if((task.getRequires() & Submission.ACCESS_AUDIO) == 0)
			{
				findViewById(R.id.add_audio_id).setEnabled(false);
			}
		}

		if (task == null) {
			finish();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// Recognize the Views
		addedPhotos = (ListView) findViewById(R.id.photos_added);
		addedAudio = (ListView) findViewById(R.id.audio_added);
		submissionText = (EditText) findViewById(R.id.submissionText);
		submissionSummary = (EditText) findViewById(R.id.submissionSummary);

		// Attach the ImageAdapter to the ListView for photos.
		addedPhotos.setAdapter(new ImageAdapter(this));
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
				submissionPermission = Submission.Permission.Private;
			}

			break;
		case R.id.submission_creator_sharing:
			if (checked) {
				submissionPermission = Submission.Permission.Creator;
			}

			break;
		case R.id.submission_public_sharing:
			if (checked) {
				submissionPermission = Submission.Permission.Public;
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
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent,
				CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// Photo has been generated
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			photosTaken.add(thumbnail);
		}

	}

	/**
	 * Name: addAudioHandler Description: Similar to the photo handler but This
	 * will be done in phase 3
	 * 
	 * @param v
	 */
	public void addAudioHandler(View v) {
		// We're going to send a Toast that the button functionality is disabled
		// right now.
		ApplicationCore.displayToastMessage(getApplicationContext(),
				"Adding of Audio is not implemented right now.");
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
		
		boolean connected = TaskManager.getInstance().isConnected(getApplicationContext());
		
		if(!connected){
			ApplicationCore.displayToastMessageLong(getApplicationContext(), 
					"Please connect to the internet before trying to add a submission.");
			return;
		}
		
		
		
		String summary = submissionSummary.getText().toString();
		if (summary == null || summary.length() <= 0) {
			ApplicationCore.displayToastMessage(getApplicationContext(),
					"Please specify a summary for this submission");
			return;
		}

		// Task requires photo in a submission
		if ((task.getRequires() & Submission.ACCESS_PHOTO) != 0
				&& photosTaken.size() <= 0) {
			ApplicationCore.displayToastMessage(getApplicationContext(),
					"This submission requires at least 1 photo");
			return;
		}

		String textSubmission = submissionText.getText().toString();
		// Task requires a text in a submission
		if ((task.getRequires() & Submission.ACCESS_TEXT) != 0
				&& textSubmission.length() <= 0) {
			ApplicationCore.displayToastMessage(getApplicationContext(),
					"This submission requires a text entry");
			return;
		}

		if (submissionPermission == null) {
			ApplicationCore
					.displayToastMessage(getApplicationContext(),
							"Please select a sharing permission type for this submission");
			return;
		}

		// TODO do audio requirement
		String author = LocalStorage.getInstance().loadUsernameFromStorage();

		long timestamp = (new Date()).getTime();

		Submission submission = new Submission(summary, author, textSubmission,
				photosTaken, submissionPermission, timestamp);
		TaskManager.getInstance().addSubmission(task.getId(), submission, this);
		finish();
	}

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * ImageAdapter Class to support putting thumbnails into list views. Will
	 * also have a modified implementation into ViewSubmission activity. This is
	 * tailored to fit ListViews.
	 */
	public class ImageAdapter extends BaseAdapter {
		private Context context;

		public ImageAdapter(Context C) {
			context = C;
		}

		public int getCount() {
			return photosTaken.size();
		}

		public Object getItem(int arg0) {
			return photosTaken.get(arg0);
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(context);
				imageView.setLayoutParams(new ListView.LayoutParams(185, 185));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(5, 5, 5, 5);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageBitmap(photosTaken.get(position));
			return imageView;
		}

	}

}
