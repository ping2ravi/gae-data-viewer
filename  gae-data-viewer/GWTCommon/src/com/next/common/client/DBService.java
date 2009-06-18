package com.next.common.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntityRowBean;
import com.next.common.client.beans.EntitySearchCriteria;
import com.next.common.client.exceptions.ClientException;

public interface DBService extends RemoteService {

	public EntityDescriptionBean[] getAllEntities()  throws ClientException;
	public EntityDefnitionBean createEntity(EntityDefnitionBean entity);
	public EntityDefnitionBean updateEntity(EntityDefnitionBean entity);
	public String[] deleteEntity(String[] entities);
	public void deleteEntity(Long id);
	public EntityDescriptionBean getEntityDescription(Long id);
	public EntityRowBean createEntityData(EntityRowBean entity);
	public EntityRowBean updateEntityData(EntityRowBean entity);
	public void deleteEntityData(EntityRowBean entity);
	public EntityRowBean[] findEntityData(EntitySearchCriteria searchBean);
	public EntityDefnitionBean[] findAllEntity(String entityName);
	public EntityDescriptionBean getEntityDescription(String className)  throws ClientException;
}
