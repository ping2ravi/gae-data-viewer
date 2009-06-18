package com.next.common.server.entity.helper;

import java.util.List;
import java.util.Map;

import com.next.common.server.exceptions.NoSuchENtity;


public class GenericEntityHelper {
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
	public Object getGenericEntityById(DBManager dbManager,Class entityClass,Long id)
	{
		return dbManager.getObjectById(entityClass, id);
	}
	public Object updateGenericEntity(DBManager dbManager,Object entity)
	{
		return dbManager.updateObject(entity);
	}
	public void deleteGenericEntity(DBManager dbManager,Class entityClass,Long id)
	{
		dbManager.deleteObjectById(entityClass,id);
	}
	public List findGenericEntity(DBManager dbManager,Class entityClass,Map entity)
	{
		return dbManager.runQuery(entityClass, entity);
	}
	public List findGenericEntity(DBManager dbManager,String entityName,Map entity) throws NoSuchENtity
	{
		
		Class entityClass;
		try {
			entityClass = Class.forName(entityName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity exists " + entityName);
		}
		return dbManager.runQuery(entityClass, entity);
	}

}
