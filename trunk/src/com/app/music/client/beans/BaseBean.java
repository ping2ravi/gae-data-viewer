package com.app.music.client.beans;

import java.io.Serializable;

public class BaseBean implements Serializable{

	protected static final long serialVersionUID = 9079396375541402205L;
	private long id;
	private int orderByField;
	private boolean ascending;

	public int getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(int orderByField) {
		this.orderByField = orderByField;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
