package com.app.music.client.callback;

import com.app.music.client.exceptions.ClientException;
import com.app.music.client.manager.WorkingDisplayManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class DefaultAsyncCallback<T> implements AsyncCallback<T> {

	public abstract void onServiceFailure(Throwable caught);
	public abstract void onServiceSuccess(T result);
	
	private String displayCordinatorKey;
/*	private final static LoginPopupBox lpBox = new LoginPopupBox();*/
	
	public DefaultAsyncCallback(String key)
	{
		displayCordinatorKey = key;
		try 
		{
			if(displayCordinatorKey != null)
				WorkingDisplayManager.getWorking().add( displayCordinatorKey, WorkingDisplayManager.SELECT);
		} catch (ClientException e) 
		{
			if(displayCordinatorKey != null)
				WorkingDisplayManager.getWorking().remove(displayCordinatorKey);
		}
	}
	
	public final void onFailure(Throwable caught) {
		try
		{
			//You can handle few generic exceptiions here.
			//like SessionExpiredException
			/*
			 * if(caught instanceof SessionExpiryException)
			{
				if(this.loginService !=null)
				{	
					if (!lpBox.isVisible()) {
						lpBox.setSize(300, 200);
						lpBox.setTitle("Session Expired. Login Again");
						lpBox.show(this.loginService);
					}
				}
			}*/
			onServiceFailure(caught);
		}
		catch(Exception ex)
		{
			
		}
		hideWorkingPopup();

	}
	
	public final void onSuccess(T result) {
		try
		{
			onServiceSuccess(result);
		}
		catch(Exception ex)
		{
			GWT.log("Failed to process sucess", ex);
		}
		
		hideWorkingPopup();
	}
	
	private void hideWorkingPopup()
	{
		if(WorkingDisplayManager.getWorking() != null)
			if(displayCordinatorKey != null)
				WorkingDisplayManager.getWorking().remove(displayCordinatorKey);
	}
}
