package com.next.common.client.session;

import com.next.common.client.beans.FunctionBean;
import com.next.common.client.beans.UserBean;

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
