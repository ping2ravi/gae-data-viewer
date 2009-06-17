package com.next.common.client.panels.generic;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class CommonPanel extends VerticalPanel implements ClickHandler{

	private FieldsBean[] fields;
	private Button search = new Button("Search");
	private Button clear = new Button("Clear");
	private Button newButton = new Button("New");
	private HTML errorText = new HTML();
	private Map<String, Widget> searchElements;
	String objectName;
	private Grid resultGrid;
	private VerticalPanel resultPanel;
	public CommonPanel(String objectName)
	{
		this.objectName = objectName;
		searchElements = new HashMap<String, Widget>();
	}
	public Widget createPanel()
	{
		createUIPanel();
		return this;
	}
	public abstract Object[][] convertToObjectArray(Object object);
	public abstract Object getObjectFromParams(Map<String, String> params);
	public abstract boolean validateObject(Object obj,UiErrorPanel parentPanel);
	public abstract void onSave(Object object);
	public abstract void findData(Object object);
	public FieldsBean[] getFields() {
		return fields;
	}
	public void setFields(FieldsBean[] fields) {
		this.fields = fields;
	}
	private void createUIPanel()
	{
		if(fields == null || fields.length <= 0)
		{
			Window.alert("No FIelds found");
		}
		int rowCount = fields.length/2 + 1;

		HorizontalPanel allButtonPanel = new HorizontalPanel();
		Grid buttonPanel = new Grid(1,5);
		search.addClickHandler(this);
		buttonPanel.setWidget(0, 0, search);
		
		clear.addClickHandler(this);
		
		buttonPanel.setWidget(0, 1, clear);
		
		newButton.addClickHandler(this);
		buttonPanel.setWidget(0, 4, newButton);

		allButtonPanel.add(buttonPanel);
		Grid allControls = new Grid(rowCount,4);
		int row = -1;
		int col = 0;
		Widget widget;
		for(int i=0;i<fields.length;i++)
		{
			if(col %4 == 0)
			{
				col = 0;
				row++;
			}
			//Window.alert("Row " + row+",Col="+col);
			widget = new Label(fields[i].getLabelText());
			allControls.setWidget(row, col, widget);
			col++;
			widget = FieldTypes.getWidget(fields[i].getType(),fields[i].isEnabled());
			searchElements.put(fields[i].getFieldName(), widget);
			if(FieldTypes.LIST_BOX.equals(fields[i].getType()))
			{
				if(fields[i].getListBoxPopulator() != null)
					fields[i].getListBoxPopulator().populateListBox((ListBox)widget);
				else
					Window.alert("No List box populator is defined");
			}
			allControls.setWidget(row, col, widget);
			col++;
		}
		
		resultPanel = new VerticalPanel();
		Grid searchPannel = new Grid(4, 1);
		searchPannel.setWidget(0,0, errorText);
		searchPannel.setWidget(1, 0, allControls);
		searchPannel.setWidget(2, 0, allButtonPanel);
		searchPannel.setWidget(3, 0, resultPanel);
		
		this.add(searchPannel);
	}
	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if(sender.equals(this.clear))
		{
			this.clearSearchCriteriaPanel();
		}
		if(sender.equals(this.search))
		{
			//if(UserInfo.getInstance().isUserAllowedForOperation("find" + objectName +"s"))
			//{
				resultPanel.clear();
				resultPanel.add(new Label("Searching Data"));
				Map<String, String> params = getSearchParams();
				//findData(params);
			/*}
			else
			{
				Window.alert("You are not authorized to do this operation");
			}
             */
		}
		if(sender.equals(newButton))
		{
			/*if(UserInfo.getInstance().isUserAllowedForOperation("create" + objectName))
			{*/
				CommonSavePanel savePanel = new CommonSavePanel(this, fields);
				savePanel.createSavePanel();
				savePanel.setModal(false);
				savePanel.setAutoHideEnabled(true);
				
				savePanel.center(); //This is a bug, if its uncommented it will not show the panel.
				savePanel.show();
				
			/*}
			else
			{
				Window.alert("You are not authorized to do create new entries");
			}
            */
		}
		
	}
	public Map<String, String> getSearchParams() {
		Map<String, String> allParamList = new HashMap<String, String>();
		if(fields == null || fields.length <= 0)
			return allParamList;
		String paramName;
		String paramValue;
		for(int i=0;i<fields.length;i++)
		{
			paramName = fields[i].getFieldName();
			paramValue = FieldTypes.getWidgetValue(fields[i].getType(), searchElements.get(paramName));
			allParamList.put(paramName, paramValue);
		}
		return allParamList;
	}
	protected void clearSearchCriteriaPanel()
	{
		if(fields == null || fields.length <= 0)
			return;
		Widget widget;
		for(int i=0;i<fields.length;i++)
		{
			FieldTypes.clearWidget(fields[i].getType(), searchElements.get(fields[i].getFieldName()));
		}

	}
	public void findServiceFailed()
	{
		resultPanel.clear();
		resultPanel.add(new Label("Error occured at server"));
	}
	public void findServiceSuccess(Object object)
	{
		resultPanel.clear();
		//Object[][] data = this.convertToObjectArray(object);
		/*
		if(resultGrid == null)
			resultGrid = new Grid(data.length,data[0].length);
		else
		{
			if( resultGrid.getColumnCount() < data[0].length || resultGrid.getRowCount() < data.length)  
				resultGrid = new Grid(data.length,data[0].length);
		}
		*/
		resultPanel.add(resultGrid);
	}

}
