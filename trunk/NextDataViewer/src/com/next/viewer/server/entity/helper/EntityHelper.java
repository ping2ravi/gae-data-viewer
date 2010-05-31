package com.next.viewer.server.entity.helper;

import java.util.List;

import com.next.viewer.server.entity.EntityDefnition;


public class EntityHelper {

	private EntityHelper()
	{
		
	}
	private static EntityHelper instance;
	public static EntityHelper getInstance()
	{
		if(instance ==null)
			instance = new EntityHelper();
		return instance;
	}
	public EntityDefnition createEntityDefnition(DBManager dbManager,EntityDefnition entityDefnition)
	{
		return (EntityDefnition)dbManager.createObject(entityDefnition);
	}
	public EntityDefnition getEntityDefnitionById(DBManager dbManager,Long id)
	{
		return (EntityDefnition)dbManager.getObjectById(EntityDefnition.class, id);
	}
	public EntityDefnition updateEntityDefnition(DBManager dbManager,EntityDefnition entityDefnition)
	{
		return (EntityDefnition)dbManager.updateObject(entityDefnition);
	}
	public void deleteEntityDefnition(DBManager dbManager,Long id)
	{
		dbManager.deleteObjectById(EntityDefnition.class,id);
	}
	public List findEntityDefnition(DBManager dbManager,EntityDefnition entity)
	{
		return dbManager.runQuery(EntityDefnition.class, entity);
	}
}
