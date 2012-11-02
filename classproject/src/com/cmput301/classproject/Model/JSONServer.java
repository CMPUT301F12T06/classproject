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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private Type taskType = Task.class;
	

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
	public boolean isConnected(HttpPost post) {
		try {
		HttpResponse response = httpClient.execute(post);
		int status = response.getStatusLine().getStatusCode();
		
		LOGGER.log(Level.INFO,"Status: " + status);
		return (status==HttpStatus.SC_OK);

		} catch (Exception ex) {
			//do nothing. It will return false
		}
		return false;

	}
	
	/**
	 * Name: isConnected Description: this will check the connection to the json
	 * server
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return this.isConnected(httpPost);
	}

	/**
	 * Name: addTask Description: this will add the task in JSON format to the
	 * server
	 */
	public Code addTask(Task task) {
	try {
		LOGGER.log(Level.INFO,"Retrieved Task: " + task.toString());
		Task newTask = new Task();
		//We don't have a generic type so we don't need to do the TypeToken creation
		List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
		
		//This is equivalent to doing:
		//http://path?action=post&summary=desc&content=content
		values.add(new BasicNameValuePair("action","post"));
		values.add(new BasicNameValuePair("content",gson.toJson(task)));
		
		httpPost.setEntity(new UrlEncodedFormEntity(values));
		HttpResponse response = httpClient.execute(httpPost);
		LOGGER.log(Level.INFO,"Task added. Status Code: " + response.getStatusLine().toString());
		
		
		//Get the content of the response on the page
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			InputStream is = entity.getContent();
			String jsonString = convertStreamToString(is);
			Pattern p = Pattern.compile("\"id\":\".*\"");
			Matcher m = p.matcher(jsonString);
			String id = "";
			if(m.find()) {
				id = m.group(0).replaceAll("(\\r|\\n)", ""); //TODO - Add some error checking
				newTask.setId(id);
			}
			
			LOGGER.log(Level.INFO,"JSON String: " + jsonString);
			LOGGER.log(Level.INFO,"id obtained: " + id);
			

		}
		EntityUtils.consume(entity);
		//After we retrieve the id we will update the task
		values = new ArrayList<BasicNameValuePair>();
		
		//This is equivalent to doing:
		//http://path?action=post&summary=desc&content=content
		values.add(new BasicNameValuePair("action","update"));
		values.add(new BasicNameValuePair("id",newTask.getId()));
		values.add(new BasicNameValuePair("content",gson.toJson(task)));
		
		httpPost.setEntity(new UrlEncodedFormEntity(values));
		response = httpClient.execute(httpPost);
		EntityUtils.consume(entity);
		
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==HttpStatus.SC_OK)
			return Code.SUCCESS;
		
		LOGGER.log(Level.INFO,"Retrieved Task: " + newTask.toString());
		
		
	} catch (Exception e) {
		LOGGER.log(Level.SEVERE,e.getMessage());
		e.printStackTrace();
	}

	return Code.FAILURE;
		
	}

	/**
	 * Name: getTask Description: This will retrieve all tasks in JSON form
	 * convert it to a "Task" list and return it It is null if something went
	 * wrong.
	 * 
	 * @return
	 */
	public List<Task> getAllTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		ArrayList<String> ids = new ArrayList<String>();
		
		try {
			
			//Do a list action to get the id's 
			List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
			
			//This is equivalent to doing:
			//http://path?action=post&summary=desc&content=content
			values.add(new BasicNameValuePair("action","list"));
	
			httpPost.setEntity(new UrlEncodedFormEntity(values));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			if(entity!=null) {
				InputStream is = entity.getContent();
				String jsonString = convertStreamToString(is);
				jsonString = jsonString.replace("[","").replace("]", "");
				jsonString = jsonString.replaceAll("\\{\"id\":|\\}", "");
				jsonString = jsonString.replaceAll("\\\"","");
				jsonString = jsonString.replaceAll("(\\r|\\n)", ""); //regex to remove the end of line
				String temp[] = jsonString.split(",");
				for(String i : temp) {
					LOGGER.log(Level.INFO,"String i: " + i);
					ids.add(i);
				}
				
			}
			
			LOGGER.log(Level.INFO,ids.toString());
			EntityUtils.consume(entity);
			
			
			
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,ex.getMessage());
			ex.printStackTrace();
		}
		for(int i = 0; i < ids.size();i++) {
			tasks.add(getTask(ids.get(i)));
		}
		LOGGER.log(Level.INFO,"Tasks Length: " + tasks.size());
		for(Task t : tasks) {
			LOGGER.log(Level.INFO,t.toString());
		}
		return tasks;
		
	}
	
	private Task getTask(String id) {
		Task newTask = new Task();
		try {
			//We don't have a generic type so we don't need to do the TypeToken creation
			List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();

			values.add(new BasicNameValuePair("action","get"));
			values.add(new BasicNameValuePair("id",id));
	
			httpPost.setEntity(new UrlEncodedFormEntity(values));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			if(entity!=null) {
				InputStream is = entity.getContent();
				String jsonString = convertStreamToString(is);
				LOGGER.log(Level.INFO,"String: " + jsonString);
				newTask = gson.fromJson(jsonString, taskType);
			}
			EntityUtils.consume(entity);
			return newTask;
			
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,ex.getMessage());
			ex.printStackTrace();
		}
		return null;
		
	}

	/**
	 * Name: This syncs our local tasks to the most recent server version
	 * 
	 * @return
	 */
	public Code sync() {
		return fakeServer.sync();
	}

	public Code addSubmission(String taskId, Submission submission) {
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
