package com.ai;
import net.rim.device.api.ui.ContextMenu;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

/**
 * 
 * @author chad.lafontaine
 *
 */
public class labelhyperlink extends LabelField {

    private int pageCount = -1;
    private float cantidad;
    private salesControllerScreen ventasmain;
    private Object value;    
    /**
     * 
     * @param hyperlinkLabel - label to display link
     */    
    public labelhyperlink(String hyperlinkLabel, int pageCount) {
        super(hyperlinkLabel, FOCUSABLE);
        this.pageCount = pageCount;
        Font font = getBasefontSize(10);
        setFont(font.derive(Font.UNDERLINED | Font.PLAIN));        
    }
    public labelhyperlink(parameter param) {
        super(param.getName(), FOCUSABLE);        
        //this.pageCount = pageCount;
        Font font = getBasefontSize(10);
        setFont(font.derive(Font.UNDERLINED | Font.PLAIN));
        this.value=param.getValue();
    } 
    public labelhyperlink(String hyperlinkLabel, Object params) {
        super(hyperlinkLabel, FOCUSABLE);
        this.pageCount = pageCount;
        Font font = getBasefontSize(10);
        setFont(font.derive(Font.UNDERLINED | Font.PLAIN));        
    }    
    public labelhyperlink(String hyperlinkLabel, int pageCount,float cantidad, salesControllerScreen main) 
    {    	
        super(hyperlinkLabel, FOCUSABLE);
        this.pageCount = pageCount;
        Font font = getBasefontSize(10);
        setFont(font.derive(Font.UNDERLINED | Font.PLAIN));
        this.cantidad=cantidad;
        this.ventasmain=main;
        //mGetLinkMenuItem = new SampleOpenScreenMenuItem(hyperlinkLabel);
        //mGetLinkMenuItem = new SampleOpenScreenMenuItem(hyperlinkLabel,cantidad,main);
    } 

    public boolean navigationClick(int status, int time)    
    {
    	
    	if(this.getText().equals("Detalles"))
    		UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount,this.cantidad,this.ventasmain));
    	else
    		if(this.getText().equals("Semana"))
    			UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount,this.cantidad,this.ventasmain));
    		else
        		if(this.getText().equals("Ayer"))
        			UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount,this.cantidad,this.ventasmain));
            		else
                		if(this.getText().equals("Hoy"))
                			UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount,this.cantidad,this.ventasmain));
    	return true;
    }
    


    /**
     * Base font of the application. Method is static for other UI components to
     * call for calculation of display fields.
     * 
     * @return
     */
    public static Font getBasefontSize(int size) {
        Font baseFont = null;
        try {
            baseFont = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, size);
        } catch (ClassNotFoundException e) {
            baseFont = Font.getDefault().getFontFamily().getFont(FontFamily.SCALABLE_FONT, size);
        }

        return baseFont;
    }
}

