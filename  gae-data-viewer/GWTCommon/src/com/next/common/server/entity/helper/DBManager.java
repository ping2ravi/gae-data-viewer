package com.next.common.server.entity.helper;

import java.util.List;

public interface DBManager {

	public Object createObject(Object obj);
	public Object updateObject(Object obj);
	public void deleteObjectById(Class cls,Long id);
	public void deleteObject(Object obj);
	public Object getObjectById(Class cls,Long id);
	public List runQuery(Class cls,Criteria crit);
	public void commitTransaction();
	public void rollbackTransaction();
}
