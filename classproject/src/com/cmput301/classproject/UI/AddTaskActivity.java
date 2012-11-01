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

import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.ApplicationCore;
import com.cmput301.classproject.Model.DeviceUuidFactory;
import com.cmput301.classproject.Model.JSONServer;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;

public class AddTaskActivity extends Activity {

	private int submissionRequires = 0;
	private boolean isAccessPublic = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_task, menu);
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
	 * @param v
	 */
	public void onSharingChange(View v) {
		boolean checked = ((RadioButton) v).isChecked();

		switch (v.getId()) {
		case R.id.private_sharing:
			if (checked) {
				isAccessPublic = false;
			}

			break;
		case R.id.public_sharing:
			if (checked) {
				isAccessPublic = true;
			}

			break;
		}
	}

	/**
	 * Name: onCheckboxClicked Description: This will check whether the task
	 * "requires" photos, audio or text. Can be none or can be all
	 * 
	 * @param v
	 */
	public void onCheckboxClicked(View v) {
		boolean checked = ((CheckBox) v).isChecked();

		switch (v.getId()) {
		case R.id.requires_photo_checkbox:
			if (checked) {
				submissionRequires |= Submission.ACCESS_PHOTO;
			} else {
				submissionRequires &= ~Submission.ACCESS_PHOTO;
			}
			break;
		case R.id.requires_text_checkbox:
			if (checked) {
				submissionRequires |= Submission.ACCESS_TEXT;
			} else {
				submissionRequires &= ~Submission.ACCESS_TEXT;
			}
			break;
		case R.id.requires_audio_checkbox:
			if (checked) {
				submissionRequires |= Submission.ACCESS_AUDIO;
			} else {
				submissionRequires &= ~Submission.ACCESS_AUDIO;
			}
			break;
		}

	}
	public String getPhoneID() {
		TelephonyManager tManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String uid = tManager.getDeviceId();
		return uid;
	}

	/**
	 * Name: onSubmitHandler Description: Verify that the fields entered are
	 * valid then add the task to the server using the JSONServer Object
	 * 
	 * @param v
	 */
	public void onSubmitHandler(View v) {

		String title = ApplicationCore.getStringFromId(this, R.id.task_title);
		String description = ApplicationCore.getStringFromId(this,
				R.id.task_description);

		if (title == null || title.length() <= 0) {
			ApplicationCore.displayToastMessage(getApplicationContext(),
					"Invalid Task Name Specified");
			return;
		} 
		if (description == null || description.length() <= 0) {
			ApplicationCore.displayToastMessage(getApplicationContext(),
					"Invalid Task Description Specified");
			return;
		}

		
		Task task = new Task(title, description,(new DeviceUuidFactory(this)).getDeviceUuid(), submissionRequires,
				isAccessPublic);

		
		if (TaskManager.getInstance().addTask(task) != JSONServer.Code.SUCCESS) {
			ApplicationCore.displayToastMessage(getApplicationContext(),
					"Error: Failed to send task to server");
			return;
		}

		ApplicationCore.displayToastMessage(getApplicationContext(),
				"Added Task:\n" + task.toString());

		// TODO update MainActivity after this returns

		finish();

	}

	/**
	 * Name: onCancelHandler Description: Does not save anything and returns to
	 * the main activity
	 * 
	 * @param v
	 */
	public void onCancelHandler(View v) {
		finish();
	}

}
