package com.next.common.client.panels.generic;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class FieldTypes {

	public static final String TEXT_BOX = "TEXT_BOX";
	public static final String LIST_BOX = "LIST_BOX";
	public static final String DATE_PICKER_BOX = "DATE_PICKER_BOX";
	public static final String BOOLEAN_LIST_BOX = "BOOLEAN_LIST_BOX";
	
	
	public static Widget getWidget(String type,boolean enabled)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(TEXT_BOX.equals(type))
		{
			TextBox returnWidget = new TextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
			
		if(LIST_BOX.equals(type))
		{
			ListBox returnWidget = new ListBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(BOOLEAN_LIST_BOX.equals(type))
		{
			ListBox returnWidget = new ListBox();
			returnWidget.setEnabled(enabled);
            returnWidget.addItem("true", "true");
            returnWidget.addItem("false", "false");
			return returnWidget;
		}
		if(DATE_PICKER_BOX.equals(type))
		{
			//DateBox returnWidget = new DateBox();
			DateBox returnWidget = new DateBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		Window.alert("Error : Type " + type+" is not supported");
		return null;
		
	}
	public static String getWidgetValue(String type,Widget widget)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(TEXT_BOX.equals(type))
		{
			TextBox textBox = (TextBox)widget;
			return textBox.getText();
		}
		if(LIST_BOX.equals(type))
		{
			ListBox listBox = (ListBox)widget;
			return listBox.getValue(listBox.getSelectedIndex());
		}
		if(BOOLEAN_LIST_BOX.equals(type))
		{
			ListBox listBox = (ListBox)widget;
			return listBox.getValue(listBox.getSelectedIndex());
		}
		if(DATE_PICKER_BOX.equals(type))
		{
			DateBox returnWidget = (DateBox)widget;
			return returnWidget.getTextBox().getText();
		}
		Window.alert("Error : Type " + type+" is not supported");
		return null;
		
	}	
	public static void clearWidget(String type,Widget widget)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(TEXT_BOX.equals(type))
		{
			TextBox textBox = (TextBox)widget;
			textBox.setText("");
			return;
		}
		if(LIST_BOX.equals(type))
		{
			ListBox listBox = (ListBox)widget;
			listBox.setSelectedIndex(0);
			return;
		}
		if(BOOLEAN_LIST_BOX.equals(type))
		{
			ListBox listBox = (ListBox)widget;
			listBox.setSelectedIndex(0);
			return;
		}
		if(DATE_PICKER_BOX.equals(type))
		{
			DateBox returnWidget = (DateBox)widget;
			returnWidget.getTextBox().setText("");
			return ;
		}
		Window.alert("Error : Type " + type+" is not supported");
		
	}	
	public static void setWidgetValue(String type,Widget widget,String value)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(TEXT_BOX.equals(type))
		{
			TextBox textBox = (TextBox)widget;
			textBox.setText(value);
			return;
		}
		if(LIST_BOX.equals(type))
		{
			ListBox listBox = (ListBox)widget;
			int total = listBox.getItemCount();
			int selected = 0;
			for(int i=0;i<total;i++)
			{
				if(listBox.getValue(i).trim().equals(value.trim()))
				{
					selected = i;
				}
			}
			listBox.setSelectedIndex(selected);
			return;
		}
		if(BOOLEAN_LIST_BOX.equals(type))
		{
			ListBox listBox = (ListBox)widget;
			int total = listBox.getItemCount();
			int selected = 0;
			for(int i=0;i<total;i++)
			{
				if(listBox.getValue(i).trim().equals(value.trim()))
				{
					selected = i;
				}
			}
			listBox.setSelectedIndex(selected);
			return;
		}
		if(DATE_PICKER_BOX.equals(type))
		{
			DateBox returnWidget = (DateBox)widget;
			returnWidget.getTextBox().setText(value);
			return ;
		}
		Window.alert("Error : Type " + type+" is not supported");
		
	}	
}
