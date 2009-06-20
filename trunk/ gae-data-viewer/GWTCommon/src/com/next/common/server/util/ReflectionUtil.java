package com.next.common.server.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import com.next.common.client.beans.EntityColDefinitionBean;
import com.next.common.client.beans.EntitySearchResultWrapper;
import com.next.common.server.entity.Customer;
import com.next.common.server.exceptions.NoSuchENtity;

public class ReflectionUtil {

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
			if(!allMethods[i].getName().startsWith("get"))
				continue;
			if(allMethods[i].getName().equals("getClass"))
				continue;
			oneBean = new EntityColDefinitionBean();
			oneBean.setFieldName(allMethods[i].getName().substring(3));
			returnType = allMethods[i].getReturnType().toString();
			if(returnType.startsWith("class "))
				returnType = returnType.substring(6);
			oneBean.setFieldType(returnType);
			allFields.add(oneBean);
		}
		return (EntityColDefinitionBean[])allFields.toArray(new EntityColDefinitionBean[0]);
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
			if(!allMethods[i].getName().startsWith("get"))
				continue;
			if(allMethods[i].getName().equals("getClass"))
				continue;
			header.add(allMethods[i].getName().substring(3));
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
				
				if(!allMethods[i].getName().startsWith("get"))
					continue;
				if(allMethods[i].getName().equals("getClass"))
					continue;
				returnType = allMethods[i].getReturnType().toString();
				if(returnType.startsWith("class "))
					returnType = returnType.substring(6);
				System.out.println("Method " + allMethods[i].getName() + " returns "+ returnType);
				if(SupportedTypes.isSupported(returnType))
					try{
					oneRow.add(getPropertyValue(oneObj, allMethods[i].getName(), true));
					}catch(Exception ex)
					{
						oneRow.add("Could not read value of this column");
					}
				else
					oneRow.add("");
					
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
	public static String getPropertyValue(Object obj,String methodName,boolean method) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
        Class[] cArr = null;
        Object[] objs = null;
        /*Get External ID */
        if(!method)
        	methodName = "get"+methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        cArr = new Class[0];

        Method mSetExID = obj.getClass().getMethod(methodName, cArr);

        objs = new Object[0];

        return String.valueOf(mSetExID.invoke(obj,objs));
	}
	public static void setPropertyValue(Object object,String propertyName,Object value) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
        Class[] cArr = null;
        Object[] objs = null;
        /*Get External ID */

         propertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        cArr = new Class[1];
        cArr[0] = value.getClass();

        Method mSetExID = object.getClass().getMethod("set" + propertyName, cArr);

        objs = new Object[1];
        objs[0] = value;

        mSetExID.invoke(object,objs);

    }
	public static void main(String[] args) throws NoSuchENtity
	{
		getSearchResultData(null, Customer.class.getName());
	}
}
