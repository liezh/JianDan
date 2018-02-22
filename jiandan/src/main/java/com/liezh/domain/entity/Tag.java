package com.liezh.domain.entity;

/**
 * Created by Administrator on 2018/2/15.
 */
public class Tag extends BaseEntity {

    private String name;

    private Integer queryCount;

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(Integer queryCount) {
        this.queryCount = queryCount;
    }
}
