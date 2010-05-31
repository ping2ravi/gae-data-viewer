package com.next.viewer.client.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.next.viewer.client.DBServiceAsync;

public class ServiceFactory {
	private static DBServiceAsync dbService;
	public static DBServiceAsync getDBService()
	{
		if(dbService == null)
		{
			dbService = (DBServiceAsync) GWT.create(com.next.viewer.client.DBService.class);
			((ServiceDefTarget) dbService).setServiceEntryPoint(GWT.getModuleBaseURL() + "dataviewer");
		}
		return dbService;
	}
}
