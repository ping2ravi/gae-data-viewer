package com.app.music.client.manager.helper;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class WorkingPopupWindow {

	DecoratedPopupPanel loadMask = null;
	
	public WorkingPopupWindow() {
		//createLoadMask("Working...");
	}
	
	private void createLoadMask(String message) {	
		//loadMask = new LoadMask(RootPanel.getBodyElement(), message);
		loadMask = new DecoratedPopupPanel();
		loadMask.show();
	}
	
	public void setText(String message) {
		if(loadMask != null)
			loadMask.hide();
		createLoadMask(message);
	}
	
	public void hide() {
		if(loadMask != null)
			loadMask.hide();
	}	
}
