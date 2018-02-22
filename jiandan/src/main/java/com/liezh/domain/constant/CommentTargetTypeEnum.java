package com.liezh.domain.constant;

/**
 * Created by Administrator on 2018/2/17.
 */
public enum CommentTargetTypeEnum {
    TARGET_RECIPE("recipe", "菜谱评论"),

    TARGET_FOODNOTE("foodnote", "食记评论"),

    ;


    private String code;

    private String desc;

    CommentTargetTypeEnum() {
    }

    CommentTargetTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
