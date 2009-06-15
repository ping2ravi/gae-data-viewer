package com.app.music.server.persistance.helper;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.app.music.server.exception.ApplicationException;
import com.app.music.server.paging.PageInfo;
import com.app.music.server.paging.PageResult;
import com.app.music.server.persistance.BaseObject;
import com.app.music.server.session.UserServerSession;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
*
* @author Ravi
*/
public class BasePersistance {
   public BaseObject createBaseObject(UserServerSession userServerSession,BaseObject baseObject) throws ApplicationException {
       PersistenceManager  em = null;
       em = userServerSession.getPersistenceManager ();
       em.makePersistent(baseObject);
       return baseObject;
   }

   public BaseObject updateBaseObject(UserServerSession userServerSession,BaseObject baseObject) throws ApplicationException{
       PersistenceManager  em = null;
       em = userServerSession.getPersistenceManager ();
       baseObject = em.makePersistent(baseObject);
       return baseObject;
   }

   public void deleteBaseObject(UserServerSession userServerSession,Class delClass,Long id) throws ApplicationException {
       PersistenceManager  em = null;
       em = userServerSession.getPersistenceManager ();
       BaseObject baseObject;
       Key k = KeyFactory.createKey(delClass.getSimpleName(), id);
       baseObject = (BaseObject)em.getObjectById(delClass, k);
       em.deletePersistent(baseObject);
   }

   public PageResult findBaseObjectEntities(UserServerSession userServerSession, Class baseObject,PageInfo pageInfo) {
       PersistenceManager  em = userServerSession.getPersistenceManager ();
       String className = getClassNameWithoutPackage(baseObject);
       PageResult pageResult = new PageResult();
       Query q = em.newQuery(baseObject);
       //q.setFilter(filter);
       //q.setOrdering(ordering);
       //q.declareParameters("String lastNameParam");

       if (!pageInfo.isGetAllRecords()) {
    	   q.setRange((pageInfo.getPageNum() - 1)*pageInfo.getPageSize(), (pageInfo.getPageNum() - 1)*pageInfo.getPageSize() + pageInfo.getPageSize());
           //q.setMaxResults(pageInfo.getPageSize());
           //q.setFirstResult((pageInfo.getPageNum() - 1)*pageInfo.getPageSize());
       }
       pageResult.setResultData((List)q.execute());
       pageResult.setCurrentPage(pageInfo.getPageNum());
       if(pageInfo.isGetTotalSize())
       {
           long totalRecords = getBaseObjectEntitiesCount(userServerSession, baseObject,pageInfo);
           pageResult.setTotalRecords(totalRecords);
           long totalPages;
           if(totalRecords% pageInfo.getPageSize() == 0)
               totalPages = totalRecords / pageInfo.getPageSize();
           else
               totalPages = totalRecords / pageInfo.getPageSize() + 1;
           pageResult.setTotalPages(totalPages);

       }
       return pageResult;
   }
   public long getBaseObjectEntitiesCount(UserServerSession userServerSession, Class baseObject, PageInfo pageInfo) {
       PersistenceManager  em = userServerSession.getPersistenceManager ();
       Query query = em.newQuery(baseObject);
       query.setResult ("count(this)");
       //query.setFilter(filter);
       Object[] result = (Object[]) query.execute ();
       return ((Long) Long.valueOf(String.valueOf(result[0])));
   }

   public BaseObject findBaseObjectById(UserServerSession userServerSession,Class baseObject,Long id) {
       PersistenceManager  em = userServerSession.getPersistenceManager ();
       Key k = KeyFactory.createKey(baseObject.getSimpleName(), id);
       return (BaseObject)em.getObjectById(baseObject, k);
   }

   public Long getBaseObjectCount(UserServerSession userServerSession,Class baseObject) {
       PersistenceManager  em = userServerSession.getPersistenceManager ();
       Query query = em.newQuery(baseObject);
       query.setResult ("count(this)");
       Object[] result = (Object[]) query.execute ();
       return ((Long) Long.valueOf(String.valueOf(result[0])));
   }
   public String getClassNameWithoutPackage(Class baseObject)
   {
       String className = baseObject.getName();
       String strs[] = className.split("\\.");
       return strs[strs.length - 1];
   }
   /*public BaseObject executeSqlReturnObject(UserServerSession userServerSession,String jSql)
   {
       PersistenceManager  em = userServerSession.getEntityManager ();
       Query q = em.createQuery(jSql);
       List data = q.getResultList();
       if(data == null || data.size() <= 0)
           return null;
       return (BaseObject)data.get(0);
   }
   */

}
