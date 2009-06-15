package com.app.music.server.paging;

import java.util.List;

public class PageResult {
    private long currentPage;
    private long totalPages;
    private long totalRecords;
    private List resultData;
	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List getResultData() {
		return resultData;
	}
	public void setResultData(List resultData) {
		this.resultData = resultData;
	}

}
