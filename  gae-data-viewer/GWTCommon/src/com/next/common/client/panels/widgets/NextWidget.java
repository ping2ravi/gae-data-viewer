package com.next.common.client.panels.widgets;

public interface NextWidget {

	public void setText(Object obj);
	public String getText();
	public boolean validate();
	public void setEnabled(boolean val);
}
