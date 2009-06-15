package com.app.music.server.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PersistanceFactory {
	private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
    public static PersistenceManager getPersisatnceManager() {
        return pmfInstance.getPersistenceManager();
    }

}
