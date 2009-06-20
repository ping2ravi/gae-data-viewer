package com.next.common.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.next.common.client.beans.EntityColBean;
import com.next.common.client.beans.EntityDataBean;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntityRowBean;
import com.next.common.client.beans.EntitySearchCriteria;
import com.next.common.client.beans.EntitySearchResultWrapper;

public interface DBServiceAsync {
	public void getAllEntities(AsyncCallback<EntityDescriptionBean[]> callback);
	public void createEntity(EntityDefnitionBean entity,AsyncCallback<EntityDefnitionBean > callback);
	public void updateEntity(EntityDefnitionBean entity,AsyncCallback<EntityDefnitionBean> callback);
	public void deleteEntity(Long id,AsyncCallback callback);
	public void getEntityDescription(Long id,AsyncCallback<EntityDescriptionBean> callback);
	public void createEntityData(EntityDataBean entity,AsyncCallback<EntityDataBean> callback);
	public void updateEntityData(EntityDataBean entity,AsyncCallback<EntityDataBean> callback);
	public void deleteEntityData(String entityName,EntityColBean[] entities,AsyncCallback<EntityColBean[]> callback);
	public void findEntityData(EntitySearchCriteria searchBean,AsyncCallback<EntitySearchResultWrapper> callback);
	public void findAllEntity(String entityName,AsyncCallback<EntityDefnitionBean[]> callback);
	public void getEntityDescription(String className,AsyncCallback<EntityDescriptionBean> callback);
	public void deleteEntity(String[] entities,AsyncCallback<String[]> callback);

}
