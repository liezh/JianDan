package com.liezh.domain.entity;

/**
 * Created by Administrator on 2017/9/30.
 */
public class Role extends BaseEntity {

    private String name;

    private Integer status;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
