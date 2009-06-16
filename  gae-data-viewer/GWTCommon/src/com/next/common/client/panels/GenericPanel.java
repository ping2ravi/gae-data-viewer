/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.next.common.client.panels;

import java.util.Map;

import com.next.common.client.panels.generic.CommonPanel;
import com.next.common.client.panels.generic.FieldsBean;
import com.next.common.client.panels.generic.UiErrorPanel;

/**
 *
 * @author Ravi
 */
public class GenericPanel extends CommonPanel{

	public GenericPanel(String entityName,FieldsBean[] beans)
	{
	  super(entityName);
      super.setFields(beans);
	}
	@Override
    public Object[][] convertToObjectArray(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getObjectFromParams(Map<String, String> params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean validateObject(Object obj, UiErrorPanel parentPanel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onSave(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void findData(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
