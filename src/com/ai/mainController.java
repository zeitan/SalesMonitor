package com.ai;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;

public class mainController extends UiApplication
{
	static String pin= (Integer.toString(net.rim.device.api.system.DeviceInfo.getDeviceId(),16)).toUpperCase(); 
    public static String getPin() {
		return pin;
	}
	public static void main(String[] args) 
    {
		
    	mainController theApp = new mainController();    	
        theApp.enterEventDispatcher();        
    }
    public mainController() 
    {
    	salesControllerScreen sc=new salesControllerScreen(getPin());
    	//sc.setPin(getPin());
    	pushScreen(sc); 
    	
    }
}
final class mainControllerScreen extends MainScreen 
{
    public mainControllerScreen() 
    {
        super();
        LabelField title = new LabelField("HelloWorld Sample", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
        setTitle(title);
        add(new RichTextField("Test!"));
    }

    public boolean onClose() 
    {
        Dialog.alert("Chau!");
        System.exit(0);
        return true;
    }
}
