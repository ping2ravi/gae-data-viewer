package com.next.viewer.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.next.viewer.server.exceptions.ApplicationException;


public class PropertyFileUtil {

	
	private boolean fileLoaded;
	public static void loadPropertyFile(Properties properties,String fileName) throws ApplicationException
	{
	    // Read properties file.
	    try {
	        properties.load(new FileInputStream(fileName));
	    } catch (IOException e) {
	    	throw new ApplicationException("Could not read the property file");
	    }
	    
		
	}
}
