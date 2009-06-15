package com.app.music.client.factory;

import com.app.music.client.JaatMusicAdminAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ServiceFactory {
	private static JaatMusicAdminAsync loginService;
	public static JaatMusicAdminAsync getJaatMusicAdminService()
	{
		if(loginService == null)
		{
			loginService = (JaatMusicAdminAsync) GWT.create(com.app.music.client.JaatMusicAdmin.class);
			((ServiceDefTarget) loginService).setServiceEntryPoint(GWT.getModuleBaseURL() + "admin");
		}
		return loginService;
	}
}
