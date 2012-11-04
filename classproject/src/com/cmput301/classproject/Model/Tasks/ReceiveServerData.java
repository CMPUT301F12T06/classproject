package com.cmput301.classproject.Model.Tasks;

import java.util.ArrayList;

import com.cmput301.classproject.Model.Task;
import com.cmput301.classproject.Model.Tasks.JSONServer.TaskType;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ReceiveServerData extends AsyncTask<Task,Integer,ArrayList<Task>> {
	
	TaskType type;
	Context context;
	ProgressDialog dialog;
	
	public ReceiveServerData(TaskType type, Context mContext) {
		this.type = type;
		context = mContext;
		dialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		dialog.setTitle("Loading...");
		dialog.show();
	}


	@Override
	protected ArrayList<Task> doInBackground(Task...tasks) {
		return JSONServer.getInstance().getAllTasks();
	}
	
	@Override
	protected void onPostExecute(ArrayList<Task> result) {
		dialog.cancel();
	}

}
