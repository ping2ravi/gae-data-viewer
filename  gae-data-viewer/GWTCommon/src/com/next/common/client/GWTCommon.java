package com.next.common.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.callback.DefaultAsyncCallback;
import com.next.common.client.factory.ServiceFactory;
import com.next.common.client.panels.FunctionPanel;
import com.next.common.client.panels.generic.CommonPanel;

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
		DefaultAsyncCallback<EntityDescriptionBean[]> callback = new DefaultAsyncCallback<EntityDescriptionBean[]>(key){
			@Override
			public void onServiceFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onServiceSuccess(EntityDescriptionBean[] result) {
				// TODO Auto-generated method stub
				
			}
		};
		ServiceFactory.getDBService().getAllEntities(callback);
		FunctionPanel functionPanel = new FunctionPanel();
		functionPanel.createPanel();
		functionPanel.add(new Label("Sharma"));
		RootPanel.get("content").add(functionPanel);
	}
}
