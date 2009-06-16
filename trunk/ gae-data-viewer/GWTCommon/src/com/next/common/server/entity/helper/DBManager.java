package com.next.common.server.entity.helper;

import java.util.List;

public interface DBManager {

	public Object createObject(Object obj);
	public Object updateObject(Object obj);
	public void deleteObjectById(Long id);
	public Object getObjectById(Long id);
	public List runQuery(String query);
}
