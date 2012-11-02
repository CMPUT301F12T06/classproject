package com.cmput301.classproject.Model;

public class ServerData {
	
	public ServerData() {}
	
	public Task getContent() {
		return content;
	}

	public void setContent(Task content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Task content;
	private String id;

}