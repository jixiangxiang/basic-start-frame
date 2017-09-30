package com.eric.self.selfapplication.bean;

/**
 * Created by eric on 2017/9/29.
 */

public class Repository {
    private String full_name;
    private Boolean isPrivate;
    private String html_url;
    private int forks_count;
    private String description;
    private RespOwner owner;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RespOwner getOwner() {
        return owner;
    }

    public void setOwner(RespOwner owner) {
        this.owner = owner;
    }
}
