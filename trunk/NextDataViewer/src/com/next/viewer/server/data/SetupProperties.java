package com.next.viewer.server.data;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.next.viewer.server.exceptions.ApplicationException;
import com.next.viewer.server.util.PropertyFileUtil;


public class SetupProperties {
	private boolean loaded;
	private static SetupProperties instance;
	private Properties props;
	public static SetupProperties getInstance()
	{
		if(instance == null)
		{
			instance = new SetupProperties();
		}
		return instance;
	}
	private SetupProperties()
	{
		
	}
	public void loadProperties(String fileNamePath)
	{
		loaded = true;
		props = new Properties();
		try {
			PropertyFileUtil.loadPropertyFile(props, fileNamePath);
		} catch (ApplicationException e) {
			Logger logger = Logger.getLogger(SetupProperties.class.getName());
			logger.log(Level.SEVERE, "Unable to load property file " + fileNamePath);
		}
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public String getDbLayerType()
	{
		if(props == null || props.getProperty("db.layer.type") == null)
			return "JDO";
		String returnString = props.getProperty("db.layer.type");
		if("JDO".equalsIgnoreCase(returnString) || "JPA".equals(returnString))
			return returnString;
		return "JDO";
	}

}
