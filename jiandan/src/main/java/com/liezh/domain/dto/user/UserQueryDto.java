package com.liezh.domain.dto.user;

/**
 * Created by Administrator on 2018/2/15.
 */
public class UserQueryDto {

    private String account;

    private String username;

    private String mobile;

    private String email;

    public UserQueryDto() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
