package com.next.common.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTCommon implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String key = "getAllEntities";
		/*
		DefaultAsyncCallback<EntityDescriptionBean[]> callback = new DefaultAsyncCallback<EntityDescriptionBean[]>(key){
			@Override
			public void onServiceFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("failed " + caught.getMessage() + ":" + caught);
			}
			@Override
			public void onServiceSuccess(EntityDescriptionBean[] result) {
				ClientCache.setEntityCache(result);
				ScreenManager.createMainScreen(result);
				
			}
		};
		ServiceFactory.getDBService().getAllEntities(callback);
		*/
	}
}
