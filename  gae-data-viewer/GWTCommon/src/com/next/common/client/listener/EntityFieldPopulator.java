package com.next.common.client.listener;

import com.google.gwt.user.client.ui.ListBox;
import com.next.common.client.panels.generic.CommonPanel;
import com.next.common.client.panels.generic.ListBoxPopulator;

public class EntityFieldPopulator implements ListBoxPopulator {

	@Override
	public void populateListBox(ListBox listBox,CommonPanel commonPanel) {
		/*EntityPanel panel = (EntityPanel)commonPanel;
		String entityName = panel.getEntityName();
		String[] allFields = ScreenManager.getEntityFields(entityName);
		listBox.clear();
		if(allFields == null || allFields.length <= 0)
			return;
		for(String oneField:allFields)
		{
			listBox.addItem(oneField, oneField);
		}
		*/
	}

}
