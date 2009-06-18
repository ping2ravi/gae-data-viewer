package com.next.common.client.panels.generic;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class CommonSavePanel extends DecoratedPopupPanel implements UiErrorPanel{

	private HTML errorText = new HTML();
	private FieldsBean[] fields;
	private Map<String, Widget> elements;
	private CommonPanel panel;
	public CommonSavePanel(CommonPanel panel,FieldsBean[] fields)
	{	
		super(true);
		this.panel = panel;
		super.setWidth("400px");
		super.setHeight("400px");
		this.fields = fields;
		this.ensureDebugId("cwBasicPopup-simplePopup");
		elements = new HashMap<String, Widget>();
	}
	public void createSavePanel()
	{
		if(fields == null || fields.length <= 0)
			return ;

		int rowCount = fields.length;
		Grid allControls = new Grid(rowCount,4);
		int row = 0;
		int col = 0;
		Widget widget;
		for(int i=0;i<fields.length;i++)
		{
			widget = new Label(fields[i].getLabelText());
			allControls.setWidget(row, col, widget);
			col++;
			widget = FieldTypes.getWidget(fields[i].getType(),fields[i].isEnabled());
			if(FieldTypes.LIST_BOX.equals(fields[i].getType()))
			{
				if(fields[i].getListBoxPopulator() != null)
					fields[i].getListBoxPopulator().populateListBox((ListBox)widget,null);
				else
					Window.alert("No List box populator is defined");
			}
				
			elements.put(fields[i].getFieldName(), widget);
			allControls.setWidget(row, col, widget);
			col = 0;
			row++;
		}
		Grid searchPannel = new Grid(3, 1);
		searchPannel.setWidget(0,0, errorText);
		searchPannel.setWidget(1, 0, allControls);
		this.add(searchPannel);
	}
	public void populateData(Map<String,String> data)
	{
		Widget widget;
		String value;
		for(int i=0;i<fields.length;i++)
		{
			
			widget = elements.get(fields[i].getFieldName());
			value = data.get(fields[i].getFieldName());
			FieldTypes.setWidgetValue(fields[i].getType(),widget,value);
		}
		
	}
	public void onSave() {
		Map<String,String> data = new HashMap<String, String>();
		Widget widget;
		String value;
		for(int i=0;i<fields.length;i++)
		{
			
			widget = elements.get(fields[i].getFieldName());
			value = FieldTypes.getWidgetValue(fields[i].getType(),widget);
			data.put(fields[i].getFieldName(),value);
		}		
		Object obj = panel.getObjectFromParams(data);
		if(panel.validateObject(obj,this))
		{
			panel.onSave(obj);
		}
	}
	@Override
	public void setErrorMessage(String message) {
		errorText.setHTML(message);
		
	}

	
}
