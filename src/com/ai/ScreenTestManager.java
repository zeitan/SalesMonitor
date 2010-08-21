package com.ai;


import java.util.Hashtable;
import net.rim.device.api.util.DateTimeUtilities;


import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.util.DateTimeUtilities;
import net.rim.device.api.system.*;

import java.util.Vector;
import net.rim.device.api.io.Base64InputStream;
import org.ksoap2.serialization.SoapObject;
import java.util.Date;
import net.rim.device.api.i18n.SimpleDateFormat;
import java.util.Calendar;
public class ScreenTestManager extends MainScreen 
{
	private String webmethod="ventas_hoy";
	private String webmethodDetails="ventas_hoy5hora";
	private ConfigurationsScreen configurationsScreen;
	private int fontsizeheader=DataStore.getFontHeaderSize();
	private int fontsizedetails=DataStore.getFontDetailsSize();
	private Vector datacache=null;
	public ScreenTestManager(String pin)
	{
	
		String[] rangeWeek=this.RangeWeek();
    	salesmonitormodel model= new salesmonitormodel();        
        Hashtable params= new Hashtable();
        String[] fechas= new String[7];
	
        try
        {
            int countParams=0;
            String hashkey=salesmonitorutility.hashPin(pin);
           
            parameter param1=new parameter("desde", rangeWeek[0]);
            ++countParams;
            params.put(new Integer(countParams), param1);
            
            parameter param2=new parameter("hasta", rangeWeek[1]);
            ++countParams;
            params.put(new Integer(countParams), param2);
            
             parameter param3=new parameter("id", new Integer(1));
             ++countParams;	
             params.put(new Integer(countParams), param3);
             
             parameter param4=new parameter("hashkey", hashkey);
             ++countParams;	
             params.put(new Integer(countParams), param4);
             
        Vector data= model.adquireData("ventas_periodo", "http://tempuri.org#ventas_periodo", params);
        if(data.size()>0)
        {
        	for(int i=0;i<data.size();i++)
        	{
        		Vector datainterna=(Vector)data.elementAt(i);
        		String fecha=((SoapObject) datainterna.elementAt(0)).getProperty(1).toString();
        		VerticalFieldManager semanaManager=new VerticalFieldManager();
        		semanaManager.add(new LabelField(fecha));
    			for(int j=0; j<datainterna.size();j++)
    			{
    				SoapObject row=(SoapObject) datainterna.elementAt(j);
    				String header=row.getProperty(0).toString();
    				String details="Ventas: "+row.getProperty(3)+" Contado:"+row.getProperty(5)+" Credito:"+row.getProperty(6)+" Devoluciones:"+row.getProperty(7)+" T.Tickets:"+row.getProperty(8);
    				
    				String cantidadS=row.getProperty(3).toString().replace('.', ' ');
    				cantidadS=cantidadS.trim();
    				cantidadS=salesmonitorutility.removeBlankSpace(cantidadS);
    				cantidadS=cantidadS.replace(',', '.');
    				
    				int colorpanel=(j%2==0)?options.BLUE:options.WHITE;
    				int colorlabel=(j%2==0)?options.WHITE:options.BLACK;
    				byte[] imageBytes = row.getProperty(9).toString().getBytes("UTF-8");
    				byte[] bs = Base64InputStream.decode(imageBytes, 0, imageBytes.length);
    				EncodedImage myImage = EncodedImage.createEncodedImage(bs, 0, bs.length);
    				
    				
    				semanaManager.add(this.buildPanel(header, details, colorpanel, colorlabel, myImage,Integer.parseInt(row.getProperty(4).toString()) ,Float.parseFloat(cantidadS),this.webmethodDetails));
    			}
    			add(semanaManager);
    		}
		        
        }
        }
        catch(Exception ex)
        {
        	System.out.println(ex.getMessage());
        }
        
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

	private String[] RangeWeek()
	{
		  String[] fechas=new String[2];
		  Calendar begindate =Calendar.getInstance();
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          java.util.Date date = begindate.getTime();
          String endDate = dateFormat.format(date);          
          int dayofweek=begindate.get(Calendar.DAY_OF_WEEK);
          if (dayofweek==1)
        	  dayofweek=7;
          else
        	  --dayofweek;
          int dayMinus=dayofweek-1;
          if (dayMinus==0)
          {
        	  fechas[0]=endDate;
        	  fechas[1]=endDate;
          }
          else
          {    	  
	          long ctime = begindate.getTime().getTime();
	          long ctimeMinusNDays = ctime - dayMinus * ((long)DateTimeUtilities.ONEDAY);  // not usre the 'longs are needed
	          begindate.setTime(new Date(ctimeMinusNDays));
	          String beginDate = dateFormat.format(begindate.getTime());
        	  fechas[0]=beginDate;
        	  fechas[1]=endDate;	          
          }
          return fechas;
		
	}
	
	private void DisplayData(Vector result)
	{
		try
		{
        HorizontalFieldManager mMainPanel;
        VerticalFieldManager mVerticalPanel;
        mMainPanel = new HorizontalFieldManager();
        mVerticalPanel = new VerticalFieldManager(USE_ALL_HEIGHT)
        {
        public int getPreferredWidth() {
        return getScreen().getWidth();
        }
       
        protected void sublayout( int maxWidth, int maxHeight ) {
                       int myWidth = Math.min(maxWidth, getPreferredWidth());
                       int myHeight = maxHeight;
        super.sublayout(myWidth, myHeight);
        XYRect rec = getExtent();
        setExtent(myWidth , rec.height);
        }
        }
        ;
        add(mMainPanel);
        mMainPanel.add(mVerticalPanel);
        
        if(result.size()>0)
        {
        this.datacache=result;	 
        String hora=((SoapObject) result.elementAt(0)).getProperty(2).toString();
        String fecha=((SoapObject) result.elementAt(0)).getProperty(1).toString();
        LabelField status;
        if(this.webmethod.equals("ventas_hoy"))
        status= new LabelField(fecha+" "+hora);
        else
        status= new LabelField(fecha);
               setTitle(options.appName +"-" + options.appVersion);
               setStatus(status);
       
			for(int i=0; i<result.size();i++)
			{
				SoapObject data=(SoapObject) result.elementAt(i);
				String header=data.getProperty(0).toString();
				String details="Ventas: "+data.getProperty(3)+" Contado:"+data.getProperty(5)+" Credito:"+data.getProperty(6)+" Devoluciones:"+data.getProperty(7)+" T.Tickets:"+data.getProperty(8);
				
				String cantidadS=data.getProperty(3).toString().replace('.', ' ');
				cantidadS=cantidadS.trim();
				cantidadS=salesmonitorutility.removeBlankSpace(cantidadS);
				cantidadS=cantidadS.replace(',', '.');
				
				int colorpanel=(i%2==0)?options.BLUE:options.WHITE;
				int colorlabel=(i%2==0)?options.WHITE:options.BLACK;
				byte[] imageBytes = data.getProperty(9).toString().getBytes("UTF-8");
				byte[] bs = Base64InputStream.decode(imageBytes, 0, imageBytes.length);
				EncodedImage myImage = EncodedImage.createEncodedImage(bs, 0, bs.length);
				
				
				mVerticalPanel.add(this.buildPanel(header, details, colorpanel, colorlabel, myImage,Integer.parseInt(data.getProperty(4).toString()) ,Float.parseFloat(cantidadS),this.webmethodDetails));
			
			
			}
		
        }
        else
        mVerticalPanel.add(new RichTextField("No se devolvio informaci?n"));
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
	}

    private VerticalFieldManager buildPanel(String header, String details, int colorpanel, int colorlabel, EncodedImage avatar, int idtienda, float cantidad, String webmethod)
    {
     PanelHorizontalFieldManager HorizontalManager1 = new PanelHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE);
     HorizontalFieldManager explodeManager= new HorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | HorizontalFieldManager.FIELD_HCENTER | HorizontalFieldManager.FIELD_VCENTER);     

	BitmapField bf=new BitmapField();
	
	bf.setImage(avatar);
	moneylabelfield lfhead=new moneylabelfield(header,colorlabel);
	moneylabelfield lfdetails=new moneylabelfield(details,colorlabel);


	try
	{
		Font fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, this.fontsizeheader);
		lfhead.setFont(fontheader.derive(Font.UNDERLINED | Font.PLAIN));
		
		Font fontdetails = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, this.fontsizedetails);
		
