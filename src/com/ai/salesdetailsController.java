 package com.ai;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class salesdetailsController extends MainScreen {
	private float cantidad=0;
	private String webmethod="ventas_hoy5hora";	
	private int idtienda;
	private String pin;
	private EncodedImage avatar;
	public salesdetailsController (int idtienda, String pin) {

        super(Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLLBAR);
		//super();
		this.idtienda=idtienda;
		this.pin=pin;
		this.paintScreen();

     }

	public salesdetailsController (int idtienda,float cantidad,salesControllerScreen main) 
	{
		
		this(idtienda,main.getPin());
		if (this.cantidad>cantidad)
			main.repaintScreen();
		
	}

	public salesdetailsController(Hashtable params)
	{
		super(Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLLBAR);
		this.webmethod=((parameter)params.get(options.getName(options.WEBMETHOD))).getValue().toString();
		this.idtienda= ((Integer)((parameter)params.get(options.getName(options.IDTIENDA))).getValue()).intValue();
		float cantidad= ((Float)((parameter)params.get(options.getName(options.CANTIDAD))).getValue()).floatValue();
		salesControllerScreen sc= ((salesControllerScreen)((parameter)params.get(options.getName(options.MAIN))).getValue());
		this.pin=sc.getPin();
		this.avatar=((EncodedImage)((parameter)params.get(options.getName(options.AVATAR))).getValue());
		this.paintScreen();
		if (this.cantidad>cantidad)
			sc.repaintScreen();
		
	}
	private void paintScreen()
	{
        salesmonitormodel model= new salesmonitormodel();
        Hashtable params= new Hashtable();
        int countParams=0;
        //add(new RichTextField("Ventas: " + this.adquireData()));
        try
        {
            String hashkey=salesmonitorutility.hashPin(this.pin);
        	parameter param=new parameter("id", new Integer(this.idtienda));
        	++countParams;
        	params.put(new Integer(countParams), param);
        	
        	param=new parameter("hashkey", hashkey);        	
        	++countParams;
        	params.put(new Integer(countParams), param);

        	
        	Vector result=model.adquireData(this.webmethod, "http://tempuri.org#"+this.webmethod, params);
        	if (result.size()>0)
        	{
        		HorizontalFieldManager mMainPanel;
        		VerticalFieldManager mVerticalPanel;
        		mMainPanel = new HorizontalFieldManager(USE_ALL_HEIGHT| USE_ALL_WIDTH);        		
        		mVerticalPanel = new VerticalFieldManager(VERTICAL_SCROLL | VERTICAL_SCROLLBAR);
        		        		
        		
        		HorizontalFieldManager avatarPanel=new HorizontalFieldManager( USE_ALL_WIDTH);

        		  //A HorizontalFieldManager to hold the column headings.
                //HorizontalFieldManager avatarPanel = new HorizontalFieldManager(HorizontalFieldManager.NO_HORIZONTAL_SCROLL 
                //    | HorizontalFieldManager.NO_VERTICAL_SCROLL | HorizontalFieldManager.USE_ALL_WIDTH);
                      		
        		String fecha=((SoapObject) result.elementAt(0)).getProperty(1).toString();
        		String tienda=((SoapObject) result.elementAt(0)).getProperty(0).toString();
        		
        		BitmapField bf=new BitmapField();
        		bf.setImage(this.avatar);
        		avatarPanel.add(bf);
        		avatarPanel.add(new LabelField(tienda));
        		mVerticalPanel.add(avatarPanel);
        		mMainPanel.add(mVerticalPanel);
        		LabelField status= new LabelField(fecha); 
        		setTitle(options.appName +"-" + options.appVersion);
                setStatus(status);        		
	        	for(int i=0; i<result.size();i++)
	        	{
	        		SoapObject data=(SoapObject) result.elementAt(i); 
	        		String header=data.getProperty(2).toString();
	        		String details="Ventas: "+data.getProperty(3)+" Contado:"+data.getProperty(5)+" Credito:"+data.getProperty(6)+" Devoluciones:"+data.getProperty(7)+" T.Tickets:"+data.getProperty(8);
	        		int colorpanel=(i%2==0)?options.BLUE:options.WHITE;
	        		int colorlabel=(i%2==0)?options.WHITE:options.BLACK;
	        		
	        		mVerticalPanel.add(this.buildPanel(header, details, colorpanel, colorlabel, Integer.parseInt(data.getProperty(4).toString()) ,this.webmethod));
	        		
	        		String cantidadS=data.getProperty(3).toString().replace('.', ' ');
	        		cantidadS=cantidadS.trim();
	        		cantidadS=salesmonitorutility.removeBlankSpace(cantidadS);
	        		cantidadS=cantidadS.replace(',', '.');
	        		this.cantidad+=Float.valueOf(cantidadS).floatValue();
	        	}
	        	add(mMainPanel);
        	}
        }
        catch(Exception ex)
        {
        	add(new RichTextField(ex.getMessage()));
        }		
		
	}
    private HorizontalFieldManager buildPanel(String header, String details, int colorpanel, int colorlabel,  int idtienda,  String webmethod)
    {
    	PanelHorizontalFieldManager HorizontalManager1 = new PanelHorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | USE_ALL_WIDTH );
    	//HorizontalFieldManager explodeManager= new HorizontalFieldManager(HorizontalFieldManager.FOCUSABLE | HorizontalFieldManager.FIELD_HCENTER |  HorizontalFieldManager.FIELD_VCENTER);
		//BitmapField bf=new BitmapField(avatar);
		
		moneylabelfield lfhead=new moneylabelfield(header,colorlabel, FOCUSABLE);
		moneylabelfield lfdetails=new moneylabelfield(details,colorlabel);
	

		try
		{
		Font fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 12);		
		lfhead.setFont(fontheader.derive(Font.UNDERLINED | Font.PLAIN));
		
		Font fontdetails = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 9);		

		lfdetails.setFont(fontdetails.derive(Font.PLAIN));
		
		

		
		HorizontalManager1.setHightlightColor(colorpanel);
		//HorizontalManager1.add(bf);
		VerticalFieldManager textVFM=new VerticalFieldManager();
		//VerticalFieldManager infoVFM=new VerticalFieldManager();
		//moneyhorizontalfiedlmanager buttonManagerHF=new moneyhorizontalfiedlmanager();   
		/*HorizontalFieldManager buttonManagerHF=new HorizontalFieldManager()
		{
	        protected void onUnfocus() {		         
		         super.onUnfocus();
		         deleteAll();
		        }
			
		}
		;*/
		/*
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
		lhl.setFont(fontexploit.derive(Font.PLAIN));*/
		
		textVFM.add(lfhead);
		textVFM.add(lfdetails);		
		HorizontalManager1.add(textVFM);
		
		//explodeManager.add(lhl);
		//HorizontalManager1.add(explodeManager);
		//infoVFM.add(HorizontalManager1);
		//infoVFM.add(buttonManagerHF);
		return HorizontalManager1;
		//mVerticalPanel.add(infoVFM);
		//this.add(mVerticalPanel);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
		    	
    }	
}
