package com.liezh.domain.dto.subject;

/**
 * Created by Administrator on 2018/2/20.
 */
public class SubjectQueryDto {

    private String title;

    private Long creatorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
