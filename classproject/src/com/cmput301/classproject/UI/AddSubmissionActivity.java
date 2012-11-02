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
import com.cmput301.classproject.Model.ApplicationCore;
import com.cmput301.classproject.Model.Task;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.support.v4.app.NavUtils;

public class AddSubmissionActivity extends Activity implements Observer {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private Task task = null; // reference to our task (this is passed by
								// taskviewactivity)

	ListView addedPhotos;
	ListView addedAudio;
	EditText submissionText;
	ArrayList<Bitmap> photosTaken = new ArrayList<Bitmap>();
	
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
	public void onStart()
	{
		super.onStart();
		// Recognize the Views
		addedPhotos = (ListView) findViewById(R.id.photos_added);
		addedAudio = (ListView) findViewById(R.id.audio_added);
		submissionText = (EditText) findViewById(R.id.submissionText);
		
		
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
		 Intent cameraIntent = new
		 Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		 startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode ==
				RESULT_OK)
		{
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
		// We're going to send a Toast that the button functionality is disabled right now.
		ApplicationCore.displayToastMessage(getApplicationContext(), "Adding of Audio is not implemented right now.");
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
		
		// The photos are stored in ArrayList<Bitmap> photosTaken.  Waiting on a storage implementation before we port it over.
		
		finish();

	}

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ImageAdapter Class to support putting thumbnails into list views.
	 * Will also have a modified implementation into ViewSubmission activity.
	 * This is tailored to fit ListViews.
	 */
	public class ImageAdapter extends BaseAdapter
	{
		private Context context;
		
		public ImageAdapter(Context C)
		{
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
