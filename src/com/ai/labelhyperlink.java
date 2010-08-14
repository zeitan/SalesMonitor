package com.ai;
import java.util.Hashtable;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;

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
    public labelhyperlink(String hyperlinkLabel, int pageCount,float cantidad, salesControllerScreen main) 
    {    	
        super(hyperlinkLabel, FOCUSABLE);
        this.pageCount = pageCount;
        Font font = getBasefontSize(10);
        setFont(font.derive(Font.UNDERLINED | Font.PLAIN));
        this.cantidad=cantidad;
        this.ventasmain=main;
    } 

    public boolean navigationClick(int status, int time)    
    {
    	switch(options.getOption(this.getText()))
    	{
    	 	case options.VENTASDETALLES:
    	 		UiApplication.getUiApplication().pushScreen(new salesdetailsController((Hashtable)this.value));
    	 	break;
    	 	case options.VENTASAYER:    	 		
    	 		try
    	 		{
    	 			parameter param=(parameter)this.value;
    	 			salesControllerScreen sc=(salesControllerScreen)param.getValue();
    	 			sc.SalesYesterday();
    	 		}
    	 		catch(ClassCastException ex)
    	 		{
    	 			UiApplication.getUiApplication().popScreen((salesdetailsController)this.value);
    	 			salesControllerScreen sc=(salesControllerScreen)UiApplication.getUiApplication().getActiveScreen();
    	 			sc.SalesYesterday();
    	 		}
    	 	break;
    	 	case options.VENTASHOY:    	 		
    	 		try
    	 		{
    	 			parameter param1=(parameter)this.value;
    	 			salesControllerScreen sc=(salesControllerScreen)param1.getValue();
    	 			sc.SalesToday();
    	 		}
    	 		catch(ClassCastException ex)
    	 		{
    	 			UiApplication.getUiApplication().popScreen((salesdetailsController)this.value);
    	 			salesControllerScreen sc=(salesControllerScreen)UiApplication.getUiApplication().getActiveScreen();
    	 			sc.SalesToday();
    	 		}    	 		
    	 	break;    	 	
    	 	//case options.VENTASAYER:
    	 	//	UiApplication.getUiApplication().pushScreen(new salesControllerScreen(((parameter)this.value).);
    	 	//break;	
    	 	
    	}    	
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

