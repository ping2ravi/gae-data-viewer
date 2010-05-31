package com.next.viewer.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AboutPanel extends DecoratedPopupPanel{

	public AboutPanel()
	{
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Frame frame = new Frame();
		frame.setUrl("http://gaedataviewer.appspot.com/");
		vp.add(new HTML("Current version is 1.0"));
		vp.add(frame);
		Button okButton = new Button("Ok");
		okButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				AboutPanel.this.hide();
			}
			
		});
		vp.add(okButton);
		this.add(vp);
	}
}
