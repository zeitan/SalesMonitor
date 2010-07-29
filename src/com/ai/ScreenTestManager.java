package com.ai;


import java.util.Hashtable;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.util.DateTimeUtilities;
import net.rim.device.api.system.*;
import java.util.Vector;
import net.rim.device.api.io.Base64InputStream;
import org.ksoap2.serialization.SoapObject;
public class ScreenTestManager extends MainScreen 
{
	public ScreenTestManager()
	{
	
    	/*salesmonitormodel model= new salesmonitormodel();        
        Hashtable params= new Hashtable();
		
        Vector data= model.adquireData("icono", "http://tempuri.org#icono", params);
        if(data.size()>0)
        {
		byte[] imageBytes = (((SoapObject)data.elementAt(0) ).getProperty(0))  .getBytes("UTF-8");

		 

		byte[] bs = Base64InputStream.decode(imageBytes, 0, imageBytes.length);

		 

		EncodedImage myImage = EncodedImage.createEncodedImage(bs, 0, bs.length);

		 

		BitmapField bmf = new BitmapField();

		bmf.setImage(myImage);

		add(bmf)
        }*/
/*		
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
		//long sizeheightpanel=(long)USE_ALL_HEIGHT/4; 
		myHorizontalFieldManager HorizontalManager1 = new myHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | USE_ALL_WIDTH )
		/*{
		            protected void sublayout(int width, int height) {
		            super.sublayout(Display.getWidth()/3, height);
		        }
		}*/;
/*		
		myHorizontalFieldManager HorizontalManager2 = new myHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | USE_ALL_WIDTH );
		HorizontalFieldManager explodeManager= new HorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | HorizontalFieldManager.FIELD_HCENTER |  HorizontalFieldManager.FIELD_VCENTER)
		/*{
		            protected void sublayout(int width, int height) {
		            super.sublayout((Display.getWidth()/3)*2, height);
		        }
		}*/;
		
		
/*		
		BitmapField bf=new BitmapField(Bitmap.getBitmapResource("border.png"));
		
		LabelField lf=new LabelField("Empresa Demo");
		LabelField lf3=new LabelField("Ventas:20.326,56 Contado:25.245,26 Credito:20000,45 Devoluciones:-4500,23 Tickets:711");
		//labelhyperlink lhl=new labelhyperlink("+", 1);		
		labelhyperlink lhl3=new labelhyperlink("Test3", 1);
		try
		{
		Font fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 12);		
		lf.setFont(fontheader.derive(Font.UNDERLINED | Font.PLAIN));
		
		LabelField lf2=new LabelField("Test1Test1Test1Test1Test1Test1Test1 Test1Test1Test1Test1 Test1Test1Test1 ");
		Font fontdetails = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 9);		
		lf2.setFont(fontdetails.derive(Font.PLAIN));
		lf3.setFont(fontdetails.derive(Font.PLAIN));
		
		HorizontalFieldManager mMainPanel;
		VerticalFieldManager mVerticalPanel;
		mMainPanel = new HorizontalFieldManager();
		mVerticalPanel = new VerticalFieldManager(USE_ALL_HEIGHT| USE_ALL_WIDTH);
		add(mMainPanel);
		mMainPanel.add(mVerticalPanel);
		
		HorizontalManager1.setHightlightColor(15592941);
		HorizontalManager1.add(bf);
		VerticalFieldManager textVFM=new VerticalFieldManager();
		VerticalFieldManager infoVFM=new VerticalFieldManager();
		
		HorizontalFieldManager buttonManagerHF=new HorizontalFieldManager()
		{
	        protected void onUnfocus() {		         
		         super.onUnfocus();
		         deleteAll();
		        }
			
		}
		;
		int countParams=0;
		Hashtable paramsDetails= new Hashtable();
    	++countParams;
		parameter param1=new parameter("idtienda", new Integer(1));
		paramsDetails.put(options.getName(options.IDTIENDA), param1);
		
    	++countParams;
		parameter param2=new parameter("cantidad", new Float(50));
		paramsDetails.put(options.getName(options.CANTIDAD), param2);

    	++countParams;
		parameter param3=new parameter("salesmainScreen",  new Object());
		paramsDetails.put(options.getName(options.MAIN), param3);
		
    	++countParams;
		parameter param4=new parameter("webmethod", "ventas_hoy5hora");
		paramsDetails.put(options.getName(options.WEBMETHOD), param4);

		
		
		parameter paramLFexploit1=new parameter(options.getName(options.VENTASDETALLES),paramsDetails);
		parameter paramLFexploit2=new parameter(options.getName(options.VENTASAYER),"PIN");
		
		countParams=0;
		Hashtable paramsRangeDate= new Hashtable();
    	
		++countParams;
		parameter paramfrom=new parameter("desde", "dd-mm-yyyy");
		paramsRangeDate.put(options.getName(options.FECHADESDE), paramfrom);
		++countParams;
		parameter paramto=new parameter("hasta", "dd-mm-yyyy");
		paramsRangeDate.put(options.getName(options.FECHAHASTA), paramto);

		
		parameter paramLFexploit3=new parameter(options.getName(options.VENTASSEMANA),paramsRangeDate);

		Vector params=new Vector();
		
		params.addElement(paramLFexploit1);
		params.addElement(paramLFexploit2);
		params.addElement(paramLFexploit3);
		
		labelfieldexploit lhl=new labelfieldexploit("+", FOCUSABLE,null,params,0 );
		Font fontexploit = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 18);
		lhl.setFont(fontexploit.derive(Font.PLAIN));
		
		textVFM.add(lf);
		textVFM.add(lf3);		
		HorizontalManager1.add(textVFM);
		//HorizontalManager1.add(lf);
		explodeManager.add(lhl);
		HorizontalManager1.add(explodeManager);
		infoVFM.add(HorizontalManager1);
		infoVFM.add(buttonManagerHF);
		//HorizontalManager1.add(lhl2);
		HorizontalManager2.setHightlightColor(16777164);
		HorizontalManager2.add(lf2);
		HorizontalManager2.add(lhl3);
		mVerticalPanel.add(infoVFM);
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
}
