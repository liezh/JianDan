package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Tag;

/**
 * Created by Administrator on 2018/2/20.
 */
public interface ITagService {

    ServerResponse<PageInfo> queryTag(Tag tag, Integer pageNum, Integer pageSize);

    ServerResponse<Tag> queryTagById(Long tagId);

    ServerResponse<Long> insertTag(Tag tag);

    ServerResponse<Integer> deleteTag(Long tagId);



}
