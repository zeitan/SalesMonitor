package com.ai;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.ScrollChangeListener;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class PanelHorizontalFieldManager extends HorizontalFieldManager implements ScrollChangeListener  
{
 	public PanelHorizontalFieldManager (long style)
 	{
 		super(style);
 	}
    /*protected void setHorizontalScroll2( int horizontalScroll ) {

        super.setHorizontalScroll( horizontalScroll );

    }
 
    public void setHorizontalScroll( int horizontalScroll ) {

// Attempts to break on this line do not occur when scrolling:

        setHorizontalScroll2( horizontalScroll );
    }*/

    public void scrollChanged(Manager manager, int newHorizontalScroll, int newVerticalScroll) {

// Attempts to break on this line do not occur when scrolling in the simulator

        manager.setHorizontalScroll(newHorizontalScroll);
   }
    int mHColor = -1;

    public void setHightlightColor(int color) {
     mHColor = color;
    }

    protected void onFocus(int direction) {
     invalidate();
     super.onFocus(direction);
    }

    protected void onUnfocus() {
     invalidate();
     super.onUnfocus();
    }

    //define width
    public int getPreferredWidth()
    {
        return Display.getWidth();
    }
    //define height
    public int getPreferredHeight()
    {
        return 50;
    }
    protected void sublayout( int maxWidth, int maxHeight )
    {
        super.sublayout(getPreferredWidth(), 
                        getPreferredHeight());
        setExtent(getPreferredWidth(), getPreferredHeight());
    }
    
    public void paint(Graphics graphics) {
      graphics.setBackgroundColor(mHColor);	          
      graphics.clear();
      //graphics.drawBitmap(0, 0, 450, getPreferredHeight(), Bitmap.getBitmapResource("border.png"), 0, 0);
      graphics.drawRect(0, 0, getPreferredWidth(), getPreferredHeight());
      
      //graphics.drawRoundRect(0, 0, getPreferredWidth(), getPreferredHeight(), 10, 10);
     super.paint(graphics);
    }	
}
