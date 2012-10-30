package com.cmput301.classproject;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import android.app.Application;
import android.content.Context;

//Singleton
public class LocalStorage {

	private static final String LOG_FILE = "LocalTaskStorage";

	private Application appRef = null;
	private static LocalStorage instance = null;

	private LocalStorage() {
	}

	public static LocalStorage getInstance() {
		if (instance == null) {
			instance = new LocalStorage();
		}
		return instance;
	}

	public void setApplicatonReference(Application appRef) {
		this.appRef = appRef;
	}

	// Saves logEntries array to file
	@SuppressWarnings({ "unchecked" })
	public List<Task> load() {
		List<Task> output = null;
		try {
			ObjectInputStream in = new ObjectInputStream(
					appRef.openFileInput(LOG_FILE));
			if (in != null)
				output = (List<Task>) in.readObject();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			output = null;
		}
		return output;
	}

	// Loads logEntries array to file
	public void save(List<Task> tasks) {
		try {
			ObjectOutput out = new ObjectOutputStream(appRef.openFileOutput(
					LOG_FILE, Context.MODE_PRIVATE));
			out.writeObject(tasks);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
