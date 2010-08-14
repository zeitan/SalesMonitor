package com.ai;
import java.io.IOException;
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
import net.rim.device.api.io.Base64InputStream;
import net.rim.device.api.system.*;

public class salesControllerScreen extends MainScreen
{

private String pin="";
private String webmethod="ventas_hoy";
private String webmethodDetails="ventas_hoy5hora";
private ConfigurationsScreen configurationsScreen;
private int fontsizeheader=14;
private int fontsizedetails=10;
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
		this.deleteAll();
		this.DisplayData(this.datacache);
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
        
    
         Vector result=model.adquireData(this.webmethod, "http://tempuri.org#"+ this.webmethod, params);
         this.DisplayData(result);

        }
        catch(Exception ex)
        {
         this.add(new RichTextField(ex.getMessage()));
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
               //UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount));
            //UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount,this.cantidad,this.ventasmain));

           }

       }
    
}

