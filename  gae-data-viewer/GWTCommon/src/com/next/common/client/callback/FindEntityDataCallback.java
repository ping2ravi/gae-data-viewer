package com.next.common.client.callback;

import com.google.gwt.user.client.Window;
import com.next.common.client.beans.EntitySearchResultWrapper;
import com.next.common.client.panels.generic.CommonPanel;

public class FindEntityDataCallback extends DefaultAsyncCallback<EntitySearchResultWrapper> {
	private CommonPanel panel;
	public FindEntityDataCallback(CommonPanel panel,String key) {
		super(key);
		this.panel = panel;
	}

	@Override
	public void onServiceFailure(Throwable caught) {
		Window.alert("Error " + caught);
	}

	@Override
	public void onServiceSuccess(EntitySearchResultWrapper result) {
		panel.findServiceSuccess(result.getData(), result.getHeader());
	}

}
