package com.next.common.client.beans;

import java.io.Serializable;

public class EntitySearchCriteria implements Serializable {

	private EntityColBean[] allColumns;
	private String entityName;
	public EntityColBean[] getAllColumns() {
		return allColumns;
	}
	public void setAllColumns(EntityColBean[] allColumns) {
		this.allColumns = allColumns;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
