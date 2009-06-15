package com.app.music.server.session;

import javax.jdo.PersistenceManager;

import com.app.music.server.persistance.User;

public class UserServerSession {

    private User user;
    private PersistenceManager  entityManager;
    
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public PersistenceManager getPersistenceManager() {
		return entityManager;
	}
	public void setPersistenceManager(PersistenceManager  entityManager) {
		this.entityManager = entityManager;
	}
	
}
