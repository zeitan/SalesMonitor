
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
    	salesmonitormodel model= new salesmonitormodel();
        Hashtable params= new Hashtable();
        int countParams=0;
        String hashkey=salesmonitorutility.hashPin(pin);
        //add(new RichTextField("Ventas: " + this.adquireData()));
        try
        {
        
         parameter param=new parameter("id", new Integer(1));
         ++countParams;
         params.put(new Integer(countParams), param);
        
         param=new parameter("hashkey", hashkey);
         ++countParams;
         params.put(new Integer(countParams), param);
         
         if(RadioInfo.getSignalLevel() != RadioInfo.LEVEL_NO_COVERAGE)
        	 this.add(new LabelField("Tiene Señal de Radio"));
         else
        	 this.add(new LabelField("No Tiene Señal de Radio"));
         
         if((RadioInfo.getNetworkService() & RadioInfo.NETWORK_SERVICE_DATA)>0)
        	 this.add(new LabelField("Tiene conexión de datos"));
         else
        	 this.add(new LabelField("No Tiene conexión de datos"));
    
         //this.add(new LabelField(salesmonitorutility.updateConnectionSuffix()));
         this.add(new LabelField(options.appVersion));
         
         Vector result=model.adquireData(this.webmethod, "http://tempuri.org#"+ this.webmethod, params);
         
         this.add(new LabelField(new Integer(result.size()).toString()));
         

        }
        catch(Exception ex)
        {
         this.add(new LabelField(ex.getMessage()));
        }	

	


		
		
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
