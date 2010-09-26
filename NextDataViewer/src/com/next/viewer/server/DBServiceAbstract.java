package com.next.viewer.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.next.common.client.exceptions.ClientException;
import com.next.viewer.client.DBService;
import com.next.viewer.client.beans.EntityColBean;
import com.next.viewer.client.beans.EntityDataBean;
import com.next.viewer.client.beans.EntityDefnitionBean;
import com.next.viewer.client.beans.EntityDescriptionBean;
import com.next.viewer.client.beans.EntitySearchCriteria;
import com.next.viewer.client.beans.EntitySearchResultWrapper;
import com.next.viewer.server.data.SetupProperties;
import com.next.viewer.server.entity.EntityDefnition;
import com.next.viewer.server.entity.helper.DBManager;
import com.next.viewer.server.entity.helper.EntityHelper;
import com.next.viewer.server.entity.helper.GenericEntityHelper;
import com.next.viewer.server.entity.helper.JDOManager;
import com.next.viewer.server.exceptions.ApplicationException;
import com.next.viewer.server.exceptions.NoSuchENtity;
import com.next.viewer.server.util.ReflectionUtil;
import com.next.viewer.server.util.SupportedTypes;

public abstract class DBServiceAbstract extends RemoteServiceServlet implements DBService{

	private static Logger logger = Logger.getLogger(DBServiceAbstract.class.getName());
	@Override
	public EntityDefnitionBean createEntity(EntityDefnitionBean entity) {
		DBManager dbManager = null;
		try{
			dbManager = getDBManager();
			System.out.println("Creating Entity " + entity.getName());
			EntityDefnition entityDefnition = new EntityDefnition();
			entityDefnition.setKeyField(entity.getKeyField());
			entityDefnition.setName(entity.getName());
			System.out.println("Saving Entity " + entity.getName());
			entityDefnition = EntityHelper.getInstance().createEntityDefnition(dbManager, entityDefnition);
			System.out.println("Saved Entity " + entity.getName());
			entity.setId(entityDefnition.getId());
			dbManager.commitTransaction();
		}catch(Exception ex)
		{
			dbManager.rollbackTransaction();
			System.out.println("Error Occured " + ex.getMessage());
			System.out.println("Error Occured " + ex.getClass().getName());
			System.out.println("Error Occured " + ex.getLocalizedMessage());
			System.out.println("Error Occured " + ex);
			ex.printStackTrace();
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
		DBManager dbManager = null;
		try{
			dbManager = getDBManager();
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
		DBManager dbManager = null;
		System.out.println("Start");
		for(EntityColBean oneEntityKey:entities)
		{
			try{
			dbManager = getDBManager();
			GenericEntityHelper geh = GenericEntityHelper.getInstance();
			Object dbEntityKey = ReflectionUtil.getEntityKey(entityName,oneEntityKey);
			if(dbEntityKey == null)
				throw new ClientException("Data type not supported for PK field");
			System.out.println("deleting Object " + dbEntityKey);
			geh.deleteGenericEntity(dbManager, entityName, dbEntityKey);
			System.out.println("Finished Deleting ");
			dbManager.commitTransaction();
			}catch(Exception ex)
			{
				ex.printStackTrace();
				dbManager.rollbackTransaction();
				throw new ClientException(ex);
			}
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
			logger.info("DB manger is ready");
			logger.info("getting data");
			List data = geh.findGenericEntity(dbManager, searchBean);
			logger.info("converting data");
			returnEntitySearchResultWrapper = ReflectionUtil.getSearchResultData(data, searchBean.getEntityName());
			returnEntitySearchResultWrapper.setPageInfo(searchBean.getPageInfo());
			dbManager.commitTransaction();
		}catch(NoSuchENtity ex)
		{
			dbManager.rollbackTransaction();
			logger.log(Level.SEVERE, "Error " + ex);
			throw new ClientException(ex);
		}catch(Exception ex){
			dbManager.rollbackTransaction();
			logger.log(Level.SEVERE, "Error " + ex);
			throw new ClientException("Internal Server error " + ex.getMessage());
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
		}catch(Exception ex){
			dbManager.rollbackTransaction();
			ex.printStackTrace();
			throw new ClientException("Internal Server error ");
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
		DBManager dbManager = null;
		try{
			dbManager = getDBManager();
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
		Logger logger = Logger.getLogger(this.getClass().getName());
		try{
			GenericEntityHelper geh = GenericEntityHelper.getInstance();
			logger.info("Getting DBManger");
			dbManager = getDBManager();
			logger.info("Getting EntityKey");
			Object dbEntityKey = ReflectionUtil.getEntityKey(entity);
			logger.info("Found EntityKey");
			if(dbEntityKey == null)
			{
				logger.info("Data type not supported for PK field");
				throw new ClientException("Data type not supported for PK field");
			}
			logger.info("Get Object from DB");
			Object dbEntity = geh.getGenericEntityById(dbManager, entity.getEntityName(), dbEntityKey);
			logger.info("Db ENtity Foiund as " + dbEntity);
			if(dbEntity == null)
			{
				logger.info("Cannot update data, PK value ["+dbEntityKey+"] do not exists in Database");
				throw new ClientException("Cannot update data, PK value ["+dbEntityKey+"] do not exists in Database");
			}
			logger.info("Updating Entity");
			ReflectionUtil.updateEntityDataObject(entity.getColumns(), dbEntity);
			geh.updateGenericEntity(dbManager, dbEntity);

			/*
			for(int i=0;i<entity.getColumns().length;i++)
			{
				if(entity.getPkField().equals(entity.getColumns()[i].getFieldName()))
					entity.getColumns()[i].setFieldValue(ReflectionUtil.getPropertyValue(dbEntity,entity.getPkField() ,entity.getColumns()[i].getFieldType(), false));
			}
			*/
			dbManager.commitTransaction();
			}catch(NoSuchENtity ex)
			{
				dbManager.rollbackTransaction();
				logger.log(Level.SEVERE,"Ravi Exception",ex);
				throw new ClientException(ex);
			}catch(Exception ex){
				dbManager.rollbackTransaction();
				logger.log(Level.SEVERE,"Ravi Exception",ex);
				ex.printStackTrace();
				throw new ClientException("Internal Server error ");
			}
			return entity;
	}

	@Override
	public EntityDefnitionBean[] findAllEntity(String entityName) throws ClientException {

		DBManager dbManager = null;
		try{
			dbManager = getDBManager();
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
		}catch(Exception ex){
			dbManager.rollbackTransaction();
			ex.printStackTrace();
			throw new ClientException("Internal Server error ");
		}
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
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ClientException("Internal Server error ");
		}
		return entity;
	}

	public abstract Object getPersistanceManagerFactory();
	public DBManager getDBManager() throws ApplicationException
	{
		SetupProperties sp = getSetupProperties();
		String dbLayerType = sp.getDbLayerType();
		if("JDO".equals(dbLayerType))
		{
			PersistenceManagerFactory pmf = (PersistenceManagerFactory)getPersistanceManagerFactory();
			PersistenceManager pm = pmf.getPersistenceManager();
			pm.currentTransaction().begin();
			DBManager dBManager = new JDOManager(pm);
			return dBManager;
		}
		throw new ApplicationException("Data layer type "+ dbLayerType +" not supported");
	}

	private SetupProperties getSetupProperties()
	{
		SetupProperties sp = SetupProperties.getInstance();
		if(!sp.isLoaded())
			sp.loadProperties(getPropertyFileNameAndPath());
		return sp;
	}
	@Override
	public String[] deleteEntity(String[] entities) {
		DBManager dbManager = null;
		try{
			dbManager = getDBManager();
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
	protected String getWebDir()
	{
		return this.getServletContext().getContextPath();
	}
	protected String getPropertyFileNameAndPath()
	{
		return this.getServletConfig().getServletContext().getRealPath("WEB-INF/dbviewer.ptoperties");
	}
}
