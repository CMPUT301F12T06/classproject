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
 **/

package com.cmput301.classproject.Model;

import com.cmput301.classproject.Model.Tasks.JSONServer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationCore extends Application {

	public static void displayToastMessageLong(Context context, CharSequence text) {
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
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
