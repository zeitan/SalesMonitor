package com.ai;

public class options 
{
	final static int VENTASHOY=1;
	final static int VENTASAYER=2;
	final static int VENTASSEMANA=3;
	final static int VENTASDETALLES=4;
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
		}
		return "";
	}
	public static int getaction(String action)
	{
		if (action.equals("Ventas Hoy")) return VENTASHOY;
		if (action.equals("Ventas Ayer")) return VENTASAYER;
		if (action.equals("Detalles")) return VENTASDETALLES;
		if (action.equals("Ventas Semana")) return VENTASSEMANA;
		return -1;
	}	
}
