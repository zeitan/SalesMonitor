package com.ai;

import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.component.LabelField;

public class moneyhorizontalfiedlmanager extends HorizontalFieldManager
{
	private LabelField lf;
	public void setLf(LabelField lf) {
		this.lf = lf;
	}
	public moneyhorizontalfiedlmanager()
	{
		super();		
		
	}
    protected void onUnfocus() {		         
        super.onUnfocus();
        deleteAll();
        lf.setText("+");
       }	
}
