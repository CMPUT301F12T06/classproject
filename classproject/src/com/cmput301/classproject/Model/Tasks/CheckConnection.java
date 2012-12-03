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

import android.content.Context;
import android.os.AsyncTask;

import com.cmput301.classproject.Model.LocalStorage;
import com.cmput301.classproject.Model.TaskManager;

public class CheckConnection extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;

	/**
	 * The constructor used to initialize AddSubmission
	 * 
	 * @param mContext
	 *            The applicationContext in which it was called
	 */
	public CheckConnection(Context mContext) {
		this.context = mContext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	/**
	 * Calls the JSONServer to handle the addition of a submission
	 */
	@Override
	protected Boolean doInBackground(Integer... data) {
		return JSONServer.getInstance().isConnected();
	}

	/**
	 * Get the tasks from the server and save it locally
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if(result) {
			JSONServer.getInstance().sync();
			new ReceiveServerData(JSONServer.TaskType.GetTasks,context).execute();
			LocalStorage.getInstance().saveTasksFromStorage(TaskManager.cachedTasks);
		}

	}
}
