package com.next.common.client.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.next.common.client.DBServiceAsync;

public class ServiceFactory {
	private static DBServiceAsync dbService;
	public static DBServiceAsync getDBService()
	{
		if(dbService == null)
		{
			dbService = (DBServiceAsync) GWT.create(com.next.common.client.DBService.class);
			((ServiceDefTarget) dbService).setServiceEntryPoint(GWT.getModuleBaseURL() + "common");
		}
		return dbService;
	}
}
