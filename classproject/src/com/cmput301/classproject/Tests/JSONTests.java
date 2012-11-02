package com.cmput301.classproject.Tests;

import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.cmput301.classproject.Model.JSONServer;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;


public class JSONTests {

	@Test
	public void testGoodConnection() throws Exception {
		assertTrue(JSONServer.getInstance().isConnected());
	}
	
	@Test
	public void testBadConnection() {
		HttpPost post = new HttpPost("http://fakeAddress");
		assertFalse(JSONServer.getInstance().isConnected(post));
	}
	
	@Test
	public void testAddTask() {
		Task task1 = new Task("Apple pictures", "Get me apple pics",
				"DEADBEAF", Submission.ACCESS_PHOTO | Submission.ACCESS_TEXT,
				true);
		JSONServer.getInstance().addTask(task1);
	}
	
	@Test
	public void getTasks() {
		JSONServer.getInstance().getAllTasks();
	}
	
	@Test
	public void sync() {
	
	}
	
	@Test
	public void addSubmission() {
	
	}
}
