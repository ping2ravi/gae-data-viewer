package com.next.common.client.panels.generic;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.datepicker.client.DatePicker;

public class MyDatePicker extends HorizontalPanel implements ClickHandler{

	private TextBox dateField;
	private Button datePickerButton;
	public MyDatePicker()
	{
		dateField = new TextBox();
		dateField.setEnabled(false);
		datePickerButton = new Button("..");
		datePickerButton.addClickHandler(this);
		this.add(dateField);
		this.add(datePickerButton);
	}
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(datePickerButton))
		{
			final PopupPanel pp = new PopupPanel();
			DatePicker datePicker = new DatePicker();
			datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
				@Override
			      public void onValueChange(ValueChangeEvent<Date> event) {
			        Date date = event.getValue();
			        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
			        dateField.setText(dateString);
			        pp.hide();
			        
			      }
			    });
			pp.setWidget(datePicker);
			pp.setAutoHideEnabled(true);
			pp.showRelativeTo((UIObject)event.getSource());
		}
		
	}
	public String getDate()
	{
		return dateField.getText();
	}
	public void setDate(String dateValue) 
	{
		dateField.setText(dateValue);
	}
	public void clear()
	{
		dateField.setText("");
	}
	public void setEnabled(boolean enabled)
	{
		datePickerButton.setEnabled(enabled);
	}
}
