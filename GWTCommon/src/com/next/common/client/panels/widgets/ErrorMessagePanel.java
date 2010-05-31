package com.next.common.client.panels.widgets;

import com.google.gwt.user.client.ui.HTML;

public class ErrorMessagePanel extends HTML {

	public ErrorMessagePanel()
	{
	}
	public void setText(String message)
	{
		this.setHTML(message);
	}
}
