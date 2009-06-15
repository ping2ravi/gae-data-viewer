package com.app.music.server.persistance.helper;

import com.app.music.server.exception.ApplicationException;
import com.app.music.server.paging.PageInfo;
import com.app.music.server.paging.PageResult;
import com.app.music.server.persistance.User;
import com.app.music.server.session.UserServerSession;

public class UserPersistance extends BasePersistance {

    public void createUser(UserServerSession userServerSession,User newUser) throws ApplicationException {
    	super.createBaseObject(userServerSession, newUser);
    }

    public void updateUser(UserServerSession userServerSession,User function) throws ApplicationException {
    	super.updateBaseObject(userServerSession, function);
    }

    public void deleteUser(UserServerSession userServerSession,Long id) throws ApplicationException {
    	super.deleteBaseObject(userServerSession,User.class, id);
    	
    }

    public PageResult findUsersEntities(UserServerSession userServerSession, PageInfo pageInfo) {
        return super.findBaseObjectEntities(userServerSession, User.class, pageInfo);
    }

    public User findUserById(UserServerSession userServerSession,Long id) {
        return (User)super.findBaseObjectById(userServerSession, User.class, id);
    }

    public Long getUsersCount(UserServerSession userServerSession) {
        return super.getBaseObjectCount(userServerSession, User.class);
    }
}
