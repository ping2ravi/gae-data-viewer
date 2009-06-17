package com.next.common.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntityRowBean;

public interface DBServiceAsync {
	public void getAllEntities(AsyncCallback<EntityDescriptionBean[]> callback);
	public void createEntity(EntityDefnitionBean entity,AsyncCallback<EntityDefnitionBean > callback);
	public void updateEntity(EntityDefnitionBean entity,AsyncCallback<EntityDefnitionBean> callback);
	public void deleteEntity(Long id,AsyncCallback callback);
	public void getEntityDescription(Long id,AsyncCallback<EntityDescriptionBean> callback);
	public void createEntityData(EntityRowBean entity,AsyncCallback<EntityRowBean> callback);
	public void updateEntityData(EntityRowBean entity,AsyncCallback<EntityRowBean> callback);
	public void deleteEntityData(EntityRowBean entity,AsyncCallback callback);
	public void findEntityData(AsyncCallback<EntityRowBean[]> callback);

}
