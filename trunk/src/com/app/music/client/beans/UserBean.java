package com.app.music.client.beans;

public class UserBean extends BaseBean {
    private String emailId;
    private String nickName;
	private FunctionBean[] functions;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public FunctionBean[] getFunctions() {
		return functions;
	}
	public void setFunctions(FunctionBean[] functions) {
		this.functions = functions;
	}
	
}