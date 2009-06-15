package com.app.music.client.beans;


public class RoleBean extends BaseBean {

	private String name;
	private String description;
	private FunctionBean[] permissions;
	
	public FunctionBean[] getPermissions() {
		return permissions;
	}
	public void setPermissions(FunctionBean[] permissions) {
		this.permissions = permissions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
