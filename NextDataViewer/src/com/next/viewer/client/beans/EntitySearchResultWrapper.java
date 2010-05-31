package com.next.viewer.client.beans;

import java.io.Serializable;

import com.next.common.client.beans.PageInfoBean;

public class EntitySearchResultWrapper implements Serializable {

	String[][] data;
	String[] header;
	private PageInfoBean pageInfo;

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

	public void setPageInfo(PageInfoBean pageInfo) {
		this.pageInfo = pageInfo;
	}

	public PageInfoBean getPageInfo() {
		return pageInfo;
	}
}
