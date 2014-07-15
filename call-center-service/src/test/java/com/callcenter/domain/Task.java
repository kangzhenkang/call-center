package com.callcenter.domain;

import java.util.Date;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class Task {
    private int id;
    private String name;
    private Date date ;
    private String express;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public Task() {
        super();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
