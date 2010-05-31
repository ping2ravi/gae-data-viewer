package com.next.viewer.client.beans;

import java.io.Serializable;

public class EntityRowBean implements Serializable {

	private EntityColBean[] colData;

	public EntityColBean[] getColData() {
		return colData;
	}

	public void setColData(EntityColBean[] colData) {
		this.colData = colData;
	}
}
