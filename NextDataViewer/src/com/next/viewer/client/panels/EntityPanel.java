package com.next.viewer.client.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.next.common.client.panels.generic.CommonPanel;
import com.next.common.client.panels.generic.FieldTypes;
import com.next.common.client.panels.generic.FieldsBean;
import com.next.common.client.panels.generic.UiErrorPanel;
import com.next.viewer.client.beans.EntityColDefinitionBean;
import com.next.viewer.client.beans.EntityDefnitionBean;
import com.next.viewer.client.beans.EntityDescriptionBean;
import com.next.viewer.client.factory.ServiceFactory;
import com.next.viewer.client.session.ClientCache;

public class EntityPanel extends DecoratedPopupPanel implements ChangeHandler,ClickHandler{

	private TextBox idTextBox;
	private TextBox entityName;
	private ListBox fieldNames;
	private Button save;
	private Button cancel;
	
	public EntityPanel()
	{
		this("", "", "");
	}
	public EntityPanel(String id,String name,String keyField)
	{
		FlexTable ft = new FlexTable();
		idTextBox = new TextBox();
		idTextBox.setEnabled(false);
		idTextBox.setText(id);
		entityName = new TextBox();
		entityName.addChangeHandler(this);
		entityName.setText(name);
		entityName.setWidth("300px");
		fieldNames = new ListBox();
		populateFieldListBox(keyField);
		save = new Button("Save");
		save.addClickHandler(this);
		cancel = new Button("Cancel");
		cancel.addClickHandler(this);
		ft.setWidget(0, 0, new Label("Entity Class Name"));
		ft.setWidget(0, 1, entityName);
		ft.setWidget(1, 0, new Label("Primary Key Field"));
		ft.setWidget(1, 1, fieldNames);
		ft.setWidget(2, 0, new Label("Id"));
		ft.setWidget(2, 1, idTextBox);
		ft.setWidget(3, 0, cancel);
		ft.setWidget(3, 1, save);
		this.add(ft);
	}

	@Override
	public void onChange(ChangeEvent event) {
		if(event.getSource().equals(entityName))
		{
			populateFieldListBox("");
		}
		
	}
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(save))
		{
			if(idTextBox.getText().trim().equals(""))
			{
				EntityDefnitionBean entity = new EntityDefnitionBean();
				entity.setKeyField(fieldNames.getValue(fieldNames.getSelectedIndex()));
				entity.setName(entityName.getText());
				ServiceFactory.getDBService().createEntity(entity, new AsyncCallback<EntityDefnitionBean>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(EntityDefnitionBean result) {
						if(result.getId() != null)
							idTextBox.setText(String.valueOf(result.getId()));
					}
					
				});
			}
			else
			{
				EntityDefnitionBean entity = new EntityDefnitionBean();
				entity.setKeyField(fieldNames.getValue(fieldNames.getSelectedIndex()));
				entity.setName(entityName.getText());
				entity.setId(Long.parseLong(idTextBox.getText()));
				ServiceFactory.getDBService().updateEntity(entity, new AsyncCallback<EntityDefnitionBean>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(EntityDefnitionBean result) {
						if(result.getId() != null)
							idTextBox.setText(String.valueOf(result.getId()));
					}
					
				});
			}
		}
		if(event.getSource().equals(cancel))
		{
			this.hide();
		}
		
	}
	private void populateFieldListBox(String keyField)
	{
		fieldNames.clear();
		if(entityName.getText() != null && !entityName.getText().trim().equals(""))
		{
			EntityDescriptionBean entity = ClientCache.getEntityCache(entityName.getText());
			List<String> allFieldsDef = new ArrayList<String>();
			String[] allFields = null;
			EntityColDefinitionBean[] allFieldBeans = null;
			if(entity != null)
			{
				allFieldBeans = entity.getEntityFields();
				keyField = entity.getKeyField();
				for(EntityColDefinitionBean oneBean:allFieldBeans)
				{
					allFieldsDef.add(oneBean.getFieldName());
				}
				allFields = (String[])allFieldsDef.toArray(new String[0]);
			}
			
			if(allFields == null || allFields.length <= 0)
			{
				ServiceFactory.getDBService().getEntityDescription(entityName.getText(), new AsyncCallback<EntityDescriptionBean>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						entityName.setFocus(true);
					}

					@Override
					public void onSuccess(EntityDescriptionBean result) {
						ClientCache.setEntityCache(result);
						EntityColDefinitionBean[] allFields = result.getEntityFields();
						fieldNames.clear();
						for(EntityColDefinitionBean oneField:allFields)
						{
							fieldNames.addItem(oneField.getFieldName()+" - "+oneField.getFieldType(),oneField.getFieldName());
						}
					}
					
				});
			}
			else
			{
				int i = 0;
				fieldNames.clear();
				for(EntityColDefinitionBean oneField:allFieldBeans)
				{
					fieldNames.addItem(oneField.getFieldName()+" - "+oneField.getFieldType(),oneField.getFieldName());
				}
				for(String oneField:allFields)
				{
					if(oneField.equals(keyField))
					{
						fieldNames.setSelectedIndex(i);
					}
					i++;
				}
			}
		}
	}
}
