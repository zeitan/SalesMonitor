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
}
