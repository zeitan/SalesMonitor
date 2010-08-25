package com.ai;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;
import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;
import org.xmlpull.v1.XmlPullParserException;
import net.rim.device.api.math.Fixed32;





import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.util.DateTimeUtilities;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.io.Base64InputStream;
import net.rim.device.api.system.*;

public class salesControllerScreen extends MainScreen
{
private String pin="";
private int id=1;
private String webmethod="ventas_hoy";
private String webmethodDetails="ventas_hoy5hora";
private ConfigurationsScreen configurationsScreen;
private int fontsizeheader=DataStore.getFontHeaderSize();
private int fontsizedetails=DataStore.getFontDetailsSize();
private Vector datacache=null;
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
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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

	public void ResizeFont(int sizefont)
	{
		this.fontsizeheader+=sizefont;
		this.fontsizedetails+=sizefont;
		this.clearScreen();
		this.DisplayData(this.datacache);
		DataStore.setFontHeaderSize(fontsizeheader);
		DataStore.setFontDetailsSize(fontsizedetails);
	}
	public void SalesYesterday()
	{
		this.webmethod="ventas_ayer";
		this.webmethodDetails="ventas_ayer5hora";
		this.repaintScreen();
	}
	public void SalesToday()
	{
		this.webmethod="ventas_hoy";
		this.webmethodDetails="ventas_hoy5hora";
		this.repaintScreen();
	}
	public void SalesWeek()
	{
		this.webmethod="ventas_periodo";
		this.clearScreen();
		this.buildPanelRangeWeek();
	}	
	public void SalesALL()
	{
		this.repaintScreen();
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
				
				
				mVerticalPanel.add(this.buildPanel(header, details, colorpanel, colorlabel, myImage,Integer.parseInt(data.getProperty(4).toString()) ,Float.parseFloat(cantidadS),this.webmethodDetails,""));
			
			
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
    private void paintScreen()
    {
     //Bitmap avatar=Bitmap.getBitmapResource("border.png");
     salesmonitormodel model= new salesmonitormodel();
        Hashtable params= new Hashtable();
        int countParams=0;
        String hashkey=salesmonitorutility.hashPin(this.pin);
        //add(new RichTextField("Ventas: " + this.adquireData()));
        try
        {
        
         parameter param=new parameter("id", new Integer(this.id));
         ++countParams;
         params.put(new Integer(countParams), param);
        
         param=new parameter("hashkey", hashkey);
         ++countParams;
         params.put(new Integer(countParams), param);
        
    
         Vector result=model.adquireData(this.webmethod, "http://tempuri.org#"+ this.webmethod, params);
         this.DisplayData(result);

        }
        catch(Exception ex)
        {
        	Font fontheader=null;
	        try
	        {
	        	fontheader = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, this.fontsizeheader);
	        }
			catch (Exception e)
			{
			}
	        VerticalFieldManager mVerticalPanel = new VerticalFieldManager(USE_ALL_HEIGHT | HorizontalFieldManager.FIELD_HCENTER | VerticalFieldManager.FIELD_VCENTER);
	        Bitmap avatar=Bitmap.getBitmapResource("MoNeY.png");
	        BitmapField bf=new BitmapField(avatar);	
	        mVerticalPanel.add(bf);
			LabelField lfhead=new LabelField(ex.getMessage()); 
			lfhead.setFont(fontheader.derive(Font.BOLD));
			mVerticalPanel.add(lfhead);
			add(mVerticalPanel);
	        
        }
	}
    private VerticalFieldManager buildPanel(String header, String details, int colorpanel, int colorlabel, EncodedImage avatar, int idtienda, float cantidad, String webmethod, String fecha)
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
	};
	VerticalFieldManager infoVFM=new VerticalFieldManager();
	moneyhorizontalfiedlmanager buttonManagerHF=new moneyhorizontalfiedlmanager();

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
	parameter param8=new parameter("fecha", fecha);
	paramsDetails.put(options.getName(options.FECHA), param8);
	
	
	
	
	parameter paramLFexploit1=new parameter(options.getName(options.VENTASDETALLES),paramsDetails);
	
	
	Vector params=new Vector();
	
	params.addElement(paramLFexploit1);
	//this.id=idtienda;
	if(this.webmethod.equals("ventas_hoy"))	
	{		
		parameter paramSalesYesterday=new parameter(options.getName(options.VENTASAYER),param3);		
		params.addElement(paramSalesYesterday);
		parameter paramSalesWeek=new parameter(options.getName(options.VENTASSEMANA),param3);
		params.addElement(paramSalesWeek);		
		params.addElement(param1);
	}
	if(this.webmethod.equals("ventas_ayer"))
	{
		parameter paramSalesYesterday=new parameter(options.getName(options.VENTASHOY),param3);
		params.addElement(paramSalesYesterday);
		parameter paramSalesWeek=new parameter(options.getName(options.VENTASSEMANA),param3);
		params.addElement(paramSalesWeek);
		params.addElement(param1);
		
	}
	if(this.webmethod.equals("ventas_periodo"))
	{
		parameter paramSalesToday=new parameter(options.getName(options.VENTASHOY),param3);
		params.addElement(paramSalesToday);
		parameter paramYesterday=new parameter(options.getName(options.VENTASAYER),param3);
		params.addElement(paramYesterday);
		params.addElement(param1);
		
	}	

	if(this.id!=1)
	{
		parameter paramAll=new parameter(options.getName(options.ALL),param3);
		params.addElement(paramAll);
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
    public void repaintScreen()
    {
     this.clearScreen();
     this.paintScreen();
    }
	private String[] RangeWeek()
	{
		  String[] fechas=new String[2];
		  TimeZone tz = TimeZone.getTimeZone("GMT-0430");
		  Calendar begindate =Calendar.getInstance(tz);	
		  int gmtOffset = tz.getOffset(1, begindate.get(Calendar.YEAR), begindate.get(Calendar.MONTH), begindate.get(Calendar.DATE), begindate.get(Calendar.DAY_OF_WEEK), begindate.get(Calendar.MILLISECOND)); 
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		  
          java.util.Date date = begindate.getTime();
          long currentTime=date.getTime()+gmtOffset;
          date.setTime(currentTime); 
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
    private void buildPanelRangeWeek()
    {
		String[] rangeWeek=this.RangeWeek();
    	salesmonitormodel model= new salesmonitormodel();        
        Hashtable params= new Hashtable();    
        try
        {
            int countParams=0;
            String hashkey=salesmonitorutility.hashPin(this.pin);
           
            parameter param1=new parameter("desde", rangeWeek[0]);
            ++countParams;
            params.put(new Integer(countParams), param1);
            
            parameter param2=new parameter("hasta", rangeWeek[1]);
            ++countParams;
            params.put(new Integer(countParams), param2);
            
             parameter param3=new parameter("id", new Integer(this.id));
             ++countParams;	
             params.put(new Integer(countParams), param3);
             
             parameter param4=new parameter("hashkey", hashkey);
             ++countParams;	
             params.put(new Integer(countParams), param4);
             
        Vector data= model.adquireData(this.webmethod, "http://tempuri.org#"+ this.webmethod, params);
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
    				
    				if (j<datainterna.size()-1)
    					semanaManager.add(this.buildPanel(header, details, colorpanel, colorlabel, myImage,Integer.parseInt(row.getProperty(4).toString()) ,Float.parseFloat(cantidadS),"ventas_dia5hora",fecha));
    				else
    					semanaManager.add(this.buildPanel(header, details, colorpanel, colorlabel, myImage,Integer.parseInt(row.getProperty(4).toString()) ,Float.parseFloat(cantidadS),"ventas_hoy5hora",""));
    			}
    			add(semanaManager);
    		}
		        
        }
        }
        catch(Exception ex)
        {
        	System.out.println(ex.getMessage());
        }    	
    	
    }
    
    private void showOptionsScreen() {
        if (null == configurationsScreen) {
         configurationsScreen= new ConfigurationsScreen();
        }
        configurationsScreen.displayOptions();
        UiApplication.getUiApplication().pushModalScreen(configurationsScreen);
        
    }
    MenuItem refreshMenu = new MenuItem("Actualiza Ahora...", 100, 100)
    {
        public void run()
        {
         repaintScreen();
        }
    };
    
    MenuItem FontPlusMenu = new MenuItem("+Font", 100, 100)
    {
        public void run()
        {
         ResizeFont(1);
        }
    };  

    MenuItem FontMinusMenu = new MenuItem("-Font", 100, 100)
    {
        public void run()
        {
         ResizeFont(-1);
        }
    };   
    
    private MenuItem coofigurationMenu = new MenuItem("Configuración",100,10) {
        public void run() {
            showOptionsScreen();
        }
     };
     
    protected void makeMenu(Menu menu, int instance)
    {
    	if(this.webmethod.equals("ventas_hoy"))
    		menu.add(refreshMenu );
        menu.add(coofigurationMenu );
        menu.add(FontPlusMenu );
        menu.add(FontMinusMenu );        
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
    
    class OptionsMenuItem extends MenuItem {

        //float cantidad;
        salesControllerScreen ventasmain;
           public OptionsMenuItem (String menuLabel) {
               super(menuLabel, 0, 100);
           }
           public OptionsMenuItem (String menuLabel,float cantidad, salesControllerScreen main)
           {
            super(menuLabel, 0, 100);
            this.ventasmain=main;
           }
           public void run()
           {
        	   ventasmain.repaintScreen();

           }

       }
    
}

