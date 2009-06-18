package com.next.common.client.beans;

import java.io.Serializable;

public class EntityDefnitionBean implements Serializable {

	private Long id;
	private String name;
	private String keyField;
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
