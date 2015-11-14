/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bng.core.util;

import java.util.Date;

import com.bng.core.xmlparser.marshal.ConverterCoreEngine;


/**
 *
 * @author richa
 */
public class Logger 
{	
    public static void sysLog(int severity, String applicationName, String message)
    {
        try
        {
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(applicationName);
            switch (severity) 
            {       	    
                case LogValues.fatal:
                    log.fatal(message); 
                    break;
                case LogValues.error:
                    log.error(message); 
                    break;
                case LogValues.warn: 
                    log.warn(message); 
                    break;
                case LogValues.info:
                    log.info(message); 
                    break;
                case LogValues.debug:
                    log.debug(message); 
                    break; 
                default:log.trace(message);
            }
        }
        catch( Throwable e )
        {
            System.err.println(" Error doing log severity: "+severity+" Application: "+applicationName+" Message: "+message );
            Logger.sysLog(LogValues.error, ConverterCoreEngine.class.getName(), coreException.GetStack(e));
        }
    }

	public static void sysLog(int info, String name, Date startDate) 
	{
		sysLog(info,name,startDate.toString());		
	}

	public static void sysLog(int info, String name, int size) 
	{
		sysLog(info,name,Integer.toString(size));
		
	}


	public static void sysLog(int info, String name, Throwable cause) 
	{
		sysLog(info,name,cause.toString());
	}

}
