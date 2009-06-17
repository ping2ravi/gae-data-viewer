package com.next.common.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.next.common.client.DBService;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntityRowBean;
import com.next.common.client.exceptions.ClientException;
import com.next.common.server.entity.Customer;
import com.next.common.server.entity.Greeting;
import com.next.common.server.exceptions.NoSuchENtity;
import com.next.common.server.util.ReflectionUtil;

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
	public EntityDescriptionBean[] getAllEntities() throws ClientException {
		EntityDescriptionBean[] allEntities = new EntityDescriptionBean[2];
		try {
		allEntities[0] = new EntityDescriptionBean();
		allEntities[0].setId(1L);
		allEntities[0].setEntityName(Customer.class.getName());
		allEntities[0].setEntityFields(ReflectionUtil.getClassFields(Customer.class.getName()));
		allEntities[1] = new EntityDescriptionBean();
		allEntities[1].setId(1L);
		allEntities[1].setEntityName(Greeting.class.getName());
		allEntities[1].setEntityFields(ReflectionUtil.getClassFields(Greeting.class.getName()));
		} catch (NoSuchENtity e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ClientException(e);
		}
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
