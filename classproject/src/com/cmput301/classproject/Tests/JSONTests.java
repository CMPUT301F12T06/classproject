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

JSONTests.java consists of the JUnit tests to test the functionality of the
JSONServer

@author Benson Trinh

 **/

package com.cmput301.classproject.Tests;

import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.cmput301.classproject.Model.Tasks.JSONServer;
import com.cmput301.classproject.Model.Tasks.JSONServer.Code;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;

public class JSONTests {

	JSONServer server = JSONServer.getInstance();

	@Test
	public void testGoodConnection() throws Exception {
		assertTrue(server.isConnected());
	}

	@Test
	public void testBadConnection() {
		HttpPost post = new HttpPost("http://fakeAddress");
		assertFalse(server.isConnected(post));
	}

	@Test
	public void testAddTask() {
		Task task1 = new Task("JUNIT Test", "Testing", "ABC",
				Submission.ACCESS_PHOTO | Submission.ACCESS_TEXT, true);
		// Test creation
		assertTrue(server.addTask(task1) == Code.SUCCESS);

		// Cleanup
		Task newTask = server.getLatestTask();
		// Test deletion
		assertTrue(server.deleteTask(newTask) == Code.SUCCESS);

	}

	@Test
	public void getTasks() {
		assertTrue(server.getAllTasks().size() > 0);
	}

	@Ignore("Only ran if the server is empty. We do not want the nuke it")
	@Test
	public void getEmptyTasks() {
		assertTrue(server.getAllTasks().size() == 0);
	}

	@Test
	public void addSubmission() {
		Task task1 = new Task("JUNIT Test", "Testing", "ABC",
				Submission.ACCESS_PHOTO | Submission.ACCESS_TEXT, true);

		Submission submission = new Submission("test submission",
				"fake author", "my text", null, Submission.Permission.Public,
				(new Date()).getTime());
		// add task
		server.addTask(task1);

		// add submission to task
		Task newTask = server.getLatestTask();
		assertTrue(server.addSubmission(newTask.getId(), submission) == Code.SUCCESS);

		// cleanup
		server.deleteTask(newTask);
	}
}
