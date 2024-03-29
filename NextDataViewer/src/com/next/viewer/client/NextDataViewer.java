package com.next.viewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.next.common.client.callback.DefaultAsyncCallback;
import com.next.viewer.client.beans.EntityDescriptionBean;
import com.next.viewer.client.factory.ServiceFactory;
import com.next.viewer.client.manager.ScreenManager;
import com.next.viewer.client.session.ClientCache;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NextDataViewer implements EntryPoint {
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
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ScreenManager.init();
	}
}
