package com.liezh.domain.dto.subject;

/**
 * Created by Administrator on 2018/2/16.
 */
public class SubjectInfoDto {

    private Long id;

    private String title;

    private String synopsis;

    private String cover;

    private Long creatorId;

    private String creatorAccount;

    private String creatorUsername;

    private String creatorAvatar;

    private String creatorSynopsis;

    private Boolean creatorHasFollow;

    private Boolean hasCollect;

    public SubjectInfoDto() {
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

    public String getCreatorAccount() {
        return creatorAccount;
    }

    public void setCreatorAccount(String creatorAccount) {
        this.creatorAccount = creatorAccount;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public String getCreatorSynopsis() {
        return creatorSynopsis;
    }

    public void setCreatorSynopsis(String creatorSynopsis) {
        this.creatorSynopsis = creatorSynopsis;
    }

    public Boolean getCreatorHasFollow() {
        return creatorHasFollow;
    }

    public void setCreatorHasFollow(Boolean creatorHasFollow) {
        this.creatorHasFollow = creatorHasFollow;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }
}
