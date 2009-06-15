package com.app.music.client.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.music.client.beans.FunctionBean;
import com.app.music.client.callback.DefaultAsyncCallback;
import com.app.music.client.factory.ServiceFactory;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditRoleFunctionPanel extends VerticalPanel implements ClickHandler{

	private ListBox roleFunctions;
	private ListBox availableFunctions;
	private Button addFunctions;
	private Button removFunctions;
	private Button refresh;
	private Button saveButton;
	private TextBox roleId;
	private Map<Long, String> functionUpdateMap;
	private Map<Long, FunctionBean> availableFUnctionsResult;
	private Map<Long, FunctionBean> roleFunctionsResult;
	private String soeIdText;
	public EditRoleFunctionPanel()
	{
		
	}
	public Widget createPanel()
	{
		availableFUnctionsResult = new HashMap<Long, FunctionBean>();
		roleFunctionsResult = new HashMap<Long, FunctionBean>();
		functionUpdateMap = new HashMap<Long, String>();
		roleFunctions = new ListBox(true);
		roleFunctions.setVisibleItemCount(10);
		roleFunctions.setWidth("250px");
		availableFunctions = new ListBox(true);
		availableFunctions.setVisibleItemCount(10);
		availableFunctions.setWidth("250px");
		addFunctions = new Button(">");
		removFunctions = new Button("&lt;");
		refresh = new Button("Get Functions");
		saveButton = new Button("Save");
		addFunctions.addClickHandler(this);
		removFunctions.addClickHandler(this);
		refresh.addClickHandler(this);
		saveButton.addClickHandler(this);
		roleId = new TextBox();
		VerticalPanel butonPanel = new VerticalPanel();
		butonPanel.add(addFunctions);
		butonPanel.add(removFunctions);
		Grid mainTable = new Grid(2,3);
		mainTable.setWidget(0, 0, new Label("Available Functions"));
		mainTable.setWidget(0, 1, butonPanel);
		mainTable.setWidget(0, 2, new Label("User Functions"));
		mainTable.setWidget(1, 0, availableFunctions);
		mainTable.setWidget(1, 1, butonPanel);
		mainTable.setWidget(1, 2, roleFunctions);
		HorizontalPanel userSoeIdPanel = new HorizontalPanel();
		userSoeIdPanel.add(new Label("Enter Role Id"));
		userSoeIdPanel.add(roleId);
		userSoeIdPanel.add(refresh);
		this.add(new HTML("<h2>Add/Remove Function to Role</h2>"));
		this.add(userSoeIdPanel);
		this.add(mainTable);
		this.add(saveButton);
		return this;
	}
	private void resetPanel()
	{
		availableFunctions.clear();
		roleFunctions.clear();
		availableFUnctionsResult.clear();
		roleFunctionsResult.clear();
		functionUpdateMap.clear();
	}
	private void refreshData()
	{
		String key = "getRolesAvailableForUser"+soeIdText;
		ServiceFactory.getJaatMusicAdminService().getFunctionAvailableForRole(soeIdText, new DefaultAsyncCallback<FunctionBean[]>(key){
			public void onServiceFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onServiceSuccess(FunctionBean[] result) {
				availableFunctions.clear();
				if(result == null || result.length <= 0)
					return;
				for(FunctionBean oneFunction:result)
				{
					availableFUnctionsResult.put(oneFunction.getId(), oneFunction);
				}
				addMapDataToListBox(availableFunctions, availableFUnctionsResult);
				
			}

		});		
		key = "getUserRoles"+soeIdText;
		ServiceFactory.getJaatMusicAdminService().getRoleFunctions(soeIdText, new DefaultAsyncCallback<FunctionBean[]>(key){
			public void onServiceFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onServiceSuccess(FunctionBean[] result) {
				roleFunctions.clear();
				if(result == null || result.length <= 0)
					return;
				for(FunctionBean oneRole:result)
				{
					roleFunctionsResult.put(oneRole.getId(), oneRole);
				}
				addMapDataToListBox(roleFunctions, roleFunctionsResult);
			}

		});		
	}
	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if(sender.equals(refresh))
		{
			resetPanel();
			if(roleId.getText() == null || roleId.getText().trim().equals(""))
			{
				Window.alert("Enter SOE id");
				return;
			}
			soeIdText = roleId.getText();
			refreshData();
		}
		if(sender.equals(addFunctions))
		{
			int total = availableFunctions.getItemCount();
			List<String> selectedValues = new ArrayList<String>();
			FunctionBean oneFunctionBean;
			for(int i=0;i<total ;i++)
			{
				if(availableFunctions.isItemSelected(i))
					selectedValues.add(availableFunctions.getValue(i));
			}
			if(selectedValues.size() <= 0)
				Window.alert("Nothing is selected");
			else
			{
				FunctionBean function;
				String updatedRole;
				for(String oneS:selectedValues)
				{
					function = availableFUnctionsResult.remove(Long.parseLong(oneS));
					roleFunctionsResult.put(function.getId(), function);
					updatedRole = functionUpdateMap.get(Long.parseLong(oneS));
					if(updatedRole == null)
						functionUpdateMap.put(Long.parseLong(oneS), "AddRole");
					else
						functionUpdateMap.remove(Long.parseLong(oneS));
				}
				addMapDataToListBox(availableFunctions, availableFUnctionsResult);
				addMapDataToListBox(roleFunctions, roleFunctionsResult);
			}
		}
		if(sender.equals(removFunctions))
		{
			int total = roleFunctions.getItemCount();
			List<String> selectedValues = new ArrayList<String>();
			FunctionBean oneFunctionBean;
			for(int i=0;i<total ;i++)
			{
				if(roleFunctions.isItemSelected(i))
					selectedValues.add(roleFunctions.getValue(i));
			}
			if(selectedValues.size() <= 0)
				Window.alert("Nothing is selected");
			else
			{
				FunctionBean role;
				String updatedRole;
				for(String oneS:selectedValues)
				{
					role = roleFunctionsResult.remove(Long.parseLong(oneS));
					availableFUnctionsResult.put(role.getId(), role);
					updatedRole = functionUpdateMap.get(Long.parseLong(oneS));
					if(updatedRole == null)
						functionUpdateMap.put(Long.parseLong(oneS), "RemoveRole");
					else
						functionUpdateMap.remove(Long.parseLong(oneS));
				}
				addMapDataToListBox(availableFunctions, availableFUnctionsResult);
				addMapDataToListBox(roleFunctions, roleFunctionsResult);
			}
		}
		if(sender.equals(saveButton))
		{
			Collection<Long> allKeys = functionUpdateMap.keySet();
			List<Long> rolesToAdd = new ArrayList<Long>();
			List<Long> rolesToRemove = new ArrayList<Long>();
			for(Long oneKey:allKeys)
			{
				if("RemoveRole".equals(functionUpdateMap.get(oneKey)))
					rolesToRemove.add(oneKey);
				if("AddRole".equals(functionUpdateMap.get(oneKey)))
					rolesToAdd.add(oneKey);
				Window.alert(oneKey + " is " + functionUpdateMap.get(oneKey) + " Reuest");
			}
			String key = "updateUserRoles"+soeIdText;
			ServiceFactory.getJaatMusicAdminService().updateRoleFunctions(soeIdText, (Long[])rolesToAdd.toArray(new Long[0]), (Long[])rolesToRemove.toArray(new Long[0]), new DefaultAsyncCallback<String>(key){

				@Override
				public void onServiceFailure(Throwable caught) {
					Window.alert("Error : " + caught.getMessage());
				}

				@Override
				public void onServiceSuccess(String result) {
					Window.alert(result);
					resetPanel();
					refreshData();
				}
				
			});
		}
		
	}
	
	private void addMapDataToListBox(ListBox listBox,Map<Long, FunctionBean> data)
	{
		if(listBox == null)
		{
			return;
		}
		listBox.clear();
		Collection<FunctionBean> allFunctions =  data.values();
		for(FunctionBean oneFunction:allFunctions)
		{
			listBox.addItem(oneFunction.getName(), String.valueOf(oneFunction.getId()));
		}
		
	}
	
}
