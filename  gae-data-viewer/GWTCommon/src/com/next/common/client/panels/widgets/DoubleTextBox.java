package com.next.common.client.panels.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DoubleTextBox extends VerticalPanel implements NextWidget,ChangeHandler{

	TextBox tb = null;
	ErrorMessagePanel message = null;
	private boolean nullable = true;
	public DoubleTextBox()
	{
		tb = new TextBox();
		tb.addChangeHandler(this);
		message = new ErrorMessagePanel();
		this.add(tb);
		this.add(message);
	}
	@Override
	public String getText() {
		return tb.getText();
	}

	@Override
	public void setText(Object obj) {
		if(obj != null )
			tb.setText(String.valueOf(obj));
		else
			tb.setText("");
	}
	@Override
	public boolean validate() {
		String val = tb.getText();
		boolean returnValue = false;
		if(val == null || val.trim().equals(""))
		{
			if(nullable)
				returnValue =  true;
			else
				returnValue = false;
			return returnValue;
		}
		try{
			Double.parseDouble(val);
			returnValue =  true;
		}catch(Exception ex)
		{
			returnValue = false;
		}
		return returnValue;
		
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public boolean isNullable() {
		return nullable;
	}
	@Override
	public void onChange(ChangeEvent event) {
		if(event.getSource().equals(tb))
		{
			if(validate())
			{
				message.setVisible(false);
				message.setText("");
			}
			else
			{
				message.setVisible(true);
				message.setText("Invalid Double Value");
			}
		}
	}
	@Override
	public void setEnabled(boolean val) {
		tb.setEnabled(val);
	}

}
