/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.next.viewer.client.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.next.common.client.beans.PageInfoBean;
import com.next.common.client.panels.generic.CommonPanel;
import com.next.common.client.panels.generic.FieldsBean;
import com.next.common.client.panels.generic.UiErrorPanel;
import com.next.viewer.client.beans.EntityColBean;
import com.next.viewer.client.beans.EntityColDefinitionBean;
import com.next.viewer.client.beans.EntityDataBean;
import com.next.viewer.client.beans.EntityDescriptionBean;
import com.next.viewer.client.beans.EntitySearchCriteria;
import com.next.viewer.client.callbacks.FindEntityDataCallback;
import com.next.viewer.client.factory.ServiceFactory;
import com.next.viewer.client.session.ClientCache;

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
    		ServiceFactory.getDBService().createEntityData(data, new AsyncCallback<EntityDataBean>(){

				@Override
				public void onFailure(Throwable caught) {
					savePanel.setErrorMessage("Error occured while saving<br>"+caught.getMessage());
				}

				@Override
				public void onSuccess(EntityDataBean result) {
					EntityColBean[] colBeans = result.getColumns();
					Map<String,String> data = new HashMap<String, String>();
					for(int i=0;i<colBeans.length;i++)
					{
						data.put(colBeans[i].getFieldName(), colBeans[i].getFieldValue());
					}
					savePanel.populateData(data);
					savePanel.setErrorMessage("Data Saved Succesfully");
				}
    			
    		});
    	}
    	else
    	{
    		ServiceFactory.getDBService().updateEntityData(data, new AsyncCallback<EntityDataBean>(){

				@Override
				public void onFailure(Throwable caught) {
					savePanel.setErrorMessage("Error occured while saving<br>"+caught.getMessage());
				}

				@Override
				public void onSuccess(EntityDataBean result) {
					EntityColBean[] colBeans = result.getColumns();
					Map<String,String> data = new HashMap<String, String>();
					for(int i=0;i<colBeans.length;i++)
					{
						data.put(colBeans[i].getFieldName(), colBeans[i].getFieldValue());
					}
					savePanel.populateData(data);
					savePanel.setErrorMessage("Data Saved Succesfully");
				}
    			
    		});
    		
    	}
    }

    @Override
    public void findData(Map<String,String> searchData,PageInfoBean pageInfo) {
        //get the search criteria
    	EntitySearchCriteria searchBean = new EntitySearchCriteria();
    	searchBean.setPageInfo(pageInfo);
    	EntityDescriptionBean cachedBean = ClientCache.getEntityCache(super.objectName);
    	EntityColDefinitionBean colDefs[] = cachedBean.getEntityFields();
    	searchBean.setEntityName(super.objectName);
    	List<EntityColBean> allParams = new ArrayList<EntityColBean>();
    	if(searchData != null)
    	{
        	EntityColBean oneColBean;
    		String value;
    		for(String key:searchData.keySet())
    		{
    			value = searchData.get(key);
    			if(value == null || value.trim().equals(""))
    				continue;
    			oneColBean = new EntityColBean();
    			oneColBean.setFieldName(key);
    			oneColBean.setFieldValue(value);
    			for(int i=0;i<colDefs.length;i++)
    			{
    				if(key.equals(colDefs[i].getFieldName()))
    					oneColBean.setFieldType(colDefs[i].getFieldType());
    			}
    			allParams.add(oneColBean);
    		}
    	}
    	searchBean.setAllColumns((EntityColBean[])allParams.toArray(new EntityColBean[0]));
    	FindEntityDataCallback callback = new FindEntityDataCallback(this,"key");
    	ServiceFactory.getDBService().findEntityData(searchBean,callback);
    	
    	
    }
	@Override
	public void deleteData(final FlexTable resultGrid) {
		
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
					oneEntityKey = getKeyValuefromRow(i,resultGrid);
					allDeletedData.add(oneEntityKey);
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
						Window.alert(result.length + " Records deleted succesfully ");
						for(int i=totalRows;i>=1;i--)
						{
							for(int j=0;j<result.length;j++)
							{
								GWT.log(result[j].getFieldValue()+":" + getKeyValuefromRow(i,resultGrid).getFieldValue(), null);
								if(result[j].getFieldValue().equals(getKeyValuefromRow(i,resultGrid).getFieldValue()))
								{
									resultGrid.removeRow(i);
									break;
								}
							}
								
						}
					}
					
				});
				
			}
		}
		
	}
	private EntityColBean getKeyValuefromRow(int row,FlexTable resultGrid)
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

}