		lfdetails.setFont(fontdetails.derive(Font.PLAIN));
	
	
	
	
	HorizontalManager1.setHightlightColor(colorpanel);
	HorizontalManager1.add(bf);
	VerticalFieldManager textVFM=new VerticalFieldManager()
	{
	public int getPreferredWidth() {
	return getScreen().getWidth()-60;
	}

	protected void sublayout( int maxWidth, int maxHeight ) {
        int myWidth = Math.min(maxWidth, getPreferredWidth());
        int myHeight = maxHeight;
        super.sublayout(myWidth, myHeight);
		XYRect rec = getExtent();
		setExtent(myWidth , rec.height);
	}
	}
;
VerticalFieldManager infoVFM=new VerticalFieldManager();
moneyhorizontalfiedlmanager buttonManagerHF=new moneyhorizontalfiedlmanager();
/*HorizontalFieldManager buttonManagerHF=new HorizontalFieldManager()
{
protected void onUnfocus() {
super.onUnfocus();
deleteAll();
}

}
;*/

Hashtable paramsDetails= new Hashtable();

    
parameter param1=new parameter("idtienda", new Integer(idtienda));
paramsDetails.put(options.getName(options.IDTIENDA), param1);

    
parameter param2=new parameter("cantidad", new Float(cantidad));
paramsDetails.put(options.getName(options.CANTIDAD), param2);

    
parameter param3=new parameter("salesmainScreen", this);
paramsDetails.put(options.getName(options.MAIN), param3);

    
parameter param4=new parameter("webmethod", webmethod);
paramsDetails.put(options.getName(options.WEBMETHOD), param4);

parameter param5=new parameter("avatar", avatar);
paramsDetails.put(options.getName(options.AVATAR), param5);

parameter param6=new parameter("fontheader", new Integer(this.fontsizeheader));
paramsDetails.put(options.getName(options.FONTHEADER), param6);

parameter param7=new parameter("fontdetails", new Integer(this.fontsizedetails));
paramsDetails.put(options.getName(options.FONTDETAILS), param7);



parameter paramLFexploit1=new parameter(options.getName(options.VENTASDETALLES),paramsDetails);


Vector params=new Vector();

params.addElement(paramLFexploit1);
if(this.webmethod.equals("ventas_hoy"))
{
	parameter paramSalesYesterday=new parameter(options.getName(options.VENTASAYER),param3);
	params.addElement(paramSalesYesterday);
}
if(this.webmethod.equals("ventas_ayer"))
{
	parameter paramSalesYesterday=new parameter(options.getName(options.VENTASHOY),param3);
	params.addElement(paramSalesYesterday);
}

final labelfieldexploit lhl=new labelfieldexploit("+", FOCUSABLE,buttonManagerHF,params, colorlabel );
Font fontexploit = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 18);
lhl.setFont(fontexploit.derive(Font.PLAIN));

     //HorizontalFieldManager explodeManager= new HorizontalFieldManager();
textVFM.add(lfhead);
textVFM.add(lfdetails);


HorizontalManager1.add(textVFM);
explodeManager.add(lhl);
HorizontalManager1.add(explodeManager);

infoVFM.add(HorizontalManager1);
infoVFM.add(buttonManagerHF);
return infoVFM;
//mVerticalPanel.add(infoVFM);
//this.add(mVerticalPanel);
}
catch(Exception ex)
{
System.out.println(ex.getMessage());
return null;
}

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
