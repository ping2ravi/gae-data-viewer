package com.next.common.client.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.next.common.client.beans.EntityDefnitionBean;
import com.next.common.client.beans.EntityDescriptionBean;
import com.next.common.client.listener.MenuClickListener;
import com.next.common.client.panels.GenericPanel;
import com.next.common.client.panels.generic.CommonPanel;
import com.next.common.client.panels.generic.FieldTypes;
import com.next.common.client.panels.generic.FieldsBean;
import com.next.common.client.panels.generic.ListBoxPopulator;

public class ScreenManager {

	private static Map<String, CommonPanel> allPanels = new HashMap<String, CommonPanel>();
	private static Map<String,EntityDescriptionBean> entityDefnitionBeanMap = new HashMap<String, EntityDescriptionBean>();
	private static VerticalPanel mainScreen = null;
	private static VerticalPanel screen = null;
	private static VerticalPanel menuScreen = null;
	static{
		menuScreen = new VerticalPanel();
		menuScreen.setWidth("100%");
		menuScreen.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		menuScreen.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		
	    screen = new VerticalPanel();
	    screen.setWidth("100%");
	    screen.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	    screen.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
	    
		mainScreen = new VerticalPanel();
		mainScreen.setWidth("100%");
		mainScreen.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		mainScreen.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

		mainScreen.add(menuScreen);
	    mainScreen.add(screen);
		RootPanel.get("content").add(mainScreen);
	}
	public static void createMainScreen(EntityDescriptionBean[] entityDefnitionBeans)
	{
	    // Create a menu bar
	    MenuBar menu = new MenuBar();
	    menu.setAutoOpen(false);
	    //menu.setWidth("500px");
	    menu.setAnimationEnabled(true);

	    MenuBar entityMenu = new MenuBar(true);
	    entityMenu.setAnimationEnabled(true);
	    menu.addItem(new MenuItem("Entities", entityMenu));
	    if(entityDefnitionBeans != null)
	    {
		    for(int i=0;i<entityDefnitionBeans.length;i++)
		    {
		        entityMenu.addItem(entityDefnitionBeans[i].getEntityName(), new MenuClickListener(entityDefnitionBeans[i].getEntityName()));
		        entityDefnitionBeanMap.put(entityDefnitionBeans[i].getEntityName(), entityDefnitionBeans[i]);
		    }
	    }
	    else{
	        entityMenu.addItem("No Entity Configured", new Command(){
				@Override
				public void execute() {
					Window.alert("Configure entites from Admin menu");
				}
	        	
	        });
	    }

	    // Create the Admin menu
	    
	    MenuBar adminMenu = new MenuBar(true);
	    menu.addItem(new MenuItem("Admin", adminMenu));
	    String[] adminOptions = {"Edit Entity"};
	    for (int i = 0; i < adminOptions.length; i++) {
	      adminMenu.addItem(adminOptions[i], new MenuClickListener(adminOptions[i]));
	    }

		    

		    //menu.ensureDebugId("cwMenuBar");
		    menuScreen.clear();
		    menuScreen.add(menu);
		    screen.clear();
		    screen.add(new HTML("Welcome to Next - GAE data viewer "));

	}
	public static void showMenuPanel(String text)
	{
		CommonPanel currentPanel = allPanels.get(text);
		if(currentPanel  == null)
		{
			EntityDescriptionBean bean = entityDefnitionBeanMap.get(text);
			if(bean == null)
				return;
			List<FieldsBean> allFields = new ArrayList<FieldsBean>();
			String[] fields = bean.getEntityFields();
			if(fields != null)
			{
				FieldsBean oneFieldsBean;
				for(String oneField:fields)
				{
					oneFieldsBean = new FieldsBean(FieldTypes.TEXT_BOX,oneField,oneField,true,null);
					allFields.add(oneFieldsBean);
				}
			}
			currentPanel = new GenericPanel(text,(FieldsBean[])allFields.toArray(new FieldsBean[0]));
			currentPanel.createPanel();
			allPanels.put(text, currentPanel);
		}
		
		if(currentPanel != null)
		{
			screen.clear();
			screen.add(currentPanel);
		}
		else
		{
			Window.alert("Clicked on " + text +", which is not implemented yet");
		}
		
	}
}
