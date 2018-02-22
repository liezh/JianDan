package com.liezh.domain.entity;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/15.
 */
public class Subject extends BaseEntity {

    private String title;

    private String synopsis;

    private String cover;

    private Long creatorId;

    public Subject() {
    }

    public Subject(String title, String synopsis, String cover) {
        this.title = title;
        this.synopsis = synopsis;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
