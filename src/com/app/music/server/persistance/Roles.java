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
public class Roles extends BaseObject{
    @Persistent
    private String name;
    @Persistent
    private String description;
    @Persistent
    private Date dateCreated;
    @Persistent
    private Date dateModified;
    @Persistent
    private BigInteger creatorId;
    @Persistent
    private BigInteger modifierId;
    @Persistent
    private Set<Key> functions;

    public Set<Key> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<Key> functions) {
		this.functions = functions;
	}

	public Roles() {
    }

    public Roles(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.music.server.persistance.Roles[id=" + id + "]";
    }

}
