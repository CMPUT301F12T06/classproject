package com.cmput301.classproject;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

//Singleton object - We extend application so we can use the
//ApplicationContext and treat this like a singleton.
// Each additional singleton used in this project has a reference
// to this application object so we can do operations like local file
// storage amoung other singletons.
public class TaskManager extends Application {

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
	}

}
