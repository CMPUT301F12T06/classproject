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
 **/
package com.cmput301.classproject.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cmput301.classproject.Model.JSONServer.Code;

public class FakeJSONServer {

	private ArrayList<Task> tasks = new ArrayList<Task>();

	public FakeJSONServer() {
		tasks.clear();

		Task task1 = new Task("Bacon Pictures", "Get me bacon pics",
				"DEADBEAF", Submission.ACCESS_PHOTO | Submission.ACCESS_TEXT,
				true);
		task1.addSubmission(new Submission("SUBMISSION XXX"));

		Task task2 = new Task("Bacon Panckake Audio",
				"Get me bacon pancake audio", "Joe", Submission.ACCESS_AUDIO,
				true);
		
		// Creating Submission details
		byte access = 0;
		
		Submission task2Sub = new Submission("SUBMISSION YYYY");
		task2Sub.setAccess(access);
		task2Sub.setAuthor("Pancake McPancakerson");
		task2Sub.setSummary("SampleSubmission1");
		task2Sub.setText("My Text is bacon");
		task2Sub.setTimestamp(20);
		task2.addSubmission(task2Sub);

		addTask(task1);
		addTask(task2);
	}

	public boolean isConnected() {
		return true;
	}

	// generates a random id for a task.
	public Code addTask(Task task) {
		Random r = new Random();
		task.setId(r.nextInt(10000));
		tasks.add(task);
		return Code.SUCCESS;
	}

	public List<Task> getAllTasks() {
		return tasks;
	}

	public Code updateTask(Task t) {

		return Code.SUCCESS;
	}

	public Code sync() {
		return Code.SUCCESS;
	}

	public Code addSubmission(int taskId, Submission submission) {
		for (Task t : tasks) {
			if (t.getId() == taskId) {
				t.addSubmission(submission);
				return Code.SUCCESS;
			}
		}
		return Code.FAILURE;

	}

}
