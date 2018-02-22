package com.liezh.domain.dto.role;

/**
 * Created by Administrator on 2018/2/15.
 */
public class RoleQueryDto {

    private Long id;

    private String name;

    private Integer status;
    

    public RoleQueryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
