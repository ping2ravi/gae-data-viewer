package com.next.common.client.panels.generic;

import java.io.Serializable;

public class FieldsBean implements Serializable {

	private String type;
	private String labelText;
	private String fieldName;
	private boolean enabled = true;
	private ListBoxPopulator listBoxPopulator;
	public FieldsBean(String type,String labelText,String fieldName,boolean enabled,ListBoxPopulator listBoxPopulator)
	{
		this.type = type;
		this.labelText = labelText;
		this.fieldName = fieldName;
		this.enabled = enabled;
		this.listBoxPopulator = listBoxPopulator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ListBoxPopulator getListBoxPopulator() {
		return listBoxPopulator;
	}

	public void setListBoxPopulator(ListBoxPopulator listBoxPopulator) {
		this.listBoxPopulator = listBoxPopulator;
	}
	
	
}
