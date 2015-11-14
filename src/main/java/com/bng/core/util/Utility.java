/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bng.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;


import com.google.gson.Gson;

/**
 *
 * @author richa
 */
public class Utility 
{   

private static String countryCodes;

public static int obdMsisdnLength;
	
	
	public  String getCountryCodes() {
		return countryCodes;
	}


	public  void setCountryCodes(String countryCodes) {
		Utility.countryCodes = countryCodes;
	}
	
	private static Gson gson = new Gson();

	public static int getModValue(String msisdn, int modFactor)
    {
		Logger.sysLog(LogValues.info, Utility.class.getName(),"msisdn = "+msisdn+" , modFactor = "+modFactor);
        int mod = Integer.parseInt(msisdn.substring(msisdn.length()-4))%(modFactor);
        return mod;
    }
    
    public static boolean getPercentage(int percentage)
    {
        boolean flag = false;
        try
        {
            Random randomGenerator = new Random();
            int randomNumber = randomGenerator.nextInt(100);
            if(randomNumber <= percentage)
                flag = true;
            Logger.sysLog(LogValues.info, Utility.class.getName(),"Recording = "+flag);
        }
        catch(Exception e)
        {
            Logger.sysLog(LogValues.error, Utility.class.getName(), coreException.GetStack(e));
        }
        return flag;
    }   
    
    public static String callUrl(String httpurl)
    {
        String httpUrlResponse = "";
        try
        {
            //Logger.sysLog(LogValues.info, this.getClass().getName(),"HTTP url = "+httpurl);
            URL url = new URL(httpurl);
            URLConnection uc = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) uc;           
            InputStream rd = con.getInputStream();
            int c = 0;
            while ((c = rd.read()) != -1)
            {
                    httpUrlResponse+= (char) c;
            }
        }
        catch(Exception e)
        {
            Logger.sysLog(LogValues.error, Utility.class.getName(), coreException.GetStack(e));
        }
        return httpUrlResponse.trim();
    }
    
    
    public synchronized static String convertObjectToJsonStr(Object object)
    {    	
    	return gson.toJson(object);
    }
    
    public synchronized static <T> T convertJsonStrToObject(String json, Class<T> classOfT)
    {    	 
    	return gson.fromJson(json,classOfT);
    }
    
    public static byte[] object2byte(Object obj) throws Exception{
            ByteArrayOutputStream byteObject = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteObject);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteObject.close();
            return byteObject.toByteArray();
    }

    public static Object byte2object(byte[] obj) throws Exception{
            ByteArrayInputStream bais = new ByteArrayInputStream(obj); 
            ObjectInputStream object = new ObjectInputStream(bais);
            bais.close();
            object.close();
            return object.readObject();
    }
    
    public static String getCurrentDate(String dateFormat)
    {    	
	    SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;    	
    }
    
    public static Date addDays(Date d, int days)
    {
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }

    
    public static String findLastModified(String directoryPath)
	{
		File LastModifiedFile = null;
		File dir = new File(directoryPath);
		String[] ext = null; 
		File [] file = FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(dir, ext, false));
		try
		{
			long max = Long.MIN_VALUE;
			for (File f : file) 
			{	
				if (f.lastModified() > max) 
				{
					LastModifiedFile = f;
					max = f.lastModified();
				}
			}
			return directoryPath+"/"+LastModifiedFile.getName();
		}
		catch(Exception ex){
			Logger.sysLog(LogValues.error, Utility.class.getName(), coreException.GetStack(ex));	
			return "";
		}
	} 
    public static String numbercorrected(String aPartyMsisdn)
	 { 
		Logger.sysLog(LogValues.info, Utility.class.getName(),"country codes: "+countryCodes);
		String msisdntruncated = aPartyMsisdn;
	  if(countryCodes != null)
	  {
	   String[] countryCode =  countryCodes.split(",");
	   for (int i = 0; i < countryCode.length; i++)
	   {
	    if(aPartyMsisdn.startsWith(countryCode[i]))
	    {
	     msisdntruncated = aPartyMsisdn.substring(countryCode[i].length(),aPartyMsisdn.length());
	     break;
	    }
	    else
	     msisdntruncated = aPartyMsisdn;
	   } 
	  }
	  return msisdntruncated;
	 }


	public int getObdMsisdnLength() {
		return obdMsisdnLength;
	}


	public void setObdMsisdnLength(int obdMsisdnLength) {
		this.obdMsisdnLength = obdMsisdnLength;
	}
}
