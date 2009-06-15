package com.app.music.client.beans;


public class FunctionBean extends BaseBean{
	private String name;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String functionName) {
		this.name = functionName;
	}

}
