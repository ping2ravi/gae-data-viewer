package com.next.common.client.beans;

import java.io.Serializable;

public class EntityColDefinitionBean implements Serializable {
	private String fieldName;
	private String fieldType;
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
