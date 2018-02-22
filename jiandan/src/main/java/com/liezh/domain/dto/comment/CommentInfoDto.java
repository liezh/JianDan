package com.liezh.domain.dto.comment;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/17.
 */
public class CommentInfoDto {

    private Long id;

    private String content;

    private String targetType;

    private Long targetId;

    private Integer goodCount;

    private Long publisherId;

    private String publisherAccount;

    private String publisherUsername;

    private String publisherAvatar;

    private String publisherSynopsis;


    public CommentInfoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public String getPublisherAccount() {
        return publisherAccount;
    }

    public void setPublisherAccount(String publisherAccount) {
        this.publisherAccount = publisherAccount;
    }

    public String getPublisherUsername() {
        return publisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
    }

    public String getPublisherAvatar() {
        return publisherAvatar;
    }

    public void setPublisherAvatar(String publisherAvatar) {
        this.publisherAvatar = publisherAvatar;
    }

    public String getPublisherSynopsis() {
        return publisherSynopsis;
    }

    public void setPublisherSynopsis(String publisherSynopsis) {
        this.publisherSynopsis = publisherSynopsis;
    }
}
