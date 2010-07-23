package com.ai;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.Graphics;

public class moneylabelfield extends LabelField 
{
	private int colorlabel;
	public moneylabelfield(String text, int colorlabel) 
	{
		super(text);
		this.colorlabel=colorlabel;
	}
	
	public moneylabelfield(String text, int colorlabel, long style) 
	{
		super(text,style);
		this.colorlabel=colorlabel;
	}	
    public void paint(Graphics graphics)
    {
        graphics.setColor(this.colorlabel);
        super.paint(graphics);
    }		
}
