 package com.ai;
import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

public class salesdetailsController extends MainScreen {
	private float cantidad=0;
	public salesdetailsController (int idtienda, String pin) {

        super(Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLLBAR);
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
        	for(int i=0; i<result.size();i++)
        	{
        		SoapObject data=(SoapObject) result.elementAt(i);  
        		String Linea="Tienda: "+data.getProperty(0)+" Fecha: "+data.getProperty(1)+" Hora: "+data.getProperty(2)+" T.Venta: "+data.getProperty(3)+" T.Contado:"+data.getProperty(5)+" T.Credito:"+data.getProperty(6)+" T.Devoluciones:"+data.getProperty(7)+" T.Tickets:"+data.getProperty(8);
        		RichTextField rf=new RichTextField(Linea);
        		Font font = Font.getDefault();
        		font.derive(Font.PLAIN,2);
        		rf.setFont(font);
        		add(rf);
        		add(new RichTextField("    =======   "));
        		String cantidadS=data.getProperty(3).toString().replace('.', ' ');
        		cantidadS=cantidadS.trim();
        		cantidadS=salesmonitorutility.removeBlankSpace(cantidadS);
        		cantidadS=cantidadS.replace(',', '.');
        		this.cantidad+=Float.valueOf(cantidadS).floatValue();
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
