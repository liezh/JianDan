package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.dao.TagDao;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Tag;
import com.liezh.service.ITagService;
import com.liezh.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/2/20.
 */
@Service
public class TagServiceImpl implements ITagService {

    private static Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Autowired
    private TagDao tagDao;

    public ServerResponse<PageInfo> queryTag(Tag tag, Integer pageNum, Integer pageSize){
        if (tag == null) {
            tag = new Tag();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagDao.queryTag(tag);
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<Tag> queryTagById(Long tagId) {
        if (tagId == null) {
            logger.error("标签id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Tag tag = tagDao.queryTagById(tagId);
        if (tag != null && tag.getId() != null) {
            return ServerResponse.createBySuccess(tag);
        }
        return ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
    }

    @Transactional
    public ServerResponse<Long> insertTag(Tag tag) {
        if (tag == null || StringUtils.isBlank(tag.getName())) {
            logger.error("标签名为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Tag tagOrig = tagDao.queryTagByName(tag.getName());
        if (tagOrig != null && tagOrig.getId() != null) {
            logger.warn("标签已存在！ tag: {}", JsonUtil.toJson(tagOrig));
            // 查询数 +1
            tagOrig.setQueryCount(tagOrig.getQueryCount() + 1);
            int count = tagDao.updateTag(tagOrig);
            if (count > 0) {
                logger.error("标签查询权值自增成功！ tag: {}", JsonUtil.toJson(tagOrig));
            } else {
                logger.info("标签查询权值自增失败！ tag: {}", JsonUtil.toJson(tagOrig));
            }
            return ServerResponse.createBySuccess(tagOrig.getId());
        }

        int resultCount = tagDao.insertTag(tag);
        if (resultCount > 0) {
            logger.info("标签添加成功！ tag: {}", JsonUtil.toJson(tag));
            return ServerResponse.createBySuccess(tag.getId());
        }
        logger.error("标签添加失败！ tag: {}", JsonUtil.toJson(tag));
        return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
    }

    public ServerResponse<Integer> deleteTag(Long tagId) {
        if (tagId == null) {
            logger.error("标签id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = tagDao.deleteTag(tagId);
        if (resultCount > 0) {
            logger.info("标签删除成功！ tagId: {}", tagId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("标签删除失败！ tagId: {}", tagId);
        return ServerResponse.createByResponseEnum(ResponseEnum.DELETE_FAILURE);
    }

}
