package com.next.viewer.server.entity.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class JDOManager implements DBManager{

	Logger logger = Logger.getLogger(this.getClass().getName());
	private PersistenceManager pm;
	public JDOManager(PersistenceManager pm)
	{
		this.pm = pm; 
	}
	@Override
	public Object createObject(Object obj) {
		return pm.makePersistent(obj);
	}

	@Override
	public void deleteObjectById(Class cls,Object id) {
		Object obj = getObjectById(cls,id);
		pm.deletePersistent(obj);
	}
	public void deleteObject(Object obj) {
		pm.deletePersistent(obj);
	}

	@Override
	public Object getObjectById(Class cls,Object id) {
		// TODO Auto-generated method stub
		System.out.println("Getting object for id = " + id);
		return pm.getObjectById(cls, id);
	}

	@Override
	public List runQuery(Class cls,Criteria crit) {
		String criteria = "";
		Query query;
		List returnResults = new ArrayList();
		if(crit != null)
		{
			criteria = crit.getWhereClause();
			System.out.println("Criteria is " +criteria);
			query = pm.newQuery(cls,criteria);
		}
		else
			query = pm.newQuery(cls);

		query.setRange(0, 100);
		

	    try {
	        List results = (List) query.execute();
	        for(Object obj:results)
	        {
	        	returnResults.add(obj);
	        }
	    } finally {
	        query.closeAll();
	    }
	    return returnResults;
	}

	@Override
	public Object updateObject(Object obj) {
		return pm.makePersistent(obj);
	}
	@Override
	public void commitTransaction() {
		pm.currentTransaction().commit();
		pm.close();
	}
	@Override
	public void rollbackTransaction() {
		pm.currentTransaction().rollback();
		pm.close();
	}
	@Override
	public List runQuery(Class cls, Map<String, Object> crit) {
		return runQuery(cls, crit, 0, 20);
	}
	@Override
	public List runQuery(Class cls, Map<String, Object> crit,long startRecord,long endRecord) {
		Query query;
		List returnResults = new ArrayList();
		List results;
		if(crit != null && crit.size() > 0)
		{
			logger.log(Level.INFO, "Cirt is not null");
			String criteria = null;
			Object value;
			String fieldName;
			for(String key:crit.keySet())
			{
				value = crit.get(key);
				if(value == null)
					continue;
				fieldName = getFieldName(key);
				if(value == null)
					continue;
				if(criteria == null)
					criteria = fieldName +"== p_" + fieldName+" ";
				else
					criteria = criteria + " && " + fieldName +"== p_" + fieldName+" ";
			}
			logger.log(Level.INFO, "Criteria is " +criteria);
			query = pm.newQuery(cls,criteria);
			Map paramMap = new HashMap();
			String paramName;
			for(String key:crit.keySet())
			{
				fieldName = getFieldName(key);
				value = crit.get(key);
				paramName = "p_" + fieldName;
				paramMap.put(paramName, value);
				logger.log(Level.INFO, "Declare Param = " +getClassType(value.getClass().getName())+ " "+ paramName);
				query.declareParameters(getClassType(value.getClass().getName())+ " "+ paramName);
			}
			query.setRange(startRecord, endRecord);
			logger.log(Level.INFO,"Running query with map"); 
			results = (List) query.executeWithMap(paramMap);
		}
		else
		{
			query = pm.newQuery(cls);
			query.setRange(startRecord, endRecord);
			logger.log(Level.INFO,"Running query with out map"); 
	        results = (List) query.execute();
		}
		

	    try {
	        for(Object obj:results)
	        {
	        	returnResults.add(obj);
	        }
	    } finally {
	        query.closeAll();
	    }
	    return returnResults;
	}
	private String getFieldName(String fieldName)
	{
		return fieldName.substring(0, 1).toLowerCase()+fieldName.substring(1);
	}
	private String getClassType(String fieldName)
	{
		if(fieldName.startsWith("class"))
			return fieldName.substring(6);
		return fieldName;
	}
	
}
