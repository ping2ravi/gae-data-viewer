package com.next.common.server.util;

import java.util.HashMap;
import java.util.Map;

public class SupportedTypes {

	private static Map<String, String> types = new HashMap<String, String>();
	static{
		types.put("byte", "Y");
		types.put("java.lang.Byte", "Y");
		types.put("short", "Y");
		types.put("java.lang.Short", "Y");
		types.put("int", "Y");
		types.put("java.lang.Integer", "Y");
		types.put("long", "Y");
		types.put("java.lang.Long", "Y");
		types.put("float", "Y");
		types.put("java.lang.Float", "Y");
		types.put("double", "Y");
		types.put("java.lang.Double", "Y");
		types.put("boolean", "Y");
		types.put("java.lang.Boolean", "Y");
		types.put("char", "Y");
		types.put("java.lang.Character", "Y");
		types.put("java.lang.String", "Y");
		types.put("java.util.Date", "Y");
		types.put("java.util.Date", "Y");
		types.put("com.google.appengine.api.datastore.Key", "Y");
		
		
	}
	public static boolean isSupported(String type)
	{
		boolean returnValue;
		if(types.get(type) == null)
			returnValue = false;
		else
			returnValue = true;
		return returnValue;
	}
}
