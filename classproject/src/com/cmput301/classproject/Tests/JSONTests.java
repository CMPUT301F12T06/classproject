package com.cmput301.classproject.Tests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.cmput301.classproject.Model.JSONServer;


public class JSONTests {

	@Test
	public void testGoodConnection() throws Exception {
		JSONServer singleton = JSONServer.getInstance();
		assertTrue(singleton.isConnected());
	}
	
	@Test
	public void testBadConnection() {
	}
	
	@Test
	public void testAddTask() {
		
	}
	
	@Test
	public void getTasks() {
	
	}
	
	@Test
	public void sync() {
	
	}
	
	@Test
	public void addSubmission() {
	
	}
}
