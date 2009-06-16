package com.next.common.client.beans;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;


public class SearchResultWrapperW  implements Serializable,IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5006441180327308668L;
	private Long totalSize;
	//private SearchResultInt[] data;
	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	/**
	 * @return the totalSize
	 */
	public Long getTotalSize() {
		return totalSize;
	}
	/**
	 * @param data the data to set
	 */
	//public void setData(SearchResultInt[] data) {
		//this.data = data;
	//}
	/**
	 * @return the data
	 */
	//public SearchResultInt[] getData() {
		//return data;
	//}	
}
