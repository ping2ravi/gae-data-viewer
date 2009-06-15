package com.app.music.server.persistance.helper;


import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.app.music.server.exception.ApplicationException;
import com.app.music.server.paging.PageInfo;
import com.app.music.server.paging.PageResult;
import com.app.music.server.persistance.Function;
import com.app.music.server.session.UserServerSession;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ravi
 */
public class FunctionPersistance extends BasePersistance{

    public void createFunction(UserServerSession userServerSession,Function newFunction) throws ApplicationException {
    	super.createBaseObject(userServerSession, newFunction);
    }

    public void updateFunction(UserServerSession userServerSession,Function function) throws ApplicationException {
    	super.updateBaseObject(userServerSession, function);
    }

    public void deleteFunction(UserServerSession userServerSession,Long id) throws ApplicationException {
    	super.deleteBaseObject(userServerSession,Function.class, id);
    	
    }

    public PageResult findFunctionsEntities(UserServerSession userServerSession, PageInfo pageInfo) {
        return super.findBaseObjectEntities(userServerSession, Function.class, pageInfo);
    }

    public Function findFunctionById(UserServerSession userServerSession,Long id) {
        return (Function)super.findBaseObjectById(userServerSession, Function.class, id);
    }

    public Long getFunctionsCount(UserServerSession userServerSession) {
        return super.getBaseObjectCount(userServerSession, Function.class);
    }

}
