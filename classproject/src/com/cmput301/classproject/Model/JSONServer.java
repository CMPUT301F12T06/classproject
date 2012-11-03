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

@author Benson Trinh
 **/
package com.cmput301.classproject.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
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
	 * This will check the connection to the json
	 * server.
	 * 
	 * @param 	post	HttpPost object. This will allow the to
	 * 					specify their own url. 
	 * 
	 * @return	true if connected, false otherwise
	 */
	public boolean isConnected(HttpPost post) {
		try {
		HttpResponse response = httpClient.execute(post);
		int status = response.getStatusLine().getStatusCode();
		
		LOGGER.log(Level.INFO,"Status: " + status);
		return (status==HttpStatus.SC_OK);

		} catch (Exception ex) {
			//do nothing. It will return false
			LOGGER.log(Level.SEVERE,ex.getMessage());
		}
		return false;

	}
	
	/**
	 * Same functionality as the other method but uses
	 * the default httpPost.
	 * @return
	 */
	public boolean isConnected() {
		return this.isConnected(httpPost);
	}
	
	/**
	 * This will get the latest task added. This will use
	 * ?action=list&n=1 to only get the latest task.
	 * Currently this is mainly used for cleanup in 
	 * the JUnit tests but the functionality may be
	 * extended in the future. 
	 * 
	 * @return the latest task or null if there is no value
	 * 
	 */
	
	public Task getLatestTask() {
		ArrayList<ServerData> ids = new ArrayList<ServerData>();
		
		try {
			
			List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
			
			values.add(new BasicNameValuePair("action","list"));
			values.add(new BasicNameValuePair("n","1"));
			
			httpPost.setEntity(new UrlEncodedFormEntity(values));
			HttpResponse response = httpClient.execute(httpPost);
			
			//Get the content of the response on the page
			HttpEntity entity = response.getEntity();
			
			if(entity!=null) {
				InputStream is = entity.getContent();
				String jsonString = convertStreamToString(is);
				Type collectionType = new TypeToken<Collection<ServerData>>() {}.getType();
				ids = gson.fromJson(jsonString, collectionType);
				
				if(!ids.isEmpty()) {
					//There should only be 1 entry
					String id = ids.get(0).getId();
					LOGGER.log(Level.INFO,"obtained latest task: " + id);
					return getTask(id);
				}
				
				EntityUtils.consume(entity);
				
			}
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,ex.getMessage());
			ex.printStackTrace();
		}
		
		//Add a return code that says the list is empty
		return null;
	}

	/**
	 * This will add the task in JSON format to the
	 * server using Gson. The task will first
	 * use a post action to send the data to the 
	 * server. It will then take that response from
	 * the server to obtain the id for the task and
	 * assign it to our Task object. An update method
	 * is then called on the task after we update it
	 * with the obtained id. 
	 * 
	 * @param	task	The task to add to the server
	 * 
	 * @return Code.SUCCESS or Code.FAILURE
	 */
	public Code addTask(Task task) {
	try {
		Task newTask = task;
		ServerData responseData = new ServerData();
		
		List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
		
		values.add(new BasicNameValuePair("action","post"));
		values.add(new BasicNameValuePair("content",gson.toJson(task)));
		
		httpPost.setEntity(new UrlEncodedFormEntity(values));
		HttpResponse response = httpClient.execute(httpPost);
		
		//Get the content of the response on the page
		HttpEntity entity = response.getEntity();
		
		if(entity!=null) {
			InputStream is = entity.getContent();
			String jsonString = convertStreamToString(is);
			
			LOGGER.log(Level.INFO,"jsonString: " + jsonString);
			
			Type collectionType = ServerData.class;
			responseData = gson.fromJson(jsonString, collectionType);
			
			LOGGER.log(Level.INFO,"responseData ID: " + responseData.getId());
			
			//Update the ID of the task
			newTask.setId(responseData.getId());
			
			
		}
		EntityUtils.consume(entity);
		
		
		//After we retrieve the id we will update the task with the new id
		return updateTask(responseData.getId(),newTask);

		
		
	} catch (Exception e) {
		LOGGER.log(Level.SEVERE,e.getMessage());
		e.printStackTrace();
	}

	return Code.FAILURE;
		
	}

	/**
	 * This will retrieve all tasks by using Gson to
	 * retrieve the ids of each task from the server.
	 * The results will be stored in a ServerData object
	 * and the ids will be added to an array.
	 * 
	 * After retrieving all the ids, we use the
	 * internal getTask(String id) method to get 
	 * the task and add it to the tasks array
	 * 
	 * @return	An ArrayList<Task>. The list will be empty
	 * 			if there are no tasks
	 */
	public List<Task> getAllTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		ArrayList<ServerData> ids = new ArrayList<ServerData>();
		
		
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
				Type collectionType = new TypeToken<Collection<ServerData>>() {}.getType();
				ids = gson.fromJson(jsonString, collectionType);
				//List the id's
				for(ServerData i : ids) {
					LOGGER.log(Level.INFO,"ID: " + i.getId());
					tasks.add(getTask(i.getId()));
				}
			}
			
			LOGGER.log(Level.INFO,ids.toString());
			EntityUtils.consume(entity);
			
			
			
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,ex.getMessage());
			ex.printStackTrace();
		}
		
		for(Task t : tasks) {
			LOGGER.log(Level.INFO,"Task: " + t.toString());
		}
		
		return tasks;
		
	}
	
	/**
	 * This will retrieve a task with the given id. 
	 * This is used internally online and should not
	 * be used outside of this class. It is used in 
	 * conjunction with getAllTasks()
	 * 
	 * @param taskId		The id of the task
	 * 
	 * @return The task with the id or null if no such task exists
	 */
	private Task getTask(String id) {
		Task newTask = new Task();
		try {
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
				
				ServerData data = gson.fromJson(jsonString, ServerData.class);
				newTask = data.getContent();
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

	
	/**
	 * This adds a submission to an existing task
	 * 
	 * @param taskId	The id of the task
	 * @param submission	The Submission object
	 * 
	 * @return Code.FAILURE or Code.SUCCESS
	 */
	public Code addSubmission(String taskId, Submission submission) {
		// get latest task via taskId
		Task newTask = getTask(taskId);
		
		// add submission to task
		ArrayList<Submission> submissions = newTask.getSubmissions();
		submissions.add(submission);
		
		//Update task 
		return updateTask(taskId,newTask);

	}
	
	
	/**
	 * This will remove the task from the server given a specified
	 * task id.
	 * 
	 * @param taskId	the id of the task to be removed
	 * 
	 * @return Code.SUCCESS or Code.FAILURE
	 */
	public Code deleteTask(String taskId) {
		List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();

		values.add(new BasicNameValuePair("action","remove"));
		values.add(new BasicNameValuePair("id",taskId));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(values));
			HttpResponse response = httpClient.execute(httpPost);
			EntityUtils.consume(response.getEntity());
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				return Code.SUCCESS;
		} catch (Exception ex) {
			//TODO: Maybe add some exception handling. Not too sure what to do besides
			//return a failure code though.
			LOGGER.log(Level.SEVERE,ex.getMessage());
		}
		
		return Code.FAILURE;


	}
	
	/**
	 * This will update a task with a new task and replace it
	 * on the server
	 * 
	 * @param taskId		The id of the task
	 * @param task			The Task object to replace the current one
	 * 
	 * @return Code.FAILURE or Code.SUCCESS
	 */
	public Code updateTask(String id, Task task) {
		try {
		List <BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();
		values = new ArrayList<BasicNameValuePair>();
		
		values.add(new BasicNameValuePair("action","update"));
		values.add(new BasicNameValuePair("id",id));
		values.add(new BasicNameValuePair("content",gson.toJson(task)));
		
		httpPost.setEntity(new UrlEncodedFormEntity(values));
		HttpResponse response = httpClient.execute(httpPost);
	
		
		int statusCode = response.getStatusLine().getStatusCode();
		EntityUtils.consume(response.getEntity());
		if(statusCode==HttpStatus.SC_OK)
			return Code.SUCCESS;
		} catch (Exception ex) {
			//do nothing. It will return Code.FAILURE
			LOGGER.log(Level.SEVERE,ex.getMessage());
		}
		
		return Code.FAILURE;
		
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
