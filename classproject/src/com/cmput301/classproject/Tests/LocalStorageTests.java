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

This class provides the JUnit test cases to test the functionality of the LocalStorage class.

@author Clinton Wong
 **/

package com.cmput301.classproject.Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.cmput301.classproject.Model.LocalStorage;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;
/*
 * This test class tests all the methods of the LocalStorage.java class.
 * The tests are tabulated on the Wiki, under Project Part 4 Testing.
 * The table is labeled "Error Guessing - LocalStorage".
 */
public class LocalStorageTests {

	LocalStorage localStorage = LocalStorage.getInstance();

	@Test
	/*
	 * Test ID: Error Guessing - LocalStorage 1
	 * Tests creating local storage on the android app.
	 */
	public void testCreateLocalStorage() {
		assertTrue(localStorage != null);
	}

	@Test
	/*
	 * Test ID: Error Guessing - LocalStorage 2
	 * Tests validation of no entries in the local storage.
	 * Should be run right after testCreateLocalStorage.
	 */
	public void testNoEntries() {
		assertTrue(localStorage.loadTasksFromStorage().isEmpty());
	}

	@Test
	/*
	 * Test ID: Error Guessing - LocalStorage 3
	 * Tests adding a task to the local storage.
	 */
	public void testAddEntry() {
		Task myTask = new Task("JUnit Local Task", "Description for Test Task",
				"Test Task Creator", Submission.ACCESS_PHOTO, true);
		myTask.setId("JUnit Local Task Test");
		List<Task> tasksList = localStorage.loadTasksFromStorage();
		int beforeCount = tasksList.size();
		tasksList.add(myTask);
		localStorage.saveTasksFromStorage(tasksList);
		int afterCount = localStorage.loadTasksFromStorage().size();
		assertTrue(afterCount > beforeCount);
	}
}
