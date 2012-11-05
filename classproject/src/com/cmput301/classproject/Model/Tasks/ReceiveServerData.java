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
 
 ReceiveServerData is a class which extends AsyncTask. This is used to sync the 
 data from the server.
 
 @author Benson Trinh
 **/
package com.cmput301.classproject.Model.Tasks;

import java.util.ArrayList;

import com.cmput301.classproject.Model.ApplicationCore;
import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;
import com.cmput301.classproject.Model.Tasks.JSONServer.TaskType;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ReceiveServerData extends
		AsyncTask<Task, Integer, ArrayList<Task>> {

	@SuppressWarnings("unused")
	private TaskType type;
	private Context context;
	private ProgressDialog dialog;

	public ReceiveServerData(TaskType type, Context mContext) {
		this.type = type;
		context = mContext;
		dialog = new ProgressDialog(context);
	}

	/**
	 * Displaying a ProgressDialog before executing the task
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setTitle("Retrieving Tasks");
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.setIndeterminate(true);
		dialog.show();
	}

	/**
	 * Calls the JSONServer to retrieve all the tasks
	 */
	@Override
	protected ArrayList<Task> doInBackground(Task... tasks) {
		return JSONServer.getInstance().getAllTasks();
	}

	/**
	 * After retreiving all the tasks it will notify all observers to update
	 * with the new information
	 * 
	 * @param result
	 *            The ArrayList<task> with the updated information
	 */
	@Override
	protected void onPostExecute(ArrayList<Task> result) {
		try {
			dialog.dismiss();
			dialog = null;
		} catch (Exception ex) {
			// do nothing
		}
		TaskManager.getInstance().notifyAllObservers(result);
		ApplicationCore.displayToastMessage(context, "Synced with Server");

	}

}
