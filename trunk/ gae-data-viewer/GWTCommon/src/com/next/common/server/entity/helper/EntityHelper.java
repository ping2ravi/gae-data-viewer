package com.next.common.server.entity.helper;

import com.next.common.server.entity.EntityDefnition;

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
	public EntityDefnition updateEntityDefnition(DBManager dbManager,EntityDefnition entityDefnition)
	{
		return (EntityDefnition)dbManager.updateObject(entityDefnition);
	}
	public void deleteEntityDefnition(DBManager dbManager,Long id)
	{
		dbManager.deleteObjectById(id);
	}
}
