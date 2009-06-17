package com.next.common.client.listener;

import com.google.gwt.user.client.Command;
import com.next.common.client.manager.ScreenManager;

public class MenuClickListener implements Command{

	private String menuItemtext;
	public MenuClickListener(String pMenuItemtext)
	{
		menuItemtext = pMenuItemtext;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ScreenManager.showMenuPanel(menuItemtext);
	}

}
