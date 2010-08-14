package com.ai;
import net.rim.device.api.system.ApplicationDescriptor;


public class options 
{
	private static ApplicationDescriptor descriptor = ApplicationDescriptor.currentApplicationDescriptor(); 
	final static int VENTASHOY=1;
	final static int VENTASAYER=2;
	final static int VENTASSEMANA=3;
	final static int VENTASDETALLES=4;
	final static int IDTIENDA=5;
	final static int PIN=6;
	final static int WEBMETHOD=7;
	final static int MAIN=8;
	final static int FECHADESDE=9;
	final static int FECHAHASTA=10;
	final static int CANTIDAD=11;
	final static int VENTASRANGO=12;
	final static int AVATAR=13;
	final static int FONTHEADER=14;
	final static int FONTDETAILS=15;

	
	final static int BLUE=1799612;
	final static int WHITE=16777215;
	final static int BLACK=0;
	final static String appName=descriptor.getName();
	final static String appVersion=descriptor.getVersion();
	public static String getName(int action)
	{		
		switch(action)
		{
			case VENTASHOY:
				return "Ventas Hoy";
			case VENTASAYER:
				return "Ventas Ayer";
			case VENTASDETALLES:
				return "Detalles";
			case VENTASSEMANA:
				return "Ventas Semana";
			case IDTIENDA:
				return "Id. Tienda";	
			case PIN:
				return "PIN";	
			case WEBMETHOD:
				return "webmethod";	
			case MAIN:
				return "main";	
			case FECHADESDE:
				return "Fecha Desde";	
			case FECHAHASTA:
				return "Fecha Hasta";	
			case CANTIDAD:
				return "Cantidad";	
			case VENTASRANGO:
				return "Ventas Rango";	
			case AVATAR:
				return "Avatar";	
			case FONTHEADER:
				return "Font header";	
			case FONTDETAILS:
				return "Font details";	
				
		}
		return "";
	}
	public static int getOption(String option)
	{
		if (option.equals("Ventas Hoy")) return VENTASHOY;
		if (option.equals("Ventas Ayer")) return VENTASAYER;
		if (option.equals("Detalles")) return VENTASDETALLES;
		if (option.equals("Ventas Semana")) return VENTASSEMANA;
		if (option.equals("Id. Tienda")) return IDTIENDA;
		if (option.equals("PIN")) return PIN;
		if (option.equals("webmethod")) return WEBMETHOD;
		if (option.equals("main")) return WEBMETHOD;
		if (option.equals("Fecha Desde")) return FECHADESDE;
		if (option.equals("Fecha Hasta")) return FECHAHASTA;
		if (option.equals("Cantidad")) return FECHAHASTA;
		if (option.equals("Ventas Rango")) return VENTASRANGO;
		if (option.equals("Avatar")) return AVATAR;
		if (option.equals("Font header")) return FONTHEADER;
		if (option.equals("Font details")) return FONTDETAILS;
		return -1;
	}	
	 
}
