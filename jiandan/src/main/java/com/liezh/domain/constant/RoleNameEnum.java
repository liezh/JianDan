package com.liezh.domain.constant;

/**
 * Created by Administrator on 2018/2/19.
 */
public enum RoleNameEnum {

    USER("USER", "普通用户"),
    ADMIN("ADMIN", "管理员"),
    ;

    private String name;

    private String desc;

    RoleNameEnum() {
    }

    RoleNameEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
