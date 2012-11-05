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
 
 AddSubmission is a class which extends AsyncTask. This was used to allow this operation
 to operate in another thread and avoid the onMainThreadException. This also
 displays to the user a ProgressDialog to show that it is working and will finish and
 return the Activity on completion.
 
 @author Benson Trinh
 **/
package com.cmput301.classproject.Model.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cmput301.classproject.Model.TaskManager;
import com.cmput301.classproject.Model.Tasks.JSONServer.Code;

public class AddSubmission extends AsyncTask<SubmissionData, Integer, Code> {

	private Context context;
	private ProgressDialog dialog;

	/**
	 * The constructor used to initialize AddSubmission
	 * 
	 * @param mContext
	 *            The applicationContext in which it was called
	 */
	public AddSubmission(Context mContext) {
		this.context = mContext;
		this.dialog = new ProgressDialog(context);
	}

	/**
	 * Displaying a ProgressDialog before executing the task
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setTitle("Adding a Submission");
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.setIndeterminate(true);
		dialog.show();
	}

	/**
	 * Calls the JSONServer to handle the addition of a submission
	 */
	@Override
	protected Code doInBackground(SubmissionData... data) {
		return JSONServer.getInstance().addSubmission(data[0].getTaskId(),
				data[0].getSubmission());
	}

	/**
	 * After completing addition of the submission it will sync the server and
	 * remove the dialog message. It will then notify the Activity to finish
	 */
	@Override
	protected void onPostExecute(Code result) {
		try {
			dialog.dismiss();
			dialog = null;
		} catch (Exception ex) {
			// do nothing - insurance for if the activity finishes faster
		}
		if (result == Code.SUCCESS)
			TaskManager.getInstance().sync(context);

		((Activity) context).finish();

	}
}
