package com.next.common.client.panels.generic;

import com.next.common.client.callback.DefaultAsyncCallback;

public class GenericFindDataCallback extends DefaultAsyncCallback<Object> {

	CommonPanel panel;
	public GenericFindDataCallback(CommonPanel panel,String key)
	{
		super(key);
		this.panel = panel;
	}
	@Override
	public void onServiceFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServiceSuccess(Object result) {
		// TODO Auto-generated method stub
		
	}

}
