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
import com.cmput301.classproject.UI.AddSubmissionActivity.SubmissionPermission;

public class TaskTests {
	private Task myTask = new Task("JUnit Test Task", "JUnit description", "JUnit Creator", Submission.ACCESS_TEXT, true);
	
	@Test
	public void testTaskToString() {
		assertFalse(myTask.toString().isEmpty());
	}
	
	@Test
	public void testSetId() {
		myTask.setId("Test ID");
		assertTrue(myTask.getId().equals("Test ID"));
	}
	
	@Test
	public void testGetCreator() {
		assertTrue(myTask.getCreator().equals("JUnit Creator"));
	}
	
	@Test
	public void testGetSubmissions() {
		assertTrue(myTask.getSubmissions().isEmpty());
	}
	
	@Test
	public void testGetName() {
		assertTrue(myTask.getName().equals("JUnit Test Task"));
	}
	
	@Test
	public void testGetDescription() {
		assertTrue(myTask.getDescription().equals("JUnit description"));
	}
	
	@Test
	public void testGetRequires() {
		assertTrue(myTask.getRequires() == Submission.ACCESS_TEXT);
	}
	
	@Test
	public void testIsPublicAccess() {
		assertTrue(myTask.isPublicAccess());
	}
	
	@Test
	public void testAddSubmission() {
		int beforeCount = myTask.getSubmissions().size();
		
		Submission newSubmission = new Submission("test submission",
				"fake author", "my text", null, SubmissionPermission.Public,
				(new Date()).getTime());
		
		myTask.addSubmission(newSubmission);
		
		int afterCount = myTask.getSubmissions().size();
		assertTrue(afterCount > beforeCount);
	}

}
