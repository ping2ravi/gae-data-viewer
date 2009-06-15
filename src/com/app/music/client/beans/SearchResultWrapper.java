package com.app.music.client.beans;

import java.io.Serializable;


public class SearchResultWrapper  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4006441180327308668L;
	private long totalSize;
	private SearchResultInt[] data;
	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	/**
	 * @return the totalSize
	 */
	public long getTotalSize() {
		return totalSize;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(SearchResultInt[] data) {
		this.data = data;
	}
	/**
	 * @return the data
	 */
	public SearchResultInt[] getData() {
		return data;
	}	
}
