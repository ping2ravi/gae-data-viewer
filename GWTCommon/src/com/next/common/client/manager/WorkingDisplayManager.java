package com.next.common.client.manager;

import java.util.LinkedList;
import java.util.List;

import com.next.common.client.exceptions.ClientException;
import com.next.common.client.manager.helper.WorkingPopupWindow;

public class WorkingDisplayManager {

	public static final int CREATE = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;
	public static final int SELECT = 4;
	private static WorkingDisplayManager work = new WorkingDisplayManager();
	
	//private DialogBox workingDisplay = null;
	private WorkingPopupWindow workingDisplay = null; 
	private boolean isSaving = false;
	private List<String> workingList = new LinkedList<String>();		
	
	// Hide the constructor.
	private WorkingDisplayManager() {
	}
	
	public static WorkingDisplayManager getWorking() {
		return work;
	}
	
	public void clear() {
		workingList.clear();
	}
	
	public void remove(String sourceId) {
		// workingDisplay.setHTML("Removing = " + sourceId + " - " + currentList());
		if (workingList.contains(sourceId)) {
			workingList.remove(sourceId);
			// workingDisplay.setHTML("REMOVED = " + sourceId + " - " + currentList());
		} else {
			// workingDisplay.setHTML("Unable to find source ID : " + sourceId);
		}
		
		if(workingList.size() <= 0)
		{
			if(workingDisplay != null)
				workingDisplay.hide();
		}
			
	}
		
	public void add(String sourceId, int operation) throws ClientException {
		if (workingList.size() == 0) {
			workingDisplay = new WorkingPopupWindow();
			this.isSaving = isSaving;
			switch(operation)
			{
			case CREATE:
				workingDisplay.setText("Wait while creating Object");
				break;
			case UPDATE:
				workingDisplay.setText("Wait while updating Object");
				break;
			case DELETE:
				workingDisplay.setText("Wait while deleting Object");
				break;
			case SELECT:
				workingDisplay.setText("Wait while loading data");
				break;
			
			}
		}
		else {
		}
		workingList.add(sourceId);
	}
	
	
}
