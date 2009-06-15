package com.app.music.server.persistance.helper;

public class HelperFactory {

	private static FunctionPersistance functionPersistance;
	public static FunctionPersistance getFunctionPersistance()
	{
		if(functionPersistance == null)
			functionPersistance = new FunctionPersistance();
		return functionPersistance;
	}
}
