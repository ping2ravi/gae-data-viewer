package com.next.common.client.panels.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.next.common.client.beans.PageData;
import com.next.common.client.beans.PageInfoBean;

public abstract class CommonPanel extends VerticalPanel implements ClickHandler,ChangeHandler{

	protected CommonSavePanel savePanel;	
	private FieldsBean[] fields;
	private Button search = new Button("Search");
	private Button clear = new Button("Clear");
	private Button newButton = new Button("New");
	private Button deleteButton = new Button("Delete");
	private HTML errorText = new HTML();
	protected Map<String, Widget> searchElements;
	protected String objectName;
	private FlexTable resultGrid;
	private VerticalPanel resultPanel;
	private HorizontalPanel nextPreviousPanel;
	private Button nextButton;
	private Button previousButton;
	private HTML pageInfoArea;
	private HTML pageSizeArea;
	private TextBox pageSize;
	private Map<Long, PageData> pageDataCache;
	protected PageInfoBean pageInfo = null;
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
	public abstract void findData(Map<String,String> searchData,PageInfoBean pageInfo);
	public abstract void deleteData(FlexTable resultTable);
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
		Grid buttonPanel = new Grid(1,6);
		search.addClickHandler(this);
		buttonPanel.setWidget(0, 0, search);
		
		clear.addClickHandler(this);
		
		buttonPanel.setWidget(0, 1, clear);
		
		newButton.addClickHandler(this);
		buttonPanel.setWidget(0, 4, newButton);
		
