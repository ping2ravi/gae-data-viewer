package com.next.common.client.panels.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.next.common.client.panels.widgets.ByteTextBox;
import com.next.common.client.panels.widgets.CharTextBox;
import com.next.common.client.panels.widgets.DoubleTextBox;
import com.next.common.client.panels.widgets.FloatTextBox;
import com.next.common.client.panels.widgets.IntegerTextBox;
import com.next.common.client.panels.widgets.LongTextBox;
import com.next.common.client.panels.widgets.ShortTextBox;

public class FieldTypes {

	/*
	public static final String TEXT_BOX = "TEXT_BOX";
	public static final String LIST_BOX = "LIST_BOX";
	public static final String DATE_PICKER_BOX = "DATE_PICKER_BOX";
	public static final String BOOLEAN_LIST_BOX = "BOOLEAN_LIST_BOX";
	*/
	
	public static Widget getWidget(String type,boolean enabled)
	{
		if(type == null )
			Window.alert("Type can not be null");
		GWT.log("Type = "+ type, null);
		if(byte.class.getName().equals(type) || Byte.class.getName().equals(type))
		{
			ByteTextBox returnWidget = new ByteTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(short.class.getName().equals(type) || Short.class.getName().equals(type))
		{
			ShortTextBox returnWidget = new ShortTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(int.class.getName().equals(type) || Integer.class.getName().equals(type))
		{
			IntegerTextBox returnWidget = new IntegerTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(long.class.getName().equals(type) || Long.class.getName().equals(type))
		{
			LongTextBox returnWidget = new LongTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(float.class.getName().equals(type) || Float.class.getName().equals(type))
		{
			FloatTextBox returnWidget = new FloatTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(double.class.getName().equals(type) || Double.class.getName().equals(type))
		{
			DoubleTextBox returnWidget = new DoubleTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(boolean.class.getName().equals(type) || Boolean.class.getName().equals(type))
		{
			ListBox returnWidget = new ListBox();
			returnWidget.setEnabled(enabled);
            returnWidget.addItem(" ", " ");
            returnWidget.addItem("true", "true");
            returnWidget.addItem("false", "false");
			return returnWidget;
		}
		if(char.class.getName().equals(type) || Character.class.getName().equals(type))
		{
			CharTextBox returnWidget = new CharTextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(java.util.Date.class.getName().equals(type))
		{
			DateBox returnWidget = new DateBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}
		if(java.lang.String.class.getName().equals(type))
		{
			TextBox returnWidget = new TextBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}

		/*
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
			DateBox returnWidget = new DateBox();
			returnWidget.setEnabled(enabled);
			return returnWidget;
		}*/
		Window.alert("Error : Type " + type+" is not supported");
		return null;
		
	}
	public static String getWidgetValue(String type,Widget widget)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(byte.class.getName().equals(type) || Byte.class.getName().equals(type))
		{
			ByteTextBox textBox = (ByteTextBox)widget;
			return textBox.getText();
		}
		if(short.class.getName().equals(type) || Short.class.getName().equals(type))
		{
			ShortTextBox textBox = (ShortTextBox)widget;
			return textBox.getText();
		}
		if(int.class.getName().equals(type) || Integer.class.getName().equals(type))
		{
			IntegerTextBox textBox = (IntegerTextBox)widget;
			return textBox.getText();
		}
		if(long.class.getName().equals(type) || Long.class.getName().equals(type))
		{
			LongTextBox textBox = (LongTextBox)widget;
			return textBox.getText();
		}
		if(float.class.getName().equals(type) || Float.class.getName().equals(type))
		{
			FloatTextBox textBox = (FloatTextBox)widget;
			return textBox.getText();
		}
		if(double.class.getName().equals(type) || Double.class.getName().equals(type))
		{
			DoubleTextBox textBox = (DoubleTextBox)widget;
			return textBox.getText();
		}
		if(boolean.class.getName().equals(type) || Boolean.class.getName().equals(type))
		{
			ListBox listBox = (ListBox)widget;
			return listBox.getValue(listBox.getSelectedIndex());
		}
		if(char.class.getName().equals(type) || Character.class.getName().equals(type))
		{
			CharTextBox textBox = (CharTextBox)widget;
			return textBox.getText();
		}
		if(java.util.Date.class.getName().equals(type))
		{
			DateBox returnWidget = (DateBox)widget;
			return returnWidget.getTextBox().getText();
		}
		if(java.lang.String.class.getName().equals(type))
		{
			TextBox textBox = (TextBox)widget;
			return textBox.getText();
		}
		/*
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
		*/
		Window.alert("Error : Type " + type+" is not supported");
		return null;
		
	}	
	public static void clearWidget(String type,Widget widget)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(byte.class.getName().equals(type) || Byte.class.getName().equals(type))
		{
			ByteTextBox textBox = (ByteTextBox)widget;
			textBox.setText("");
			return;
		}
		if(short.class.getName().equals(type) || Short.class.getName().equals(type))
		{
			ShortTextBox textBox = (ShortTextBox)widget;
			textBox.setText("");
			return;
		}
		if(int.class.getName().equals(type) || Integer.class.getName().equals(type))
		{
			IntegerTextBox textBox = (IntegerTextBox)widget;
			textBox.setText("");
			return;
		}
		if(long.class.getName().equals(type) || Long.class.getName().equals(type))
		{
			LongTextBox textBox = (LongTextBox)widget;
			textBox.setText("");
			return;
		}
		if(float.class.getName().equals(type) || Float.class.getName().equals(type))
		{
			FloatTextBox textBox = (FloatTextBox)widget;
			textBox.setText("");
			return;
		}
		if(double.class.getName().equals(type) || Double.class.getName().equals(type))
		{
			DoubleTextBox textBox = (DoubleTextBox)widget;
			textBox.setText("");
			return;
		}
		if(boolean.class.getName().equals(type) || Boolean.class.getName().equals(type))
		{
			ListBox listBox = (ListBox)widget;
			listBox.setSelectedIndex(0);
			return;
		}
		if(char.class.getName().equals(type) || Character.class.getName().equals(type))
		{
			CharTextBox textBox = (CharTextBox)widget;
			textBox.setText("");
			return;
		}
		if(java.util.Date.class.getName().equals(type))
		{
			DateBox returnWidget = (DateBox)widget;
			returnWidget.getTextBox().setText("");
			return ;
		}
		if(java.lang.String.class.getName().equals(type))
		{
			TextBox textBox = (TextBox)widget;
			textBox.setText("");
			return;
		}
		/*
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
		}*/
		Window.alert("Error : Type " + type+" is not supported");
		
	}	
	public static void setWidgetValue(String type,Widget widget,String value)
	{
		if(type == null )
			Window.alert("Type can not be null");
		if(byte.class.getName().equals(type) || Byte.class.getName().equals(type))
		{
			ByteTextBox textBox = (ByteTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(short.class.getName().equals(type) || Short.class.getName().equals(type))
		{
			ShortTextBox textBox = (ShortTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(int.class.getName().equals(type) || Integer.class.getName().equals(type))
		{
			IntegerTextBox textBox = (IntegerTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(long.class.getName().equals(type) || Long.class.getName().equals(type))
		{
			LongTextBox textBox = (LongTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(float.class.getName().equals(type) || Float.class.getName().equals(type))
		{
			FloatTextBox textBox = (FloatTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(double.class.getName().equals(type) || Double.class.getName().equals(type))
		{
			DoubleTextBox textBox = (DoubleTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(boolean.class.getName().equals(type) || Boolean.class.getName().equals(type))
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
		if(char.class.getName().equals(type) || Character.class.getName().equals(type))
		{
			CharTextBox textBox = (CharTextBox)widget;
			textBox.setText(value);
			return;
		}
		if(java.util.Date.class.getName().equals(type))
		{
			DateBox returnWidget = (DateBox)widget;
			returnWidget.getTextBox().setText(value);
			return ;
		}
		if(java.lang.String.class.getName().equals(type))
		{
			TextBox textBox = (TextBox)widget;
			textBox.setText(value);
			return;
		}
		/*
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
		}*/
		Window.alert("Error : Type " + type+" is not supported");
		
	}	
}
