package com.cmput301.classproject.Model.Tasks;

import android.os.AsyncTask;

import com.cmput301.classproject.Model.Tasks.JSONServer.Code;

public class AddSubmission extends AsyncTask<SubmissionData, Integer, Code>{
	
	@Override
	protected Code doInBackground(SubmissionData...data) {
		return JSONServer.getInstance().addSubmission(data[0].getTaskId(),data[0].getSubmission());
	}
}
