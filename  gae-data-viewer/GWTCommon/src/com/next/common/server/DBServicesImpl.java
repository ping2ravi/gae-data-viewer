package com.next.common.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.next.common.client.DBService;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntityRowBean;

public class DBServicesImpl extends RemoteServiceServlet implements DBService{

	@Override
	public EntityDefnitionBean createEntity(EntityDefnitionBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityRowBean createEntityData(EntityRowBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEntity(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEntityData(EntityRowBean entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityRowBean[] findEntityData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityDefnitionBean[] getAllEntities() {
		EntityDefnitionBean[] allEntities = new EntityDefnitionBean[2];
		allEntities[0] = new EntityDefnitionBean();
		allEntities[0].setId(1L);
		allEntities[0].setName("com.next.common.server.entity.Customer");
		allEntities[1] = new EntityDefnitionBean();
		allEntities[1].setId(2L);
		allEntities[1].setName("com.next.common.server.entity.Greeting");
		return allEntities;
	}

	@Override
	public EntityDescriptionBean getEntityDescription(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityDefnitionBean updateEntity(EntityDefnitionBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityRowBean updateEntityData(EntityRowBean entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
