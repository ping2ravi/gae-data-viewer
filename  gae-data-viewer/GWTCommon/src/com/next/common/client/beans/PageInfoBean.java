package com.next.common.client.beans;

import java.io.Serializable;

public class PageInfoBean implements Serializable {

    private int pageNum;
    private int pageSize;
    private boolean calculateTotalPages;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isCalculateTotalPages() {
		return calculateTotalPages;
	}
	public void setCalculateTotalPages(boolean calculateTotalPages) {
		this.calculateTotalPages = calculateTotalPages;
	}
	
}
