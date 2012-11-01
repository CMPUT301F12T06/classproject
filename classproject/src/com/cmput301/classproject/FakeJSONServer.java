package com.cmput301.classproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cmput301.classproject.JSONServer.Code;

public class FakeJSONServer {

	private ArrayList<Task> tasks = new ArrayList<Task>();

	public FakeJSONServer() {
		tasks.clear();
		tasks.add(new Task("Bacon Pictures", "Get me bacon pics", "DEADBEAF",
				Submission.ACCESS_PHOTO | Submission.ACCESS_TEXT, true));
		tasks.add(new Task("Bacon Panckake Audio", "Get me bacon pancake audio", "Joe",
				Submission.ACCESS_AUDIO, true));
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
