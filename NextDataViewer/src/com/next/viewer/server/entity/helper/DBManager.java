package com.next.viewer.server.entity.helper;

import java.util.List;
import java.util.Map;

public interface DBManager {

	public Object createObject(Object obj);
	public Object updateObject(Object obj);
	public void deleteObjectById(Class cls,Object id);
	public void deleteObject(Object obj);
	public void deleteAllObject(List<Object> objs);
	public Object getObjectById(Class cls,Object id);
	public List runQuery(Class cls,Criteria crit);
	public List runQuery(Class cls, Map<String, Object> crit,long from,long pageSize);
	public List runQuery(Class cls,Map<String, Object> crit);
	public void commitTransaction();
	public void rollbackTransaction();
}
