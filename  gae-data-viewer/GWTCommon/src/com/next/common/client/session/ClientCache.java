package com.next.common.client.session;

import java.util.HashMap;
import java.util.Map;

import com.next.common.client.beans.EntityDescriptionBean;

public class ClientCache {

	private static Map<String, EntityDescriptionBean> entities = new HashMap<String, EntityDescriptionBean>();
	
	public static void setEntityCache(EntityDescriptionBean[] beans)
	{
		if(beans== null || beans.length <= 0)
			return;
		for(EntityDescriptionBean oneBean:beans)
		{
			setEntityCache(oneBean);
		}
	}
	public static void setEntityCache(EntityDescriptionBean oneBean)
	{
		if(oneBean== null)
			return;
		entities.put(oneBean.getEntityName(), oneBean);
	}
	public static EntityDescriptionBean getEntityCache(String entityName)
	{
		return entities.get(entityName);
	}
}
