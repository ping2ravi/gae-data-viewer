package com.app.music.server.persistance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

/**
 *
 * @author Ravi
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User extends BaseObject{
    @Persistent
    private String firstName;
    @Persistent
    private String lastName;
    @Persistent
    private String gmailId;
    @Persistent
    private Date dateCreated;
    @Persistent
    private BigInteger creatorId;
    @Persistent
    private BigInteger modifierId;
    @Persistent
    private Date dateModified;
    @Persistent
    private Set<Key> roles;
    
    public Set<Key> getRoles() {
		return roles;
	}

	public void setRoles(Set<Key> roles) {
		this.roles = roles;
	}

	public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGmailId() {
        return gmailId;
    }

    public void setGmailId(String gmailId) {
        this.gmailId = gmailId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigInteger getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(BigInteger creatorId) {
        this.creatorId = creatorId;
    }

    public BigInteger getModifierId() {
        return modifierId;
    }

    public void setModifierId(BigInteger modifierId) {
        this.modifierId = modifierId;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.music.server.persistance.User[id=" + id + "]";
    }

}
