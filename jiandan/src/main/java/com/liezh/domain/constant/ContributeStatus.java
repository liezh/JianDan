package com.liezh.domain.constant;

/**
 * Created by Administrator on 2018/2/21.
 */
public enum ContributeStatus {

    // 投稿待审核
    TO_CHECK(0, "待审核"),
    // 投稿通过
    PASS(1, "通过"),
    // 投稿拒绝
    REJECT(2, "拒绝"),

    ;

    private Integer code;

    private String desc;

    ContributeStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    ContributeStatus() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
