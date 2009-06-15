package com.app.music.server.persistance;

import java.math.BigInteger;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class BaseObject {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Long id;
    @Persistent
    private Date dateCreated;
    @Persistent
    private Date dateModified;
    @Persistent
    private BigInteger creatorId;
    @Persistent
    private BigInteger modifierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
