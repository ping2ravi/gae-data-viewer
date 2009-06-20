package com.next.common.server.entity.helper;

import java.util.List;
import java.util.Map;

public interface DBManager {

	public Object createObject(Object obj);
	public Object updateObject(Object obj);
	public void deleteObjectById(Class cls,Object id);
	public void deleteObject(Object obj);
	public Object getObjectById(Class cls,Object id);
	public List runQuery(Class cls,Criteria crit);
	public List runQuery(Class cls,Map<String, String> crit);
	public void commitTransaction();
	public void rollbackTransaction();
}
