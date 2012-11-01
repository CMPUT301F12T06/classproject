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
		task2.addSubmission(new Submission("SUBMISSION YYYY"));

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
