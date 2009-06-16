package com.next.common.server.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import com.next.common.server.entity.Customer;
import com.next.common.server.exceptions.NoSuchENtity;

public class ReflectionUtil {

	public static String[] getClassFields(String className) throws NoSuchENtity
	{
		Class cl = null;
		try {
			cl = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity Exists " + className);
		}
		Method[] allMethods = cl.getMethods();
		List<String> allFields = new ArrayList<String>();
		for(int i=0;i<allMethods.length;i++)
		{
			if(!allMethods[i].getName().startsWith("get"))
				continue;
			if(allMethods[i].getName().equals("getClass"))
				continue;
			allFields.add(allMethods[i].getName());
		}
		return (String[])allFields.toArray(new String[0]);
	}
	public static void main(String[] args) throws NoSuchENtity
	{
		Class cl = null;
		try {
			cl = Class.forName(Customer.class.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchENtity("No Such Entity Exists " + Customer.class.getName());
		}
		Annotation[] allAnot = cl.getAnnotations();
		System.out.println("Total Annotaions are " + allAnot.length);
		for(int i=0;i<allAnot.length;i++)
		{
			System.out.println(allAnot[i].toString());
		}
		
		allAnot = cl.getDeclaredAnnotations();
		System.out.println("Total Annotaions are " + allAnot.length);
		for(int i=0;i<allAnot.length;i++)
		{
			System.out.println(allAnot[i].toString());
		}
		
		TypeVariable[] tv= cl.getTypeParameters();
		System.out.println("Total tv are " + tv.length);
		for(int i=0;i<tv.length;i++)
		{
			System.out.println(tv[i].getName());
		}

	}
}
