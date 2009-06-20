/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.next.common.client.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.next.common.client.beans.EntityColBean;
import com.next.common.client.beans.EntityColDefinitionBean;
import com.next.common.client.beans.EntityDataBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntitySearchCriteria;
import com.next.common.client.beans.EntitySearchResultWrapper;
import com.next.common.client.callback.FindEntityDataCallback;
import com.next.common.client.factory.ServiceFactory;
import com.next.common.client.panels.generic.CommonPanel;
import com.next.common.client.panels.generic.FieldsBean;
import com.next.common.client.panels.generic.UiErrorPanel;
import com.next.common.client.session.ClientCache;

/**
 *
 * @author Ravi
 */
public class GenericPanel extends CommonPanel{

	public GenericPanel(String entityName,FieldsBean[] beans)
	{
	  super(entityName);
      super.setFields(beans);
	}
	@Override
    public Object[][] convertToObjectArray(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getObjectFromParams(Map<String, String> params) {
    	EntityDescriptionBean entityDescription = ClientCache.getEntityCache(super.objectName);
    	EntityDataBean edb = new EntityDataBean();
    	edb.setEntityName(super.objectName);
    	edb.setPkField(entityDescription.getKeyField());
    	
    	List<EntityColBean> allColumnData = new ArrayList<EntityColBean>();
    	EntityColDefinitionBean[] colDefinitons = entityDescription.getEntityFields();
    	EntityColBean oneCol;
    	for(String key:params.keySet())
    	{
    		oneCol = new EntityColBean();
    		oneCol.setFieldName(key);
    		oneCol.setFieldValue(params.get(key));
    		for(int i=0;i<colDefinitons.length;i++)
    		{
    			if(key.equals(colDefinitons[i].getFieldName()))
    			{
    				oneCol.setFieldType(colDefinitons[i].getFieldType());
    				break;
    			}
    			
    		}
    		allColumnData.add(oneCol);
    	}
    	edb.setColumns((EntityColBean[])allColumnData.toArray(new EntityColBean[0]));
    	return edb;
    }

    @Override
    public boolean validateObject(Object obj, UiErrorPanel parentPanel) {
        return true;
    }

    @Override
    public void onSave(Object object) {
    	EntityDescriptionBean entityDescription = ClientCache.getEntityCache(super.objectName);
    	String keyField = entityDescription.getKeyField();
    	EntityDataBean data = (EntityDataBean)object;
    	EntityColBean colBeans[] = data.getColumns();
    	boolean isCreate = false;
    	for(EntityColBean oneEntityColBean:colBeans)
    	{
    		GWT.log(oneEntityColBean.getFieldName()+"="+keyField, null);
    		if(oneEntityColBean.getFieldName().equals(keyField))
    		{
        		GWT.log("match Found="+oneEntityColBean.getFieldValue()+"]", null);
    			if(oneEntityColBean.getFieldValue().equals(""))
    				isCreate = true;
    		}
    	}
    	if(isCreate)
    	{
    		Window.alert("I sholuld Crete now");
    		ServiceFactory.getDBService().createEntityData(data, new AsyncCallback<EntityDataBean>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error " + caught);
				}

				@Override
				public void onSuccess(EntityDataBean result) {
					Window.alert("created succesfully ");
					EntityColBean[] colBeans = result.getColumns();
					Map<String,String> data = new HashMap<String, String>();
					for(int i=0;i<colBeans.length;i++)
					{
						data.put(colBeans[i].getFieldName(), colBeans[i].getFieldValue());
					}
					savePanel.populateData(data);
				}
    			
    		});
    	}
    	else
    	{
    		Window.alert("I sholuld update now");
    		ServiceFactory.getDBService().updateEntityData(data, new AsyncCallback<EntityDataBean>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error " + caught);
				}

				@Override
				public void onSuccess(EntityDataBean result) {
					Window.alert("created succesfully ");
					EntityColBean[] colBeans = result.getColumns();
					Map<String,String> data = new HashMap<String, String>();
					for(int i=0;i<colBeans.length;i++)
					{
						data.put(colBeans[i].getFieldName(), colBeans[i].getFieldValue());
					}
					savePanel.populateData(data);
				}
    			
    		});
    		
    	}
    }

    @Override
    public void findData(Map searchData) {
        //get the search criteria
    	EntitySearchCriteria searchBean = new EntitySearchCriteria();
    	searchBean.setEntityName(super.objectName);
    	FindEntityDataCallback callback = new FindEntityDataCallback(this,"key");
    	ServiceFactory.getDBService().findEntityData(searchBean,callback);
    	
    	
    }

}
