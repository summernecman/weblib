package com.summer.mybatis.entity;

import java.util.Date;

public class Record {
    private Integer id;

    private String locpath;

    private String netpath;

    private Date ctime;

    private Date utime;

    private String atype;

    private String btype;

    private String address;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocpath() {
        return locpath;
    }

    public void setLocpath(String locpath) {
        this.locpath = locpath == null ? null : locpath.trim();
    }

    public String getNetpath() {
        return netpath;
    }

    public void setNetpath(String netpath) {
        this.netpath = netpath == null ? null : netpath.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype == null ? null : atype.trim();
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype == null ? null : btype.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}