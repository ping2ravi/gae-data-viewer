package com.app.music.server.persistance.helper;

import com.app.music.server.exception.ApplicationException;
import com.app.music.server.paging.PageInfo;
import com.app.music.server.paging.PageResult;
import com.app.music.server.persistance.User;
import com.app.music.server.session.UserServerSession;

public class RolePersistance extends BasePersistance {
    public void createRole(UserServerSession userServerSession,User newRole) throws ApplicationException {
    	super.createBaseObject(userServerSession, newRole);
    }

    public void updateRole(UserServerSession userServerSession,User function) throws ApplicationException {
    	super.updateBaseObject(userServerSession, function);
    }

    public void deleteRole(UserServerSession userServerSession,Long id) throws ApplicationException {
    	super.deleteBaseObject(userServerSession,User.class, id);
    	
    }

    public PageResult findRolesEntities(UserServerSession userServerSession, PageInfo pageInfo) {
        return super.findBaseObjectEntities(userServerSession, User.class, pageInfo);
    }

    public PageResult getUserRoles(UserServerSession userServerSession, Long userId) {
    	PageInfo pageInfo = new PageInfo();
        return super.findBaseObjectEntities(userServerSession, User.class, pageInfo);
    }
    
    public User findRoleById(UserServerSession userServerSession,Long id) {
        return (User)super.findBaseObjectById(userServerSession, User.class, id);
    }

    public Long getRolesCount(UserServerSession userServerSession) {
        return super.getBaseObjectCount(userServerSession, User.class);
    }

}
