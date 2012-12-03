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

import android.graphics.Bitmap;

import com.cmput301.classproject.Model.Submission;
/*
 * This test class tests all the methods of the Submission.java class.
 * The tests are tabulated on the Wiki, under Project Part 4 Testing.
 * The table is labeled "Error Guessing - Submissions".
 */
public class SubmissionTests {

	private Submission mySubmission = new Submission("test submission",
			"fake author", "my text", null, Submission.Permission.Public,
			(new Date()).getTime());

	@Test
	/*
	 * Tests the toString() function of the submission.
	 */
	public void testToString() {
		assertTrue(mySubmission.toString().equals("test submission"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Submissions 1
	 * Tests retrieving the author from the submission.
	 */
	public void testGetAuthor() {
		assertTrue(mySubmission.getAuthor().equals("fake author"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Submission 2
	 * Tests retrieving the summary from the submission.
	 */
	public void testGetSummary() {
		assertTrue(mySubmission.getSummary().equals("test submission"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Submission 3
	 * Tests adding and deleting photos.
	 */
	public void testAlterPhotos() {
		Bitmap newBitmap = Bitmap.createBitmap(0, 0, null);
		int currentPhotos = mySubmission.getImages().size();
		mySubmission.getImages().add(newBitmap);
		int nowPhotos = mySubmission.getImages().size();
		assertTrue(currentPhotos < nowPhotos);
		mySubmission.getImages().remove(newBitmap);
		int lessPhotos = mySubmission.getImages().size();
		assertTrue(lessPhotos < nowPhotos);
	}
	
	
	@Test
	/*
	 * Test ID: Error Guessing - Submission 4
	 * Tests retrieving the text component of the submission.
	 */
	public void testGetTextSubmission() {
		assertTrue(mySubmission.getTextSubmission().equals("my text"));
	}

	@Test
	/*
	 * Test ID: Error Guessing - Submission 5
	 * Tests retrieving the time stamp of the submission.
	 */
	public void testGetTimeStamp() {
		assertTrue(mySubmission.getTimestamp() > 0);
	}

	@Test
	/*
	 * Test ID: Error Guessing - Submission 6
	 * Tests getting the access of the submission.
	 */
	public void testGetAccess() {
		assertTrue(mySubmission.getAccess()
				.equals(Submission.Permission.Public));
	}

}
