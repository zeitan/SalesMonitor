package com.ai;
import java.io.IOException;
import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;
import org.xmlpull.v1.XmlPullParserException;




import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;

public class salesControllerScreen extends MainScreen 
{
	private String pin="";
	private String webmethod="ventas_hoy";
	private String webmethodDetails="ventas_hoy5hora";
	final int BLUE=1799612;
	final int WHITE=16777215;
	final int BLACK=0;
    public salesControllerScreen(String pin) 
    {
        super();
        this.pin=pin;
        this.paintScreen();
        new RefreshScreenTask(this);
    }
    public salesControllerScreen(String pin, String webmetod, String webmethoddetails) 
    {
        super();
        this.pin=pin;
        this.webmethod=webmetod;
        this.webmethodDetails=webmethoddetails;
        this.paintScreen();        
        if(this.webmethod.equals("ventas_hoy"))
        	new RefreshScreenTask(this);
    }

	public String getPin() {
		return pin;
	}
  
    private void clearScreen()
    {
    	try
    	{
    		this.deleteAll();
    	}
    	catch(Exception ex)
    	{
    		this.add(new RichTextField(ex.getMessage()));
    	}
    }
   /* private void paintScreen()
    {
        salesmonitormodel model= new salesmonitormodel();
        Hashtable params= new Hashtable();
        int countParams=0;
        String hashkey=salesmonitorutility.hashPin(this.pin);
        //add(new RichTextField("Ventas: " + this.adquireData()));
        try
        {
        	
        	parameter param=new parameter("id", new Integer(1));        	
        	++countParams;
        	params.put(new Integer(countParams), param);
        	
        	param=new parameter("hashkey", hashkey);        	
        	++countParams;
        	params.put(new Integer(countParams), param);
        	
        	Vector result=model.adquireData(this.webmethod, "http://tempuri.org#"+ this.webmethod, params);
        	if(result.size()>0)
        	{
        		String hora=((SoapObject) result.elementAt(0)).getProperty(2).toString();
        		String fecha=((SoapObject) result.elementAt(0)).getProperty(1).toString();                
        		LabelField status= new LabelField(fecha+"/"+hora); 
                setTitle("Monitor de Ventas");
                setStatus(status);
        		
	        	for(int i=0; i<result.size();i++)
	        	{
	        		SoapObject data=(SoapObject) result.elementAt(i);  
	        		String Linea=data.getProperty(0).toString();
	        		RichTextField rfheader=new RichTextField(Linea);
	        		Font fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 12);
	        		fontheader.derive(Font.UNDERLINED | Font.PLAIN);
	        		rfheader.setFont(fontheader);
	        		this.add(rfheader);
	        		Linea="Ventas: "+data.getProperty(3)+" Contado:"+data.getProperty(5)+" Credito:"+data.getProperty(6)+" Devoluciones:"+data.getProperty(7)+" T.Tickets:"+data.getProperty(8);
	        		RichTextField rfdetails=new RichTextField(Linea);
	        		Font fontdetails = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 11);
	        		fontdetails.derive(Font.PLAIN);
	        		rfdetails.setFont(fontdetails);
	        		this.add(rfdetails);	        		
	        		String cantidadS=data.getProperty(3).toString().replace('.', ' ');
	        		cantidadS=cantidadS.trim();
	        		cantidadS=salesmonitorutility.removeBlankSpace(cantidadS);
	        		cantidadS=cantidadS.replace(',', '.');	        		
	        		this.add(new labelhyperlink("Detalles", Integer.parseInt(data.getProperty(4).toString()),Float.valueOf(cantidadS).floatValue(),this));
	        		this.add(new LabelField(""));
	        		
	        	}

        	}
        	else
        		this.add(new RichTextField("No se devolvio información"));
        }        
        catch(Exception ex)
        {
        	this.add(new RichTextField(ex.getMessage()));
        }
    	
    }*/
    /* remove leading whitespace */
    private void paintScreen()
    {
    	Bitmap avatar=Bitmap.getBitmapResource("border.png");
    	salesmonitormodel model= new salesmonitormodel();        
        Hashtable params= new Hashtable();
        int countParams=0;
        String hashkey=salesmonitorutility.hashPin(this.pin);
        //add(new RichTextField("Ventas: " + this.adquireData()));
        try
        {
        	
        	parameter param=new parameter("id", new Integer(1));        	
        	++countParams;
        	params.put(new Integer(countParams), param);
        	
        	param=new parameter("hashkey", hashkey);        	
        	++countParams;
        	params.put(new Integer(countParams), param);
        	
    		HorizontalFieldManager mMainPanel;
    		VerticalFieldManager mVerticalPanel;
    		mMainPanel = new HorizontalFieldManager();
    		mVerticalPanel = new VerticalFieldManager(USE_ALL_HEIGHT| USE_ALL_WIDTH);
    		add(mMainPanel);
    		mMainPanel.add(mVerticalPanel);
    		
        	Vector result=model.adquireData(this.webmethod, "http://tempuri.org#"+ this.webmethod, params);
        	if(result.size()>0)
        	{
        		String hora=((SoapObject) result.elementAt(0)).getProperty(2).toString();
        		String fecha=((SoapObject) result.elementAt(0)).getProperty(1).toString();
        		LabelField status;
        		if(this.webmethod.equals("ventas_hoy"))
        			status= new LabelField(fecha+" "+hora);
        		else
        			status= new LabelField(fecha);        		
                setTitle("Monitor de Ventas");
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
	        		
	        		int colorpanel=(i%2==0)?this.BLUE:this.WHITE;
	        		int colorlabel=(i%2==0)?this.WHITE:this.BLACK;
	        		
	        		mVerticalPanel.add(this.buildPanel(header, details, colorpanel, colorlabel, avatar,Integer.parseInt(data.getProperty(4).toString()) ,Float.parseFloat(cantidadS),this.webmethodDetails));
	        		
	        		
	        	}

        	}
        	else
        		mVerticalPanel.add(new RichTextField("No se devolvio información"));
        }        
        catch(Exception ex)
        {
        	this.add(new RichTextField(ex.getMessage()));
        }    	
    }
    private VerticalFieldManager buildPanel(String header, String details, int colorpanel, int colorlabel, Bitmap avatar, int idtienda, float cantidad, String webmethod)
    {
    	PanelHorizontalFieldManager HorizontalManager1 = new PanelHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | USE_ALL_WIDTH );
    	HorizontalFieldManager explodeManager= new HorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | HorizontalFieldManager.FIELD_HCENTER |  HorizontalFieldManager.FIELD_VCENTER);
		BitmapField bf=new BitmapField(avatar);
		
		moneylabelfield lfhead=new moneylabelfield(header,colorlabel);
		moneylabelfield lfdetails=new moneylabelfield(details,colorlabel);
	

		try
		{
		Font fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 12);		
		lfhead.setFont(fontheader.derive(Font.UNDERLINED | Font.PLAIN));
		
		Font fontdetails = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 9);		

		lfdetails.setFont(fontdetails.derive(Font.PLAIN));
		
		

		
		HorizontalManager1.setHightlightColor(colorpanel);
		HorizontalManager1.add(bf);
		VerticalFieldManager textVFM=new VerticalFieldManager();
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

    	
		parameter param3=new parameter("salesmainScreen",  this);
		paramsDetails.put(options.getName(options.MAIN), param3);
		
    	
		parameter param4=new parameter("webmethod", webmethod);
		paramsDetails.put(options.getName(options.WEBMETHOD), param4);

		
		
		parameter paramLFexploit1=new parameter(options.getName(options.VENTASDETALLES),paramsDetails);
		Vector params=new Vector();
		
		params.addElement(paramLFexploit1);
		
		labelfieldexploit lhl=new labelfieldexploit("+", FOCUSABLE,buttonManagerHF,params, colorlabel );
		Font fontexploit = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 18);
		lhl.setFont(fontexploit.derive(Font.PLAIN));
		
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
    public void repaintScreen()
    {
    	this.clearScreen();
    	this.paintScreen();
    	//this.add(new RichTextField("Repintado"));
    }
    	
    class RefreshScreenTask extends TimerTask
    {
    		private Timer timer;
    		salesControllerScreen SC;
    		public RefreshScreenTask(salesControllerScreen sc)
    		{
    			SC=sc;
    			timer=new Timer();
    			timer.schedule(this,3600000,3600000);
    			
    		}
    		public void run ()
    		{ 
    		 synchronized (Application.getEventLock()) 
    		{
    			 SC.repaintScreen();	
			}
    		     		 
    		//timer.cancel (); 
    		}
    }
        
    
}
