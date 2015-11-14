package com.bng.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.bng.core.controller.FileController;

public class XmlPropertiesReader {
	private String fileName;
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public HashMap<String, String> propertiesXml() {
		
		Properties prop = new Properties();
		HashMap<String, String> propMap = null;
		
		 
    	try {
    		prop.load(FileController.class.getClassLoader().getResourceAsStream(fileName));
    		propMap = new HashMap<String, String>((Map) prop);
    	} 
    	catch (IOException ex) {
    		Logger.sysLog(LogValues.error, this.getClass().getName(), " exception "+coreException.GetStack(ex));
        }
    	return propMap;	
	}
}
