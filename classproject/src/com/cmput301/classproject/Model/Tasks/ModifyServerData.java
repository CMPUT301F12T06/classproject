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
 
 ModifyServer is a class which extends AsyncTask. This is used when adding and 
 removing a task. This was used to allow this operation to operate in another thread 
 and avoid the onMainThreadException. This also displays to the user a ProgressDialog 
 to show that it is working and will finish and return the Activity on completion.
 
 @author Benson Trinh
 **/

package com.cmput301.classproject.Model.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.TaskManager;
import com.cmput301.classproject.Model.Tasks.JSONServer.Code;
import com.cmput301.classproject.Model.Tasks.JSONServer.TaskType;

public class ModifyServerData extends AsyncTask<Task, Integer, Code> {
	
	private TaskType type;
	private ProgressDialog dialog;
	private Context context;
	
	public ModifyServerData(TaskType type,Context mContext) {
		this.type=type;
		this.context = mContext;
		dialog = new ProgressDialog(context);
	}
	
	/**
	 * Displaying a ProgressDialog before executing the task
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setTitle("Modifying Tasks");
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.setIndeterminate(true);
		//dialog.show();
	}
	
	/**
	 * Calls the JSONServer to handle the addition or deletion of a task
	 */
	@Override
	protected Code doInBackground(Task...tasks) {
		switch(type) {
			case AddTask:
				return JSONServer.getInstance().addTask(tasks[0]);
			case RemoveTask:
				return JSONServer.getInstance().deleteTask(tasks[0]);

				
			default: 
				return Code.FAILURE;
		}
	}
	
	/**
	 * After completing adding or deletion of a task it will
	 * remove the dialog message, sync and 
	 * then notify the Activity to finish
	 */
	@Override
	protected void onPostExecute(Code result) {
		//If we are loading local first then this is not needd. It will be done in the background if 
		//it is online
		try {
			dialog.dismiss();
			dialog = null;
		} catch (Exception ex) {
			//do nothing - insurance for if the activity finishes faster
		}
		TaskManager.getInstance().sync(context);
		((Activity) context).finish();

	}
}

		