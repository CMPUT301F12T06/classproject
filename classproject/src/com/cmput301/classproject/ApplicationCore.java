package com.cmput301.classproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationCore extends Application {


	public static void displayToastMessage(Context context, CharSequence text) {
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}



	public static String getStringFromId(Activity act, int id) {
		return (((TextView) act.findViewById(id)).getText().toString());
	}

	@Override
	public void onCreate() {
		super.onCreate();

	

		// pass our single Application instance to other singleton classes
		JSONServer.getInstance().setApplicatonReference(this);
		LocalStorage.getInstance().setApplicatonReference(this);
		TaskManager.getInstance().setApplicatonReference(this);
	}
}
