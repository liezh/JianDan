package com.liezh.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/30.
 *  基础实体
 */
public class BaseEntity implements Serializable {

    protected Long id;

    protected Date createTime;

    protected Date updateTime;

    protected Integer isLock;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public BaseEntity(Long id, Date createTime, Date updateTime, Integer isLock) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isLock = isLock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }
}
