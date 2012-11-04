package com.cmput301.classproject.Model.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.Tasks.JSONServer.Code;
import com.cmput301.classproject.Model.Tasks.JSONServer.TaskType;

public class ModifyServerData extends AsyncTask<Task, Integer, Code> {
	
	TaskType type;
	ProgressDialog dialog;
	Context context;
	
	public ModifyServerData(TaskType type,Context mContext) {
		this.type=type;
		this.context = mContext;
		dialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		dialog.setTitle("Please wait");
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
		dialog.dismiss();
	}
}

		