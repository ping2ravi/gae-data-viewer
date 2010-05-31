package com.next.viewer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.next.common.client.exceptions.ClientException;
import com.next.viewer.client.beans.EntityColBean;
import com.next.viewer.client.beans.EntityDataBean;
import com.next.viewer.client.beans.EntityDefnitionBean;
import com.next.viewer.client.beans.EntityDescriptionBean;
import com.next.viewer.client.beans.EntitySearchCriteria;
import com.next.viewer.client.beans.EntitySearchResultWrapper;

public interface DBService extends RemoteService {

	public EntityDescriptionBean[] getAllEntities()  throws ClientException;
	public EntityDefnitionBean createEntity(EntityDefnitionBean entity);
	public EntityDefnitionBean updateEntity(EntityDefnitionBean entity);
	public String[] deleteEntity(String[] entities);
	public void deleteEntity(Long id);
	public EntityDescriptionBean getEntityDescription(Long id);
	public EntityDataBean createEntityData(EntityDataBean entity) throws ClientException;
	public EntityDataBean updateEntityData(EntityDataBean entity) throws ClientException;
	public EntityColBean[] deleteEntityData(String entityName,EntityColBean[] entities) throws ClientException ;
	public EntitySearchResultWrapper findEntityData(EntitySearchCriteria searchBean) throws ClientException;
	public EntityDefnitionBean[] findAllEntity(String entityName) throws ClientException ;
	public EntityDescriptionBean getEntityDescription(String className)  throws ClientException;
}
