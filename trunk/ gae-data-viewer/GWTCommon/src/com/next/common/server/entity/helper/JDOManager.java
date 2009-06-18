package com.next.common.server.entity.helper;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class JDOManager implements DBManager{

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
	public void deleteObjectById(Class cls,Long id) {
		Object obj = getObjectById(cls,id);
		pm.deletePersistent(obj);
	}
	public void deleteObject(Object obj) {
		pm.deletePersistent(obj);
	}

	@Override
	public Object getObjectById(Class cls,Long id) {
		// TODO Auto-generated method stub
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

}
