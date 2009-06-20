package com.next.common.server.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.next.common.server.entity.Greeting;
import com.next.common.server.entity.helper.DBManager;
import com.next.common.server.entity.helper.GenericEntityHelper;

public class SearchEntityTest extends BaseTest {

	public void testSearchEntityApi() throws Exception
	{
		GenericEntityHelper geh = GenericEntityHelper.getInstance();
		DBManager dbManager = getDBManager();
		Map<String, String> searchParams = new HashMap<String, String>();
		List data = geh.findGenericEntity(dbManager, Greeting.class.getName(), null);
		
	}
}
