package com.next.common.client.beans;

import java.io.Serializable;

public class EntityDescriptionBean implements Serializable {

	private Long id;
	private String entityName;
	private String keyField;
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	private EntityColDefinitionBean[] entityFields;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public EntityColDefinitionBean[] getEntityFields() {
		return entityFields;
	}
	public void setEntityFields(EntityColDefinitionBean[] entityFields) {
		this.entityFields = entityFields;
	}
}
