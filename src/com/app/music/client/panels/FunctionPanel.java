package com.app.music.client.panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.app.music.client.beans.FunctionBean;
import com.app.music.client.factory.ServiceFactory;
import com.app.music.client.panels.generic.CommonPanel;
import com.app.music.client.panels.generic.FieldTypes;
import com.app.music.client.panels.generic.FieldsBean;
import com.app.music.client.panels.generic.GenericFindDataCallback;
import com.app.music.client.panels.generic.UiErrorPanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FunctionPanel extends CommonPanel{

	private static final String NAME_VALUE = "NAME_VALUE";
	private static final String DESCRIPTION_VALUE = "DESCRIPTION_VALUE";
	private static final String ID_VALUE = "ID_VALUE";
	public FunctionPanel()
	{
	  super("Function");
	  FieldsBean[] fields = new FieldsBean[3];
	  fields[0] = new FieldsBean(FieldTypes.TEXT_BOX,"Broker Swapswire Value",NAME_VALUE,true,null);
	  fields[1] = new FieldsBean(FieldTypes.TEXT_BOX,"Swaspwire Screen value",DESCRIPTION_VALUE,true,null);
	  fields[2] = new FieldsBean(FieldTypes.TEXT_BOX,"Id",ID_VALUE,false,null);
	  super.setFields(fields);
	  
	}
	@Override
	public Object[][] convertToObjectArray(Object object) {
		if(object == null)
			return new Object[0][0];
		FunctionBean[] functions = (FunctionBean[])object;
		List<String[]> allObject = new ArrayList<String[]>();
		List<String> oneObjectList;
		FunctionBean oneBean;
		for(int i=0;i<functions.length;i++)
		{
			oneBean = (FunctionBean)functions[i];
			oneObjectList = new ArrayList<String>();
			oneObjectList.add(oneBean.getName());
			oneObjectList.add(oneBean.getDescription());
			oneObjectList.add(String.valueOf(oneBean.getId()));
			allObject.add((String[])oneObjectList.toArray(new String[0]));
		}
		String[][] returnValue = (String[][])allObject.toArray(new String[0][0]);
		return (Object[][])returnValue;	
	}

	@Override
	public void findData(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getObjectFromParams(Map<String, String> data) {
		Iterator<String> keyItr = data.keySet().iterator();
		String fieldName;
		FunctionBean bean = new FunctionBean();
		while(keyItr.hasNext())
		{
			fieldName = keyItr.next();
			if(NAME_VALUE.equals(fieldName))
			{
				bean.setName(data.get(fieldName));
			}
			if(DESCRIPTION_VALUE.equals(fieldName))
			{
				bean.setDescription(data.get(fieldName));
			}
			if(ID_VALUE.equals(fieldName))
			{
				try{
				bean.setId(Long.parseLong(data.get(fieldName)));
				}catch(Exception ex){}
			}
		}	
		return bean;
	}

	@Override
	public void onSave(Object object) {
		FunctionBean bean = (FunctionBean)object;
		if(bean.getId() <= 0)
		{
			Window.alert("Create object will be called " + bean.getId());
			String key = "CreateBroker"+bean.getName()+bean.getDescription();
			AsyncCallback callback =  new GenericFindDataCallback(this,key);
			ServiceFactory.getJaatMusicAdminService().createFunction(bean,callback);
		}
		else
		{
			Window.alert("Update object will be called" + bean.getId());
			String key = "UpdateBroker"+bean.getName()+bean.getDescription();
			AsyncCallback callback =  new GenericFindDataCallback(this,key);
			ServiceFactory.getJaatMusicAdminService().updateFunction(bean, callback);
		}
	}

	@Override
	public boolean validateObject(Object obj, UiErrorPanel parentPanel) {
		FunctionBean bean = (FunctionBean)obj;
		if(bean.getName() == null || bean.getName().trim().equals(""))
		{
			parentPanel.setErrorMessage("Function name can not be null");
			return false;
		}
		if(bean.getDescription() == null || bean.getDescription().trim().equals(""))
		{
			parentPanel.setErrorMessage("Function Description can not be null");
			return false;
		}
		return true;
	}

	
}
