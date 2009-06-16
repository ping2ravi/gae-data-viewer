package com.next.common.client.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.next.common.client.beans.RoleBean;
import com.next.common.client.callback.DefaultAsyncCallback;
import com.next.common.client.factory.ServiceFactory;

public class EditUserRolesPanel extends VerticalPanel implements ClickHandler{

	private ListBox userRoles;
	private ListBox availableRoles;
	private Button addRoles;
	private Button removRoles;
	private Button refresh;
	private Button saveButton;
	private TextBox userSoeId;
	private HTML userLink;
	private Map<Long, String> roleUpdateMap;
	private Map<Long, RoleBean> availableRolesResult;
	private Map<Long, RoleBean> userRolesResult;
	private String soeIdText;
	
	public EditUserRolesPanel()
	{
		
	}
	public Widget createPanel()
	{
		userLink = new HTML();
		availableRolesResult = new HashMap<Long, RoleBean>();
		userRolesResult = new HashMap<Long, RoleBean>();
		roleUpdateMap = new HashMap<Long, String>();
		userRoles = new ListBox(true);
		userRoles.setVisibleItemCount(10);
		userRoles.setWidth("250px");
		availableRoles = new ListBox(true);
		availableRoles.setVisibleItemCount(10);
		availableRoles.setWidth("250px");
		addRoles = new Button(">");
		removRoles = new Button("&lt;");
		refresh = new Button("Get Roles");
		saveButton = new Button("Save");
		addRoles.addClickHandler(this);
		removRoles.addClickHandler(this);
		refresh.addClickHandler(this);
		saveButton.addClickHandler(this);
		userSoeId = new TextBox();
		VerticalPanel butonPanel = new VerticalPanel();
		butonPanel.add(addRoles);
		butonPanel.add(removRoles);
		Grid mainTable = new Grid(2,3);
		mainTable.setWidget(0, 0, new Label("Available Roles"));
		mainTable.setWidget(0, 1, butonPanel);
		mainTable.setWidget(0, 2, new Label("User Roles"));
		mainTable.setWidget(1, 0, availableRoles);
		mainTable.setWidget(1, 1, butonPanel);
		mainTable.setWidget(1, 2, userRoles);
		HorizontalPanel userSoeIdPanel = new HorizontalPanel();
		userSoeIdPanel.add(new Label("Enter SOE id"));
		userSoeIdPanel.add(userSoeId);
		userSoeIdPanel.add(refresh);
		this.add(new HTML("<h2>Add/Remove Role to User</h2>"));
		this.add(userSoeIdPanel);
		this.add(userLink);
		this.add(mainTable);
		this.add(saveButton);
		return this;
	}
	private void resetPanel()
	{
		availableRoles.clear();
		userRoles.clear();
		availableRolesResult.clear();
		userRolesResult.clear();
		roleUpdateMap.clear();
	}
	private void refreshData()
	{
		userLink.setHTML("<a target=_new href='http://gdir.nam.nsroot.net/globaldir/Gdir_ResultDetail.asp?SoeID=" + soeIdText+"' >User Details</a>");
		String key = "getRolesAvailableForUser"+soeIdText;
		/*
		ServiceFactory.getJaatMusicAdminService().getRolesAvailableForUser(soeIdText, new DefaultAsyncCallback<RoleBean[]>(key){
			public void onServiceFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onServiceSuccess(RoleBean[] result) {
				availableRoles.clear();
				if(result == null || result.length <= 0)
					return;
				for(RoleBean oneRole:result)
				{
					availableRolesResult.put(oneRole.getId(), oneRole);
				}
				addMapDataToListBox(availableRoles, availableRolesResult);
				
			}

		});
				
		key = "getUserRoles"+soeIdText;
		ServiceFactory.getJaatMusicAdminService().getUserRoles(soeIdText, new DefaultAsyncCallback<RoleBean[]>(key){
			public void onServiceFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onServiceSuccess(RoleBean[] result) {
				userRoles.clear();
				if(result == null || result.length <= 0)
					return;
				for(RoleBean oneRole:result)
				{
					userRolesResult.put(oneRole.getId(), oneRole);
				}
				addMapDataToListBox(userRoles, userRolesResult);
			}

		});
		*/		
	}
	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if(sender.equals(refresh))
		{
			resetPanel();
			if(userSoeId.getText() == null || userSoeId.getText().trim().equals(""))
			{
				Window.alert("Enter SOE id");
				return;
			}
			soeIdText = userSoeId.getText();
			refreshData();
		}
		if(sender.equals(addRoles))
		{
			int total = availableRoles.getItemCount();
			List<String> selectedValues = new ArrayList<String>();
			RoleBean oneRoleBean;
			for(int i=0;i<total ;i++)
			{
				if(availableRoles.isItemSelected(i))
					selectedValues.add(availableRoles.getValue(i));
			}
			if(selectedValues.size() <= 0)
				Window.alert("Nothing is selected");
			else
			{
				RoleBean role;
				String updatedRole;
				for(String oneS:selectedValues)
				{
					role = availableRolesResult.remove(Long.parseLong(oneS));
					userRolesResult.put(role.getId(), role);
					updatedRole = roleUpdateMap.get(Long.parseLong(oneS));
					if(updatedRole == null)
						roleUpdateMap.put(Long.parseLong(oneS), "AddRole");
					else
						roleUpdateMap.remove(Long.parseLong(oneS));
				}
				addMapDataToListBox(availableRoles, availableRolesResult);
				addMapDataToListBox(userRoles, userRolesResult);
			}
		}
		if(sender.equals(removRoles))
		{
			int total = userRoles.getItemCount();
			List<String> selectedValues = new ArrayList<String>();
			RoleBean oneRoleBean;
			for(int i=0;i<total ;i++)
			{
				if(userRoles.isItemSelected(i))
					selectedValues.add(userRoles.getValue(i));
			}
			if(selectedValues.size() <= 0)
				Window.alert("Nothing is selected");
			else
			{
				RoleBean role;
				String updatedRole;
				for(String oneS:selectedValues)
				{
					role = userRolesResult.remove(Long.parseLong(oneS));
					availableRolesResult.put(role.getId(), role);
					updatedRole = roleUpdateMap.get(Long.parseLong(oneS));
					if(updatedRole == null)
						roleUpdateMap.put(Long.parseLong(oneS), "RemoveRole");
					else
						roleUpdateMap.remove(Long.parseLong(oneS));
				}
				addMapDataToListBox(availableRoles, availableRolesResult);
				addMapDataToListBox(userRoles, userRolesResult);
			}
		}
		if(sender.equals(saveButton))
		{
			Collection<Long> allKeys = roleUpdateMap.keySet();
			List<Long> rolesToAdd = new ArrayList<Long>();
			List<Long> rolesToRemove = new ArrayList<Long>();
			for(Long oneKey:allKeys)
			{
				if("RemoveRole".equals(roleUpdateMap.get(oneKey)))
					rolesToRemove.add(oneKey);
				if("AddRole".equals(roleUpdateMap.get(oneKey)))
					rolesToAdd.add(oneKey);
				Window.alert(oneKey + " is " + roleUpdateMap.get(oneKey) + " Reuest");
			}
			String key = "updateUserRoles"+soeIdText;
			/*
			ServiceFactory.getJaatMusicAdminService().updateUserRoles(soeIdText, (Long[])rolesToAdd.toArray(new Long[0]), (Long[])rolesToRemove.toArray(new Long[0]), new DefaultAsyncCallback<String>(key){

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
			*/
		}
		
	}
	
	private void addMapDataToListBox(ListBox listBox,Map<Long, RoleBean> data)
	{
		if(listBox == null)
		{
			return;
		}
		listBox.clear();
		Collection<RoleBean> allRoles =  data.values();
		for(RoleBean oneRole:allRoles)
		{
			listBox.addItem(oneRole.getName(), String.valueOf(oneRole.getId()));
		}
		
	}
	
}
