package com.next.viewer.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.next.viewer.client.beans.EntityDefnitionBean;
import com.next.viewer.client.factory.ServiceFactory;

public class MainEntityPanel extends VerticalPanel implements ClickHandler{
	private TextBox entityName;
	private Button search;
	private Button create;
	private Button delete;
	private FlexTable resultTable;
	
	public MainEntityPanel()
	{
		FlexTable ft = new FlexTable();
		entityName = new TextBox();
		search = new Button("Search");
		search.addClickHandler(this);
		create = new Button("Create");
		create.addClickHandler(this);
		delete = new Button("Delete");
		delete.setEnabled(false);
		delete.addClickHandler(this);
		
		resultTable = new FlexTable();
		resultTable.addClickHandler(this);
		ft.setWidget(0, 0, new Label("Entity Class Name"));
		ft.setWidget(0, 1, entityName);
		HorizontalPanel hp =new HorizontalPanel();
		hp.add(search);
		hp.add(create);
		hp.add(delete);
		this.add(ft);
		this.add(hp);
		this.add(resultTable);
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(resultTable))
		{
			Cell cell = resultTable.getCellForEvent(event);
			EntityPanel ep = new EntityPanel(resultTable.getText(cell.getRowIndex(), 0),resultTable.getText(cell.getRowIndex(), 1),resultTable.getText(cell.getRowIndex(), 2));
			if(cell.getCellIndex() != 3)
			{
				ep.setModal(true);
				ep.center();
				ep.show();
			}
			
		}
		if(event.getSource().equals(create))
		{
			EntityPanel ep = new EntityPanel();
			ep.setModal(true);
			ep.center();
			ep.show();
		}
		if(event.getSource().equals(delete))
		{
			if(resultTable != null)
			{
				int totalRows = resultTable.getRowCount()-1;
				CheckBox checkBox;
				List<String> allDeletedData = new ArrayList<String>();
				for(int i=totalRows;i>=0;i--)
				{
					checkBox = (CheckBox)resultTable.getWidget(i, 3);
					if(checkBox.getValue())
					{
						allDeletedData.add(resultTable.getText(i, 0));
						resultTable.removeRow(i);
					}
				}
				if(allDeletedData.size() <= 0)
					Window.alert("No Records Selected");
				else
				{
					ServiceFactory.getDBService().deleteEntity((String[])allDeletedData.toArray(new String[0]), new AsyncCallback<String[]>(){
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error "+ caught);
						}
	
						@Override
						public void onSuccess(String[] result) {
							int totalRows = resultTable.getRowCount()-1;
							for(int i=totalRows;i>=0;i--)
							{
								for(int j=0;j<result.length;j++)
								{
									if(result[j].equals(resultTable.getText(i, 0)))
										resultTable.removeRow(i);
								}
									
							}
						}
						
					});
				}
			}
		}
		if(event.getSource().equals(search))
		{
			ServiceFactory.getDBService().findAllEntity(entityName.getText(), new AsyncCallback<EntityDefnitionBean[]>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Search Failed " + caught);
					delete.setEnabled(false);
				}

				@Override
				public void onSuccess(EntityDefnitionBean[] result) {
					resultTable.clear();
					delete.setEnabled(true);
					if(result != null && result.length > 0)
					{
						int i=0;
						for(EntityDefnitionBean oneBean:result)
						{
							resultTable.setText(i, 0, String.valueOf(oneBean.getId()));
							resultTable.setText(i, 1, String.valueOf(oneBean.getName()));
							resultTable.setText(i, 2, String.valueOf(oneBean.getKeyField()));
							resultTable.setWidget(i, 3, new CheckBox());
							i++;
						}
					}
				}
				
			});
		}
	}

}
