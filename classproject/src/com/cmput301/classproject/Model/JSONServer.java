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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Application;

// Singleton
public class JSONServer {
	
	private final static Logger LOGGER = Logger.getLogger(JSONServer.class.getName());

	public static enum Code {
		FAILURE, SUCCESS
	}
	

	private HttpClient httpClient = new DefaultHttpClient();
	private Gson gson = new Gson();
	private HttpPost httpPost = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T06/");
	

	@SuppressWarnings("unused")
	private Application appRef = null;
	private static JSONServer instance = null;

	private FakeJSONServer fakeServer; // TODO REMOVE

	private JSONServer() {

		fakeServer = new FakeJSONServer(); // TODO REMOVE
	}

	public static JSONServer getInstance() {
		if (instance == null) {
			instance = new JSONServer();
		}
		return instance;
	}

	public void setApplicatonReference(Application appRef) {
		this.appRef = appRef;
	}

	/**
	 * Name: isConnected Description: this will check the connection to the json
	 * server
	 * 
	 * @return
	 */
	public boolean isConnected() {
		try {
		HttpResponse response = httpClient.execute(httpPost);
		int status = response.getStatusLine().getStatusCode();
		
		LOGGER.log(Level.INFO,"Status: " + status);
		return (status==HttpStatus.SC_OK);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;

	}

	/**
	 * Name: addTask Description: this will add the task in JSON format to the
	 * server
	 */
	public Code addTask(Task task) {
		// TODO update task id from server response
		return fakeServer.addTask(task); // TODO REMOVE
	}

	/**
	 * Name: getTask Description: This will retrieve all tasks in JSON form
	 * convert it to a "Task" list and return it It is null if something went
	 * wrong.
	 * 
	 * @return
	 */
	public List<Task> getAllTasks() {

		return fakeServer.getAllTasks();// TODO REMOVE

	}

	/**
	 * Name: This syncs our local tasks to the most recent server version
	 * 
	 * @return
	 */
	public Code sync() {
		return fakeServer.sync();
	}

	public Code addSubmission(int taskId, Submission submission) {
		// get latest task via taskId
		// add submission to task
		// run updateTask on our task
		return fakeServer.addSubmission(taskId, submission); // TODO REMOVE
	}
	
	/*
	 * To convert the InputStream to String we use the BufferedReader.readLine()
	 * method. We iterate until the BufferedReader return null which means
	 * there's no more data to read. Each line will appended to a StringBuilder
	 * and returned as String.
	 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
	 */
	private  String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				is.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
