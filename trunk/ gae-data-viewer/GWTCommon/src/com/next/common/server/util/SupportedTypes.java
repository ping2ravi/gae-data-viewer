package com.next.common.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.KeyFactory;

public class SupportedTypes {

	private static Map<String, String> types = new HashMap<String, String>();
	
	static{
		types.put(byte.class.getName(), "Y");
		types.put(java.lang.Byte.class.getName(), "Y");
		types.put(short.class.getName(), "Y");
		types.put(java.lang.Short.class.getName(), "Y");
		types.put(int.class.getName(), "Y");
		types.put(java.lang.Integer.class.getName(), "Y");
		types.put(long.class.getName(), "Y");
		types.put(java.lang.Long.class.getName(), "Y");
		types.put(float.class.getName(), "Y");
		types.put(java.lang.Float.class.getName(), "Y");
		types.put(double.class.getName(), "Y");
		types.put(java.lang.Double.class.getName(), "Y");
		types.put(boolean.class.getName(), "Y");
		types.put(java.lang.Boolean.class.getName(), "Y");
		types.put(char.class.getName(), "Y");
		types.put(java.lang.Character.class.getName(), "Y");
		types.put(java.lang.String.class.getName(), "Y");
		types.put(java.util.Date.class.getName(), "Y");
		types.put(com.google.appengine.api.datastore.Key.class.getName(), "Y");
		
		
	}
	public static boolean isSupported(Object type)
	{
		boolean returnValue;
		if(types.get(type) == null)
			returnValue = false;
		else
			returnValue = true;
		return returnValue;
	}
	public static Object getValue(String objectCLass,String data,String type) throws ParseException
	{
		if(data == null || "".equals(data.trim()))
			return null;
		if(byte.class.getName().equals(type) || Byte.class.getName().equals(type))
			return Byte.valueOf(data);
		if(short.class.getName().equals(type) || Short.class.getName().equals(type))
			return Short.valueOf(data);
		if(int.class.getName().equals(type) || Integer.class.getName().equals(type))
			return Integer.valueOf(data);
		if(long.class.getName().equals(type) || Long.class.getName().equals(type))
			return Long.valueOf(data);
		if(float.class.getName().equals(type) || Float.class.getName().equals(type))
			return Float.valueOf(data);
		if(double.class.getName().equals(type) || Double.class.getName().equals(type))
			return Double.valueOf(data);
		if(boolean.class.getName().equals(type) || Boolean.class.getName().equals(type))
			return Boolean.valueOf(data);
		if(char.class.getName().equals(type) || Character.class.getName().equals(type))
			return Character.valueOf(data.toCharArray()[0]);
		if(java.util.Date.class.getName().equals(type))
		{
			SimpleDateFormat sd = new SimpleDateFormat("MMM dd, yyyy");
			return sd.parseObject(data);
		}
		if(java.lang.String.class.getName().equals(type))
			return data;
		if(com.google.appengine.api.datastore.Key.class.getName().equals(type))
			return KeyFactory.createKey(objectCLass, data);
		return null;
	}
	public static void main(String[] args)
	{
		System.out.println(byte.class.getName());
	}
	public static Class getClass(String type) throws ClassNotFoundException
	{
		if(type == null || "".equals(type.trim()))
			return null;
		if(byte.class.getName().equals(type))
			return byte.class;
		if(short.class.getName().equals(type))
			return short.class;
		if(int.class.getName().equals(type))
			return int.class;
		if(long.class.getName().equals(type))
			return long.class;
		if(float.class.getName().equals(type))
			return float.class;
		if(double.class.getName().equals(type))
			return double.class;
		if(boolean.class.getName().equals(type))
			return boolean.class;
		if(char.class.getName().equals(type))
			return char.class;
		return Class.forName(type);
	}
}
