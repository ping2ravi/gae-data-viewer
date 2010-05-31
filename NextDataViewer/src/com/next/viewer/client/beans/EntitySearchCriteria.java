package com.next.viewer.client.beans;

import java.io.Serializable;

import com.next.common.client.beans.PageInfoBean;

public class EntitySearchCriteria implements Serializable {

	private EntityColBean[] allColumns;
	private String entityName;
	private String keyName;
	private PageInfoBean pageInfo;
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
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
	public void setPageInfo(PageInfoBean pageInfo) {
		this.pageInfo = pageInfo;
	}
	public PageInfoBean getPageInfo() {
		return pageInfo;
	}
}
