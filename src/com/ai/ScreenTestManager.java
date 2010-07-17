package com.ai;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;
public class ScreenTestManager extends MainScreen 
{
	public ScreenTestManager()
	{
		VerticalFieldManager VerticalManager1 = new VerticalFieldManager(Manager.VERTICAL_SCROLL|Field.NON_FOCUSABLE){
            protected void sublayout(int width, int height) {
            super.sublayout(Display.getWidth()/3, height);
        }
};

		VerticalFieldManager VerticalManager2 = new VerticalFieldManager(Manager.VERTICAL_SCROLL|Manager.HORIZONTAL_SCROLL){
		            protected void sublayout(int width, int height) {
		            super.sublayout((Display.getWidth()/3)*2, height);
		        }
		};
		 
		myHorizontalFieldManager HorizontalManager1 = new myHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | USE_ALL_WIDTH)
		/*{
		            protected void sublayout(int width, int height) {
		            super.sublayout(Display.getWidth()/3, height);
		        }
		}*/;
		
		myHorizontalFieldManager HorizontalManager2 = new myHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | USE_ALL_WIDTH)
		/*{
		            protected void sublayout(int width, int height) {
		            super.sublayout((Display.getWidth()/3)*2, height);
		        }
		}*/;
		
		
		
		LabelField lf=new LabelField("TestTestTestTestTestTestTestTest");
		try
		{
		Font fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 12);		
		lf.setFont(fontheader.derive(Font.UNDERLINED | Font.PLAIN));
		
		LabelField lf2=new LabelField("Test1Test1Test1Test1Test1Test1Test1 Test1Test1Test1Test1 Test1Test1Test1 ");
		Font fontdetails = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 9);
		fontdetails.derive(Font.PLAIN);
		lf2.setFont(fontdetails);
		
		
		HorizontalFieldManager mMainPanel;
		VerticalFieldManager mVerticalPanel;
		mMainPanel = new HorizontalFieldManager();
		mVerticalPanel = new VerticalFieldManager(USE_ALL_HEIGHT| USE_ALL_WIDTH);
		add(mMainPanel);
		mMainPanel.add(mVerticalPanel);
		
		HorizontalManager1.setHightlightColor(15592941);
		HorizontalManager1.add(lf);
		HorizontalManager2.setHightlightColor(16777164);
		HorizontalManager2.add(lf2);
		mVerticalPanel.add(HorizontalManager1);
		mVerticalPanel.add(HorizontalManager2);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//VerticalManager2.add(grid);
		
		//placeholder for use in my title region for the row lable portion
		
		//HorizontalManager3.add(HorizontalManager1);
		
		//column labels to place in the title region
		
		//HorizontalManager3.add(HorizontalManager2);
		
		
		
		//I can add the column lables effectively using setTitle but havent figured out how to get them to scroll in lockstep
		
		//setTitle(HorizontalManager3);
		
		//VerticalManager1 contains the row lables, works perfectly (scrolls vertically but not horizontally)
		//mainHorizontalManager.add(VerticalManager1);
		
		//VerticalManager2 contains the data grid itself, all scrolling works just as desired
		//mainHorizontalManager.add(VerticalManager2);
		
		//add(HorizontalManager1);
		//add(HorizontalManager2);
		/*this.setScrollListener(new ScrollChangeListener() {
		    public void scrollChanged(Manager manager, int newHorizontalScroll, int newVerticalScroll) {
		
		//Attempts to break on this line do not occur when scrolling in the simulator
		
		        setHorizontalScroll(newHorizontalScroll);
		    }
		});*/
	}
	 private class myHorizontalFieldManager extends HorizontalFieldManager implements ScrollChangeListener {
	       
		 	public myHorizontalFieldManager(long style)
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

	        public void paint(Graphics graphics) {
	         if (-1 != mHColor && isFocus()) {
	          graphics.setBackgroundColor(mHColor);	          
	          graphics.clear();
	         }
	          graphics.setBackgroundColor(mHColor);	          
	          graphics.clear();	         
	         //graphics.drawBitmap((0, 0, Display.getWidth(), Display.getHeight(), BitmapProvider.scaledBackground, 0, 0);
	         super.paint(graphics);
	        }

	   }	
}
