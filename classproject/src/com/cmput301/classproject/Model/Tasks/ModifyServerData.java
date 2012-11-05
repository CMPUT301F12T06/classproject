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
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setTitle("Modifying Tasks");
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.setIndeterminate(true);
		dialog.show();
	}
	
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
	
	@Override
	protected void onPostExecute(Code result) {
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

		