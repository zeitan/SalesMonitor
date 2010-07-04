package com.ai;

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
}
