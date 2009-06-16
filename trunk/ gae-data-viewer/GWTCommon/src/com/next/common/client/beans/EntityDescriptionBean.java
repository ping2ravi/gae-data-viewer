package com.next.common.client.beans;

import java.io.Serializable;

public class EntityDescriptionBean implements Serializable {

	private String entityName;
	private String[] entityFields;
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String[] getEntityFields() {
		return entityFields;
	}
	public void setEntityFields(String[] entityFields) {
		this.entityFields = entityFields;
	}
}
