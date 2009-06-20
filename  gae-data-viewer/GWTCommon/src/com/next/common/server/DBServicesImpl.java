package com.next.common.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.next.common.client.DBService;
import com.next.common.client.beans.EntityColBean;
import com.next.common.client.beans.EntityDataBean;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.beans.EntityRowBean;
import com.next.common.client.beans.EntitySearchCriteria;
import com.next.common.client.beans.EntitySearchResultWrapper;
import com.next.common.client.exceptions.ClientException;
import com.next.common.server.entity.Customer;
import com.next.common.server.entity.EntityDefnition;
import com.next.common.server.entity.Greeting;
import com.next.common.server.entity.helper.DBManager;
import com.next.common.server.entity.helper.EntityHelper;
import com.next.common.server.entity.helper.GenericEntityHelper;
import com.next.common.server.entity.helper.JDOManager;
import com.next.common.server.exceptions.NoSuchENtity;
import com.next.common.server.util.PMF;
import com.next.common.server.util.ReflectionUtil;

public class DBServicesImpl extends RemoteServiceServlet implements DBService{

	@Override
	public EntityDefnitionBean createEntity(EntityDefnitionBean entity) {
		DBManager dbManager = getDBManager();
		try{
			EntityDefnition entityDefnition = new EntityDefnition();
			entityDefnition.setKeyField(entity.getKeyField());
			entityDefnition.setName(entity.getName());
			entityDefnition = EntityHelper.getInstance().createEntityDefnition(dbManager, entityDefnition);
			entity.setId(entityDefnition.getId());
			dbManager.commitTransaction();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dbManager.rollbackTransaction();
		}
		
		return entity;
	}

	@Override
	public EntityDataBean createEntityData(EntityDataBean entity) throws ClientException {
		DBManager dbManager = null;
		try{
			GenericEntityHelper geh = GenericEntityHelper.getInstance();
			dbManager = getDBManager();
			Object dbEntity = ReflectionUtil.getEntityDataObject(entity.getColumns(), entity.getEntityName());
			
			/*System.out.println("Author : " + greet.getAuthor());
			System.out.println("Content : " + greet.getContent());
			System.out.println("Date : " + greet.getDate());*/
			System.out.println("converting data");
			geh.createGenericEntity(dbManager, dbEntity);

			for(int i=0;i<entity.getColumns().length;i++)
			{
				if(entity.getPkField().equals(entity.getColumns()[i].getFieldName()))
					entity.getColumns()[i].setFieldValue(ReflectionUtil.getPropertyValue(dbEntity,entity.getPkField() ,entity.getColumns()[i].getFieldType(), false));
			}
			dbManager.commitTransaction();
			return entity;
			}catch(NoSuchENtity ex)
			{
				dbManager.rollbackTransaction();
				throw new ClientException(ex);
			}catch(Exception ex){
				dbManager.rollbackTransaction();
				ex.printStackTrace();
				throw new ClientException("Internal Server error ");
			}
	}

	@Override
	public void deleteEntity(Long id) {
		DBManager dbManager = getDBManager();
		try{
			EntityHelper.getInstance().deleteEntityDefnition(dbManager, id);
			dbManager.commitTransaction();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dbManager.rollbackTransaction();
		}
	}

	@Override
	public EntityColBean[] deleteEntityData(String entityName,EntityColBean[] entities) throws ClientException {
		DBManager dbManager = getDBManager();
		System.out.println("Start");
		try{
			GenericEntityHelper geh = GenericEntityHelper.getInstance();
			for(EntityColBean oneEntityKey:entities)
			{
				Object dbEntityKey = ReflectionUtil.getEntityKey(entityName,oneEntityKey);
				if(dbEntityKey == null)
					throw new ClientException("Data type not supported for PK field");
				System.out.println("deleting Object " + dbEntityKey);
				geh.deleteGenericEntity(dbManager, entityName, dbEntityKey);
			}
			System.out.println("Finished Deleting ");
			dbManager.commitTransaction();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dbManager.rollbackTransaction();
			throw new ClientException(ex);
		}
		return entities;
	}

	@Override
	public EntitySearchResultWrapper findEntityData(EntitySearchCriteria searchBean) throws ClientException {
		EntitySearchResultWrapper returnEntitySearchResultWrapper;
		DBManager dbManager = null;
		try{
			GenericEntityHelper geh = GenericEntityHelper.getInstance();
			dbManager = getDBManager();
			System.out.println("DB manger is ready");
			Map<String, String> searchParams = new HashMap<String, String>();
			System.out.println("getting data");
			List data = geh.findGenericEntity(dbManager, searchBean.getEntityName(), searchParams);
			System.out.println("converting data");
			returnEntitySearchResultWrapper = ReflectionUtil.getSearchResultData(data, searchBean.getEntityName());
			dbManager.commitTransaction();
		}catch(NoSuchENtity ex)
		{
			dbManager.rollbackTransaction();
			throw new ClientException(ex);
		}
		return returnEntitySearchResultWrapper;
	}