		deleteButton.setEnabled(false);
		deleteButton.addClickHandler(this);
		buttonPanel.setWidget(0, 5, deleteButton);

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
			widget = new Label(fields[i].getLabelText());
			allControls.setWidget(row, col, widget);
			col++;
			widget = FieldTypes.getWidget(fields[i].getType(),fields[i].isEnabled());
			searchElements.put(fields[i].getFieldName(), widget);
			/*
			if(FieldTypes.LIST_BOX.equals(fields[i].getType()))
			{
				if(fields[i].getListBoxPopulator() != null)
					fields[i].getListBoxPopulator().populateListBox((ListBox)widget,this);
				else
					Window.alert("No List box populator is defined");
			}
			
			//Enable all fields for Search
			if(FieldTypes.LIST_BOX.equals(fields[i].getType()))
			{
				((ListBox)widget).setEnabled(true);
			}
			if(FieldTypes.TEXT_BOX.equals(fields[i].getType()))
			{
				((TextBox)widget).setEnabled(true);
			}
			*/
			allControls.setWidget(row, col, widget);
			col++;
		}
		nextPreviousPanel = new HorizontalPanel();
		nextButton = new Button(">");
		previousButton = new Button("<");
		pageInfoArea = new HTML();
		nextButton.addClickHandler(this);
		nextButton.setEnabled(false);
		previousButton.addClickHandler(this);
		previousButton.setEnabled(false);
		pageSizeArea = new HTML("Page size");
		pageSize = new TextBox();
		pageSize.addChangeHandler(this);
		pageSize.setText("50");
		pageSize.setWidth("70px");

		nextPreviousPanel.add(previousButton);
		nextPreviousPanel.add(pageInfoArea);
		nextPreviousPanel.add(nextButton);
		nextPreviousPanel.add(pageSizeArea);
		nextPreviousPanel.add(pageSize);
		
		resultPanel = new VerticalPanel();
		resultPanel.add(nextPreviousPanel);
		
		pageDataCache = new HashMap<Long, PageData>();
		
		Grid searchPannel = new Grid(4, 1);
		searchPannel.setWidget(0,0, errorText);
		searchPannel.setWidget(1, 0, allControls);
		searchPannel.setWidget(2, 0, allButtonPanel);
		searchPannel.setWidget(3, 0, resultPanel);
		
		pageInfo = new PageInfoBean();
		pageInfo.setPageNum(1);
		pageInfo.setPageSize(50);
		
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
			resultPanel.clear();
			pageDataCache.clear();
			resultPanel.add(new Label("Searching Data"));
			Map<String, String> params = getSearchParams();
			pageInfo.setPageNum(1);
			findData(params,pageInfo);
		}
		if(event.getSource().equals(deleteButton))
		{
			deleteData(resultGrid);
			/*
			if(resultGrid != null)
			{
				int totalRows = resultGrid.getRowCount()-1;
				CheckBox checkBox;
				List<EntityColBean> allDeletedData = new ArrayList<EntityColBean>();
				for(int i=totalRows;i>=1;i--)
				{
					checkBox = (CheckBox)resultGrid.getWidget(i, 0);
					EntityColBean oneEntityKey;
					String key;
					if(checkBox.getValue())
					{
						oneEntityKey = getKeyValuefromRow(i);
						allDeletedData.add(oneEntityKey);
						Window.alert("You want to delte" + oneEntityKey.getFieldName()+" with value " + oneEntityKey.getFieldValue());
					}
				}
				if(allDeletedData.size() <= 0)
					Window.alert("No Records Selected");
				else
				{
					
					ServiceFactory.getDBService().deleteEntityData(this.objectName,(EntityColBean[])allDeletedData.toArray(new EntityColBean[0]), new AsyncCallback<EntityColBean[]>(){
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error "+ caught);
						}
	
						@Override
						public void onSuccess(EntityColBean[] result) {
							int totalRows = resultGrid.getRowCount()-1;
							Window.alert("Rows delted succesfully " + result.length);
							for(int i=totalRows;i>=1;i--)
							{
								for(int j=0;j<result.length;j++)
								{
									GWT.log(result[j].getFieldValue()+":" + getKeyValuefromRow(i).getFieldValue(), null);
									if(result[j].getFieldValue().equals(getKeyValuefromRow(i).getFieldValue()))
									{
										resultGrid.removeRow(i);
										break;
									}
								}
									
							}
						}
						
					});
					
				}
			}*/
		}
		if(sender.equals(nextButton))
		{
			resultPanel.clear();
			resultPanel.add(new Label("Searching Data"));
			pageInfo.setPageNum(pageInfo.getPageNum() + 1);
			PageData pageData = pageDataCache.get(pageInfo.getPageNum());
			if(pageData == null)
			{
				Map<String, String> params = getSearchParams();
				findData(params,pageInfo);
			}
			else
			{
				findServiceSuccess(pageData.getData(), pageData.getHeader(), pageData.getPageInfo());
			}
		}
		if(sender.equals(previousButton))
		{
			resultPanel.clear();
			resultPanel.add(new Label("Searching Data"));
			if(pageInfo.getPageNum() > 1)
				pageInfo.setPageNum(pageInfo.getPageNum() - 1);
			PageData pageData = pageDataCache.get(pageInfo.getPageNum());
			if(pageData == null)
			{
				Map<String, String> params = getSearchParams();
				findData(params,pageInfo);
			}
			else
			{
				findServiceSuccess(pageData.getData(), pageData.getHeader(), pageData.getPageInfo());
			}
		}
		if(sender.equals(newButton))
		{
				savePanel = new CommonSavePanel(this, fields);
				savePanel.createSavePanel();
				savePanel.setModal(true);
				savePanel.center(); //This is a bug, if its uncommented it will not show the panel.
				savePanel.show();
		}
		if(event.getSource().equals(resultGrid))
		{
			savePanel = new CommonSavePanel(this, fields);
			savePanel.createSavePanel();
			Cell cell = resultGrid.getCellForEvent(event);
			Map<String,String> data = new HashMap<String,String>();
			int totalColumn = resultGrid.getCellCount(0);
			for(int i=0;i<totalColumn;i++)
			{
				data.put(resultGrid.getText(0, i).toString(),resultGrid.getText(cell.getRowIndex(), i));
			}
			if(cell.getCellIndex() != 0 && cell.getRowIndex()!= 0)
			{
				savePanel.setModal(true);
				savePanel.center();
				savePanel.show();
				savePanel.populateData(data);
			}
			
		}
		
	}
	/*
	private EntityColBean getKeyValuefromRow(int row)
	{
		EntityDescriptionBean bean = ClientCache.getEntityCache(this.objectName);
		String keyField = bean.getKeyField();
		//get keyFieldType
		String keyFieldType = null;
		for(EntityColDefinitionBean oneBean:bean.getEntityFields())
		{
			if(oneBean.getFieldName().equals(keyField))
				keyFieldType =oneBean.getFieldType();
		}
		int totalColCount = resultGrid.getCellCount(0);
		EntityColBean returnValue = null;
		for(int i=1;i<totalColCount;i++)
		{
			if(keyField.equals(resultGrid.getText(0, i)))
			{
				returnValue = new EntityColBean();
				returnValue.setFieldName(keyField);
				returnValue.setFieldType(keyFieldType);
				returnValue.setFieldValue(resultGrid.getText(row, i));
				break;
			}
		}
		return returnValue;
	}
	*/
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
		deleteButton.setEnabled(false);
	}
	public void findServiceSuccess(String[][] data,String[] header,PageInfoBean resultPageInfo)
	{
		resultPanel.clear();
		PageData pageData = new PageData();
		pageData.setData(data);
		pageData.setHeader(header);
		pageData.setPageInfo(resultPageInfo);
		if(resultPageInfo != null)
		{
			pageDataCache.put(resultPageInfo.getPageNum(), pageData);
			if(resultPageInfo.getPageNum() <= 1)
				previousButton.setEnabled(false);
			else
				previousButton.setEnabled(true);
			pageInfoArea.setHTML("Page "+resultPageInfo.getPageNum() +" of ?");
			if(data == null || data.length < resultPageInfo.getPageSize())
				nextButton.setEnabled(false);
			else
				nextButton.setEnabled(true);
		}
		resultPanel.add(nextPreviousPanel);
		deleteButton.setEnabled(true);
		if(resultGrid == null)
		{
			resultGrid = new FlexTable();
			resultGrid.addClickHandler(this);
		}
		else
		{
			resultGrid.clear();
		}
		resultGrid.setBorderWidth(1);
		resultGrid.setWidget(0, 0, new HTML("<b>Select</b>"));
		for(int i=0;i<header.length;i++)
		{
			resultGrid.setWidget(0, i+1, new HTML("<b>"+header[i]+"</b>"));
		}
		for(int i=0;i<data.length;i++)
		{
			resultGrid.setWidget(i+1, 0,new CheckBox());
			for(int j=0;j<data[i].length;j++)
			{
				resultGrid.setWidget(i+1, j+1, new HTML(data[i][j]));
			}
			
		}
		resultPanel.add(resultGrid);
	}

	public void onChange(ChangeEvent event)
	{
		if(event.getSource().equals(pageSize))
		{
			long size = 0;
			try{
				size = Long.parseLong(pageSize.getText());
			}catch(Exception ex){
				pageSize.setText("50");
				size = 50;
			}
			if(size <= 0 || size > 1000)
			{
				Window.alert("Page Size should be between 1, 1000. Setting it to default 50");
				pageSize.setText("50");
				size = 50;
			}
			pageInfo.setPageSize(size);
		}
	}
}
