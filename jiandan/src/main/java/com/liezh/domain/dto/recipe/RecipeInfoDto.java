package com.liezh.domain.dto.recipe;

import com.liezh.domain.dto.user.UserInfoDto;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/16.
 */
public class RecipeInfoDto {

    private Long id;

    private String title;

    private String cover;

    private String content;

    private String synopsis;

    private String process;

    private String materials;

    private Integer status;

    private Integer goodCount;

    private Integer readCount;

    private Date releaseTime;

    // 作者信息
    private Long authorId;

    private String authorAccount;

    private String authorUsername;

    private String authorAvatar;

    private String authorSynopsis;

    private Boolean authorHasFollow;


    public RecipeInfoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getAuthorSynopsis() {
        return authorSynopsis;
    }

    public void setAuthorSynopsis(String authorSynopsis) {
        this.authorSynopsis = authorSynopsis;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Boolean getAuthorHasFollow() {
        return authorHasFollow;
    }

    public void setAuthorHasFollow(Boolean authorHasFollow) {
        this.authorHasFollow = authorHasFollow;
    }
}
