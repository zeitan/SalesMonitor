package com.ai;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.*;

public class salesmonitorutility 
{
    public static String removeBlankSpace(String source)
    {
    	String sourcenospace="";
    	int beginindex=0;    	
    	for(int i=0;i<source.length();i++)
    	{
    		if (source.charAt(i)==' ')
    		{
    			sourcenospace+=source.substring(beginindex,i);
    			beginindex=i+1;
    			}    		
    	}
    	sourcenospace+=source.substring(beginindex);
    	return sourcenospace;
    }
    public static String hashPin(String pin)
    {
    	//convert plaintext into bytes 
    	byte plain[] = pin.getBytes();
    	// create MD5 object
    	MD5 md5 = new MD5(plain);
    	//get the resulting hashed byte
    	byte[] result = md5.doFinal();
    	//convert the hashed byte into hexadecimal character for display

    	return md5.toHex(result);    	
    }
    public static String updateConnectionSuffix()
    {
    String connSuffix;	
    if (DeviceInfo.isSimulator()) {
        connSuffix = ";deviceside=true";
    } else
    if ( (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) &&
          RadioInfo.areWAFsSupported(RadioInfo.WAF_WLAN)) {
        connSuffix=";interface=wifi";
    } else {
        String uid = null;
        ServiceBook sb = ServiceBook.getSB();
        ServiceRecord[] records = sb.findRecordsByCid("WPTCP");
        for (int i = 0; i < records.length; i++) {
            if (records[i].isValid() && !records[i].isDisabled()) {
                if (records[i].getUid() != null &&
                    records[i].getUid().length() != 0) {
                    if ((records[i].getCid().toLowerCase().indexOf("wptcp") != -1) &&
                        (records[i].getUid().toLowerCase().indexOf("wifi") == -1) &&
                        (records[i].getUid().toLowerCase().indexOf("mms") == -1)   ) {
                        uid = records[i].getUid();
                        break;
                    }
                }
            }
        }
        if (uid != null) {
            // WAP2 Connection
             connSuffix = ";ConnectionUID="+uid;
        } else {
             connSuffix = ";deviceside=true";
        }
    }
    return connSuffix;
    };    
}
