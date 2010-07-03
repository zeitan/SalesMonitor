package com.ai;

import java.util.Hashtable;

import org.ksoap2.serialization.*;
//import org.ksoap2.*;
//import org.ksoap2.transport.*;

public class Complex implements KvmSerializable
{

	public String tienda;
	public String fecha;
	public String hora;
	public String venta;
	
	
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0)
		{
		case 0:
			return tienda;
		case 1:
			return fecha;
		case 2:
			return hora;
		case 3:
			return venta;
			
		}
		return null;
	}

	public int getPropertyCount() 
	{	
		return 4;
	}
	
	 public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) 
	 {
	        switch (index)
	        {
	        case 0:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "tienda";
	            break;
	        case 1:
	            info.type = PropertyInfo.INTEGER_CLASS;
	            info.name = "fecha";
	            break;
	        case 2:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "hora";
	            break;
	        case 3:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "venta";
	            break;	            
	        default:
	            break;
	        }
	    }

	    
	    public void setProperty(int index, Object value) {
	        switch (index) {
	        case 0:
	            tienda = value.toString(); 
	            break;
	        case 1: 
	            fecha = value.toString();
	            break;	        
	        case 2: 
	            hora = value.toString();
	            break;	        
	        case 3: 
	            venta = value.toString();
	            break;	         
	        default:
	            break;
	        }
	    }

}
