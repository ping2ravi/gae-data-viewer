package com.next.common.client.beans;

import java.io.Serializable;

public class EntityDataBean  implements Serializable{

	private String entityName;
	private EntityColBean[] columns;
	private String pkField;
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public EntityColBean[] getColumns() {
		return columns;
	}
	public void setColumns(EntityColBean[] columns) {
		this.columns = columns;
	}
	public String getPkField() {
		return pkField;
	}
	public void setPkField(String pkField) {
		this.pkField = pkField;
	}
}
