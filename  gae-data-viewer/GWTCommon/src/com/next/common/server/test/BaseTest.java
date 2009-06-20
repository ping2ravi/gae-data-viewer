package com.next.common.server.test;

import javax.jdo.PersistenceManager;

import junit.framework.TestCase;

import com.google.apphosting.api.ApiProxy;
import com.next.common.server.entity.helper.DBManager;
import com.next.common.server.entity.helper.JDOManager;
import com.next.common.server.util.PMF;
//import com.google.appengine.tools.development.ApiProxyLocalImpl;

public class BaseTest extends TestCase{
	public DBManager getDBManager()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.currentTransaction().begin();
		DBManager dBManager = new JDOManager(pm);
		return dBManager;
	}
    @Override
    public void setUp() throws Exception {
        super.setUp();
        ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
        //ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(".")){});
    }

    @Override
    public void tearDown() throws Exception {
        // not strictly necessary to null these out but there's no harm either
        ApiProxy.setDelegate(null);
        ApiProxy.setEnvironmentForCurrentThread(null);
        super.tearDown();
    }

}
