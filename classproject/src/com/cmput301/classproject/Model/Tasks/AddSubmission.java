package com.cmput301.classproject.Model.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.cmput301.classproject.Model.Tasks.JSONServer.Code;

public class AddSubmission extends AsyncTask<SubmissionData, Integer, Code>{
	
	Context context;
	
	public AddSubmission(Context mContext) {
		this.context = mContext;
	}
	@Override
	protected Code doInBackground(SubmissionData...data) {
		return JSONServer.getInstance().addSubmission(data[0].getTaskId(),data[0].getSubmission());
	}
	
	@Override
	protected void onPostExecute(Code result) {		
		
	}
}
