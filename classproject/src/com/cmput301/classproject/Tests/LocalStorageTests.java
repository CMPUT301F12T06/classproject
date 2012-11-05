package com.cmput301.classproject.Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.cmput301.classproject.Model.LocalStorage;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;

public class LocalStorageTests {

	LocalStorage localStorage = LocalStorage.getInstance();
	
	@Test
	public void testCreateLocalStorage() {
		assertTrue(localStorage != null);
	}
	
	@Test
	public void testNoEntries(){
		assertTrue(localStorage.load().isEmpty());
	}

	@Test
	public void testAddEntry(){
		Task myTask = new Task("JUnit Local Task", "Description for Test Task", "Test Task Creator", Submission.ACCESS_PHOTO, true);
		myTask.setId("JUnit Local Task Test");
		List<Task> tasksList = localStorage.load();
		int beforeCount = tasksList.size();
		tasksList.add(myTask);
		localStorage.save(tasksList);
		int afterCount = localStorage.load().size();
		assertTrue(afterCount > beforeCount);
	}
	
}
