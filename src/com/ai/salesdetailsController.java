 package com.ai;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

public class salesdetailsController extends MainScreen {
	private float cantidad=0;
	public salesdetailsController (int idtienda, String pin) {

        //super(Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLLBAR);
		super();
        salesmonitormodel model= new salesmonitormodel();
        Hashtable params= new Hashtable();
        int countParams=0;
        //add(new RichTextField("Ventas: " + this.adquireData()));
        try
        {
            String hashkey=salesmonitorutility.hashPin(pin);
        	parameter param=new parameter("id", new Integer(idtienda));
        	++countParams;
        	params.put(new Integer(countParams), param);
        	
        	param=new parameter("hashkey", hashkey);        	
        	++countParams;
        	params.put(new Integer(countParams), param);

        	
        	Vector result=model.adquireData("ventas_hoy5hora", "http://tempuri.org#ventas_hoy5hora", params);
        	if (result.size()>0)
        	{
        		String fecha=((SoapObject) result.elementAt(0)).getProperty(1).toString();
        		String tienda=((SoapObject) result.elementAt(0)).getProperty(0).toString();
        		LabelField status= new LabelField(tienda+" "+fecha); 
                setTitle("Monitor de Ventas");
                setStatus(status);        		
	        	for(int i=0; i<result.size();i++)
	        	{
	        		SoapObject data=(SoapObject) result.elementAt(i); 
	        		String Linea=data.getProperty(2).toString();
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
	        		add(new LabelField(""));
	        		String cantidadS=data.getProperty(3).toString().replace('.', ' ');
	        		cantidadS=cantidadS.trim();
	        		cantidadS=salesmonitorutility.removeBlankSpace(cantidadS);
	        		cantidadS=cantidadS.replace(',', '.');
	        		this.cantidad+=Float.valueOf(cantidadS).floatValue();
	        	}
        	}
        }
        catch(Exception ex)
        {
        	add(new RichTextField(ex.getMessage()));
        }
     }
	public salesdetailsController (int idtienda,float cantidad,salesControllerScreen main) 
	{
		this(idtienda,main.getPin());
		if (this.cantidad>cantidad)
			main.repaintScreen();
		
	}

}
