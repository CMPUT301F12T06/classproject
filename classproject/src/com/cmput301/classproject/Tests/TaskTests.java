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

This class provides the JUnit test cases to test the functionality of the Task class.

@author Clinton Wong
 **/

package com.cmput301.classproject.Tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.Model.Task;
/*
 * This test class tests all the methods of the Tasks.java class.
 * The tests are tabulated on the Wiki, under Project Part 4 Testing.
 * The table is labeled "Error Guessing - Tasks".
 */
public class TaskTests {
	private Task myTask = new Task("JUnit Test Task", "JUnit description",
			"JUnit Creator", Submission.ACCESS_TEXT, true);

	@Test
	/*
	 * Tests retrieving the string form of the Task.
	 */
	public void testTaskToString() {
		assertTrue(myTask.toString().equals("Task: " + "JUnit Test Task"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Tasks 1
	 * Tests setting the ID of a task.
	 */
	public void testSetId() {
		myTask.setId("Test ID");
		assertTrue(myTask.getId().equals("Test ID"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Tasks 2
	 * Tests getting the creator of a task.
	 */
	public void testGetCreator() {
		assertTrue(myTask.getCreator().equals("JUnit Creator"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Tasks 3
	 * Tests getting the submissions of a task.
	 */
	public void testGetSubmissions() {
		assertTrue(myTask.getSubmissions().isEmpty());
	}

	@Test
	/*
	 * Test ID: Error Guessing - Tasks 4
	 * Tests getting the name of the task.
	 */
	public void testGetName() {
		assertTrue(myTask.getName().equals("JUnit Test Task"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Tasks 5
	 * Tests retrieving the description of the task.
	 */
	public void testGetDescription() {
		assertTrue(myTask.getDescription().equals("JUnit description"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Task 6
	 * Tests retrieving the task requirements.
	 */
	public void testGetRequires() {
		assertTrue(myTask.getRequires() == Submission.ACCESS_TEXT);
	}

	@Test
	/*
	 * Test ID: Error Guessing - Task 7
	 * Tests if the public access boolean value works.
	 */
	public void testIsPublicAccess() {
		assertTrue(myTask.isPublicAccess());
	}

	@Test
	/*
	 * Test ID: Error Guessing - Task 8
	 * Tests adding a submission to the task.
	 */
	public void testAddSubmission() {
		int beforeCount = myTask.getSubmissions().size();

		Submission newSubmission = new Submission("test submission",
				"fake author", "my text", null, Submission.Permission.Public,
				(new Date()).getTime());

		myTask.addSubmission(newSubmission);

		int afterCount = myTask.getSubmissions().size();
		assertTrue(afterCount > beforeCount);
	}

}
