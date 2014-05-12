package com.ipdev.common.entity;

import java.util.Date;

public abstract class AuditableEntity {

    Date creationDate;
    Date lastUpdatedDate;

    public AuditableEntity() {
        this.setCreationDate(new Date());
        this.setLastUpdatedDate(new Date());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

}
