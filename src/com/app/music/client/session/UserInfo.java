package com.app.music.client.session;

import com.app.music.client.beans.FunctionBean;
import com.app.music.client.beans.UserBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class UserInfo {

	private static UserInfo instance;
	private UserBean user;
	public static UserInfo getInstance()
	{
		if(instance == null)
		{
			instance = new UserInfo();
		}
		return instance;
	}
	private UserInfo()
	{
		
	}
	public UserBean getUser() {
		return user;
	}
	public void setUserInfoWrapper(UserBean user) {
		this.user = user;
	}
	public boolean isUserAllowedForOperation(String function)
	{
		boolean allowed = false;
		if(user == null)
			allowed = false;
		else
		{
			if(user.getFunctions() != null)
			{
				for(FunctionBean oneFunction:user.getFunctions())
				{
					//GWT.log(function + "=" + oneFunction.getName() , null);
					if(oneFunction.getName().equalsIgnoreCase(function))
						allowed = true;
				}
			}
		}
		return allowed;
	}
	
}
