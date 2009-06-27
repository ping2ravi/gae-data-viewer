package com.next.common.client.beans;

public class PageData {
	private String[][] data;
	private String[] header;
	private PageInfoBean pageInfo;
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
	public PageInfoBean getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoBean pageInfo) {
		this.pageInfo = pageInfo;
	}
}