	@Override
	public EntityDescriptionBean[] getAllEntities() throws ClientException {
		DBManager dbManager = null;
		List<EntityDescriptionBean> allReturnedEntityBeans = new ArrayList<EntityDescriptionBean>();
		try {
			dbManager = getDBManager();
			List<EntityDefnition> allEntites = EntityHelper.getInstance().findEntityDefnition(dbManager, null);
			
			EntityDescriptionBean beans;
			for(EntityDefnition oneBean : allEntites)
			{
				beans = new EntityDescriptionBean();
				beans.setId(oneBean.getId());
				beans.setEntityName(oneBean.getName());
				beans.setEntityFields(ReflectionUtil.getClassFields(oneBean.getName()));
				beans.setKeyField(oneBean.getKeyField());
				allReturnedEntityBeans.add(beans);
			}
			dbManager.commitTransaction();
		} catch (NoSuchENtity e) {
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw new ClientException(e);
		}
		return (EntityDescriptionBean[])allReturnedEntityBeans.toArray(new EntityDescriptionBean[0]);
	}

	@Override
	public EntityDescriptionBean getEntityDescription(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityDefnitionBean updateEntity(EntityDefnitionBean entity) {
		DBManager dbManager = getDBManager();
		try{
			EntityDefnition entityDefnition = EntityHelper.getInstance().getEntityDefnitionById(dbManager, entity.getId());
			entityDefnition.setKeyField(entity.getKeyField());
			entityDefnition.setName(entity.getName());
			//entityDefnition = EntityHelper.getInstance().updateEntityDefnition(dbManager, entityDefnition);
			entity.setId(entityDefnition.getId());
			dbManager.commitTransaction();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dbManager.rollbackTransaction();
		}
		
		return entity;
	}

	@Override
	public EntityDataBean updateEntityData(EntityDataBean entity) throws ClientException{
		DBManager dbManager = null;
		try{
			GenericEntityHelper geh = GenericEntityHelper.getInstance();
			dbManager = getDBManager();
			Object dbEntityKey = ReflectionUtil.getEntityKey(entity);
			if(dbEntityKey == null)
				throw new ClientException("Data type not supported for PK field");
			System.out.println("Get Object from DB");
			Object dbEntity = geh.getGenericEntityById(dbManager, entity.getEntityName(), dbEntityKey);
			if(dbEntity == null)
				throw new ClientException("Cannot update data, PK value ["+dbEntityKey+"] do not exists in Database");
			ReflectionUtil.updateEntityDataObject(entity.getColumns(), dbEntity);
			geh.updateGenericEntity(dbManager, dbEntity);

			for(int i=0;i<entity.getColumns().length;i++)
			{
				if(entity.getPkField().equals(entity.getColumns()[i].getFieldName()))
					entity.getColumns()[i].setFieldValue(ReflectionUtil.getPropertyValue(dbEntity,entity.getPkField() ,entity.getColumns()[i].getFieldType(), false));
			}
			dbManager.commitTransaction();
			}catch(NoSuchENtity ex)
			{
				dbManager.rollbackTransaction();
				throw new ClientException(ex);
			}catch(Exception ex){
				dbManager.rollbackTransaction();
				ex.printStackTrace();
				throw new ClientException("Internal Server error ");
			}
			return entity;
	}

	@Override
	public EntityDefnitionBean[] findAllEntity(String entityName) {
		
		DBManager dbManager = getDBManager();
		EntityDefnition searchCriteria = new EntityDefnition();
		searchCriteria.setName(entityName);
		List<EntityDefnition> allEntites = EntityHelper.getInstance().findEntityDefnition(dbManager, searchCriteria);
		List<EntityDefnitionBean> allReturnedEntityBeans = new ArrayList<EntityDefnitionBean>();
		
		EntityDefnitionBean beans;
		for(EntityDefnition oneBean : allEntites)
		{
			beans = new EntityDefnitionBean();
			beans.setId(oneBean.getId());
			beans.setName(oneBean.getName());
			beans.setKeyField(oneBean.getKeyField());
			allReturnedEntityBeans.add(beans);
		}
		dbManager.commitTransaction();
		return (EntityDefnitionBean[])allReturnedEntityBeans.toArray(new EntityDefnitionBean[0]);
	}

	@Override
	public EntityDescriptionBean getEntityDescription(String className) throws ClientException{
		EntityDescriptionBean entity = new EntityDescriptionBean();
		try{
			entity.setEntityName(className);
			entity.setEntityFields(ReflectionUtil.getClassFields(className));
		}catch(NoSuchENtity ex)
		{
			throw new ClientException(ex);
		}
		return entity;
	}

	public DBManager getDBManager()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.currentTransaction().begin();
		DBManager dBManager = new JDOManager(pm);
		return dBManager;
	}

	@Override
	public String[] deleteEntity(String[] entities) {
		DBManager dbManager = getDBManager();
		try{
			Long id;
			for(String oneEntry:entities)
			{
				id = Long.parseLong(oneEntry);
				EntityHelper.getInstance().deleteEntityDefnition(dbManager, id);
			}
			dbManager.commitTransaction();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			dbManager.rollbackTransaction();
		}
		return entities;
	}
}
