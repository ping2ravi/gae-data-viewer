package com.next.viewer.server.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.next.common.client.data.types.NotSupportedType;
import com.next.viewer.client.beans.EntityColBean;
import com.next.viewer.client.beans.EntityColDefinitionBean;
import com.next.viewer.client.beans.EntityDataBean;
import com.next.viewer.client.beans.EntitySearchResultWrapper;
import com.next.viewer.server.exceptions.NoSuchENtity;

public class ReflectionUtil {

	private static Logger logger = Logger.getLogger(ReflectionUtil.class.getName());
	public static EntityColDefinitionBean[] getClassFields(String className) throws NoSuchENtity
	{
		Class cl = null;
		try {
			cl = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity Exists " + className);
		}
		Method[] allMethods = cl.getMethods();
		List<EntityColDefinitionBean> allFields = new ArrayList<EntityColDefinitionBean>();
		EntityColDefinitionBean oneBean;
		String returnType;
		for(int i=0;i<allMethods.length;i++)
		{
			returnType = allMethods[i].getReturnType().toString();
			if(!allMethods[i].getName().startsWith("get") && !allMethods[i].getName().startsWith("is"))
				continue;
			if(allMethods[i].getName().equals("getClass"))
				continue;
			oneBean = new EntityColDefinitionBean();
			if(allMethods[i].getName().startsWith("get"))
				oneBean.setFieldName(allMethods[i].getName().substring(3));
			if(allMethods[i].getName().startsWith("is"))
				oneBean.setFieldName(allMethods[i].getName().substring(2));
			if(returnType.startsWith("class "))
				returnType = returnType.substring(6);
			returnType = SupportedTypes.getReturnType(returnType);
			//TODO need to fix this later
			if(allMethods[i].getName().equals("getId")){
				if(NotSupportedType.class.getName().equals(returnType)){
					continue;
				}
			}
			oneBean.setFieldType(returnType);
			oneBean.setUpdateAllow(SupportedTypes.isSupported(returnType));
			allFields.add(oneBean);
		}
		return (EntityColDefinitionBean[])allFields.toArray(new EntityColDefinitionBean[0]);
	}
	public static Object getEntityKey(EntityDataBean entity) throws ParseException
	{
		String pkField = entity.getPkField();
		EntityColBean[] colBeans = entity.getColumns();
		for(EntityColBean oneColBean:colBeans)
		{
			if(oneColBean.getFieldName().equals(pkField))
			{
				logger.info("Key found ="+oneColBean.getFieldName() + ", Value = " + oneColBean.getFieldValue());
				return SupportedTypes.getValue(entity.getEntityName(), oneColBean.getFieldValue(), oneColBean.getFieldType());
			}
		}
		return null;
	}
	public static Object getEntityKey(String entityName,EntityColBean entity) throws ParseException
	{
		String pkField = entity.getFieldName();
		return SupportedTypes.getValue(entityName, entity.getFieldValue(), entity.getFieldType());
	}
	public static EntitySearchResultWrapper getSearchResultData(List<Object> data,String entityName) throws NoSuchENtity
	{
		EntitySearchResultWrapper returnData = new EntitySearchResultWrapper();
		Class cl = null;
		try {
			cl = Class.forName(entityName);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity Exists " + entityName);
		}
		Method[] allMethods = cl.getMethods();
		//set header in return result
		List<String> header = new ArrayList<String>();
		for(int i=0;i<allMethods.length;i++)
		{
			if(!allMethods[i].getName().startsWith("get") && !allMethods[i].getName().startsWith("is"))
				continue;
			if(allMethods[i].getName().equals("getClass"))
				continue;
			if(allMethods[i].getName().startsWith("get"))
				header.add(allMethods[i].getName().substring(3));
			if(allMethods[i].getName().startsWith("is"))
				header.add(allMethods[i].getName().substring(2));
		}
		returnData.setHeader((String[])header.toArray(new String[0]));
		List<String> allFields = new ArrayList<String>();
		String returnType;
		List<String> oneRow;
		List<String[]> allData = new ArrayList<String[]>();
		for(Object oneObj:data)
		{
			oneRow = new ArrayList<String>();
			for(int i=0;i<allMethods.length;i++)
			{
				
				if(!allMethods[i].getName().startsWith("get") && !allMethods[i].getName().startsWith("is"))
					continue;
				if(allMethods[i].getName().equals("getClass"))
					continue;
				returnType = allMethods[i].getReturnType().toString();
				if(returnType.startsWith("class "))
					returnType = returnType.substring(6);
				System.out.println("Method " + allMethods[i].getName() + " returns "+ returnType);
				if(SupportedTypes.isSupported(returnType))
					try{
					oneRow.add(getPropertyValue(oneObj, allMethods[i].getName(),returnType, true));
					}catch(Exception ex)
					{
						ex.printStackTrace();
						oneRow.add("Could not read value of this column");
					}
				else{
					oneRow.add("");
				}
					
			}
			allData.add((String[])oneRow.toArray(new String[0]));
		}
		for(String[] oneRowData:allData)
		{
			for(int i=0;i<oneRowData.length;i++)
			{
				System.out.print(oneRowData[i]+",");
			}
		}
		returnData.setData((String[][])allData.toArray(new String[0][0]));
		return returnData;
	}
	public static Object getEntityDataObject(EntityColBean[] data,String entityName) throws Exception
	{
		Class cl = null;
		Object returnObject = null;
		try {
			cl = Class.forName(entityName);
			returnObject = cl.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity Exists " + entityName);
		}
		returnObject = updateEntityDataObject(data, returnObject);
		return returnObject;
	}
	public static Object updateEntityDataObject(EntityColBean[] data,Object returnObject) throws Exception
	{
		Class typeClass;
		for(EntityColBean oneCol:data)
		{
			setPropertyValue(returnObject, oneCol.getFieldName(), oneCol.getFieldType(), oneCol.getFieldValue());
		}
		return returnObject;
	}
	public static String getPropertyValue(Object obj,String methodName,String type,boolean method) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
        Class[] cArr = null;
        Object[] objs = null;
        /*Get External ID */
        if(!method)
        	methodName = "get"+methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        cArr = new Class[0];

        Method mSetExID = obj.getClass().getMethod(methodName, cArr);

        objs = new Object[0];
        Object returnObject = mSetExID.invoke(obj,objs); 
        if("java.util.Date".equals(type))
        {
        	SimpleDateFormat sd = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
        	System.out.println("Returning date");
        	System.out.println(sd.format(returnObject));
        	return sd.format(returnObject);
        }
        if(Key.class.getName().equals(type))
        {
        	Key key = (Key)returnObject;
        	return String.valueOf(key.getId());
        }

        return String.valueOf(returnObject);
	}
	public static void setPropertyValue(Object object,String propertyName,String type,Object value) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, ParseException
    {
        Class[] cArr = null;
        Object[] objs = null;
        /*Get External ID */
        if(!SupportedTypes.isSupported(type))
        	return;
        propertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        System.out.println("propertyName is " + propertyName +", value is" + value);

        Class typeClass = SupportedTypes.getClass(type);
        cArr = new Class[1];
        cArr[0] = typeClass;

        Method mSetExID = object.getClass().getMethod("set" + propertyName, cArr);
        objs = new Object[1];
        objs[0] = SupportedTypes.getValue(object.getClass().getSimpleName(),String.valueOf(value), type);
        System.out.println("propertyType is " + type +", value is = " + objs[0]);

        mSetExID.invoke(object,objs);

    }
}
