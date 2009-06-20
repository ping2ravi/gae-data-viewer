package com.next.common.client.beans;

import java.io.Serializable;

public class EntitySearchResultWrapper implements Serializable {

	String[][] data;
	String[] header;

	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
}
