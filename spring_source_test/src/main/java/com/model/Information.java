package com.model;

import java.io.Serializable;

/**
 * Created by apple on 29/04/2015.
 */
public class Information implements Serializable {
    private String iid;
    private String description;
    private String cid;

    private final static long serialVersionUID = 1L;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
