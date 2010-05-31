package com.next.viewer.server.entity.helper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.next.common.client.beans.PageInfoBean;
import com.next.viewer.client.beans.EntityColBean;
import com.next.viewer.client.beans.EntitySearchCriteria;
import com.next.viewer.server.exceptions.NoSuchENtity;
import com.next.viewer.server.util.SupportedTypes;



public class GenericEntityHelper {
	public static Logger logger = Logger.getLogger(GenericEntityHelper.class.getName());
	private GenericEntityHelper()
	{
		
	}
	private static GenericEntityHelper instance;
	public static GenericEntityHelper getInstance()
	{
		if(instance ==null)
			instance = new GenericEntityHelper();
		return instance;
	}
	public Object createGenericEntity(DBManager dbManager,Object entity)
	{
		return dbManager.createObject(entity);
	}
	public Object getGenericEntityById(DBManager dbManager,Class entityClass,Object id)
	{
		return dbManager.getObjectById(entityClass, id);
	}
	public Object getGenericEntityById(DBManager dbManager,String entityClass,Object id) throws NoSuchENtity
	{
		Class cl;
		try {
			cl = Class.forName(entityClass);
		} catch (ClassNotFoundException e) {
			throw new NoSuchENtity("No Such entity exists " + entityClass);
		}
		return getGenericEntityById(dbManager, cl, id);
	}
	public Object updateGenericEntity(DBManager dbManager,Object entity)
	{
		return dbManager.updateObject(entity);
	}
	public void deleteGenericEntity(DBManager dbManager,Class entityClass,Object id)
	{
		dbManager.deleteObjectById(entityClass,id);
	}
	public void deleteGenericEntity(DBManager dbManager,String entityClass,Object id) throws NoSuchENtity
	{
		Class cl;
		try {
			cl = Class.forName(entityClass);
		} catch (ClassNotFoundException e) {
			throw new NoSuchENtity("No Such entity exists " + entityClass);
		}
		deleteGenericEntity(dbManager, cl, id);
	}
	public List findGenericEntity(DBManager dbManager,Class entityClass,Map entity)
	{
		return dbManager.runQuery(entityClass, entity);
	}
	public List findGenericEntity(DBManager dbManager,EntitySearchCriteria searchBean) throws NoSuchENtity,ParseException
	{
		String entityName = searchBean.getEntityName();
		Map<String, Object> entity = new HashMap<String, Object>();
		Class entityClass;
		try {
			EntityColBean[] allParams = searchBean.getAllColumns();
			logger.log(Level.INFO,"Params passed are");
			if(allParams != null)
			{
				System.out.println("Total Params passed are " + allParams.length);
				for(int i=0;i<allParams.length;i++)
				{
					entity.put(allParams[i].getFieldName(), SupportedTypes.getValue(searchBean.getEntityName(), allParams[i].getFieldValue(),allParams[i].getFieldType() ));
					logger.log(Level.INFO,allParams[i].getFieldName()+":"+ SupportedTypes.getValue(searchBean.getEntityName(), allParams[i].getFieldValue(),allParams[i].getFieldType() ));
				}
			}
			else
			{
				logger.log(Level.INFO,"Params passed are null");
			}
			entityClass = Class.forName(entityName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity exists " + entityName);
		} 
		if(searchBean.getPageInfo() == null)
		{
			logger.log(Level.INFO,"No Page Info");
			return dbManager.runQuery(entityClass, entity);
		}
		else
		{
			long startRecord = (searchBean.getPageInfo().getPageNum() - 1) * searchBean.getPageInfo().getPageSize(); 
			long endRecord = searchBean.getPageInfo().getPageNum() * searchBean.getPageInfo().getPageSize(); 
			logger.log(Level.INFO,"Page Info found startRecord=" + startRecord +",endRecord="+endRecord);
			return dbManager.runQuery(entityClass, entity,startRecord,endRecord);
		}
	}

}
