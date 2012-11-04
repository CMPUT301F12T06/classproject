package com.cmput301.classproject.Model.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.cmput301.classproject.Model.TaskManager;
import com.cmput301.classproject.Model.Tasks.JSONServer.Code;

public class AddSubmission extends AsyncTask<SubmissionData, Integer, Code>{
	
	Context context;
	ProgressDialog dialog;
	
	public AddSubmission(Context mContext) {
		this.context = mContext;
		this.dialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setTitle("Adding a Submission");
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.setIndeterminate(true);
		dialog.show();
	}
	
	@Override
	protected Code doInBackground(SubmissionData...data) {
		return JSONServer.getInstance().addSubmission(data[0].getTaskId(),data[0].getSubmission());
	}
	
	@Override
	protected void onPostExecute(Code result) {
		try {
			dialog.dismiss();
			dialog = null;
		} catch (Exception ex) {
			//do nothing - insurance for if the activity finishes faster
		}
		if(result==Code.SUCCESS)
			TaskManager.getInstance().sync(context);
		
		((Activity) context).finish();

	}
}
