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

This class provides the JUnit test cases to test the functionality of the Submission class.

@author Clinton Wong
 **/

package com.cmput301.classproject.Tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.UI.AddSubmissionActivity.SubmissionPermission;

public class SubmissionTests {

	private Submission mySubmission = new Submission("test submission",
			"fake author", "my text", null, SubmissionPermission.Public,
			(new Date()).getTime());
	
	@Test
	public void testToString() {
		assertFalse(mySubmission.toString().isEmpty());
	}

	@Test
	public void testGetAuthor() {
		assertTrue(mySubmission.getAuthor().equals("fake author"));
	}
	
	@Test
	public void testGetSummary() {
		assertTrue(mySubmission.getSummary().equals("test submission"));
	}
	
	@Test
	public void testGetTextSubmission() {
		assertTrue(mySubmission.getTextSubmission().equals("my text"));
	}
	
	@Test
	public void testGetTimeStamp() {
		assertTrue(mySubmission.getTimestamp() > 0);
	}
	
	@Test
	public void testGetAccess() {
		assertTrue(mySubmission.getAccess().equals(SubmissionPermission.Public));
	}
	
}
