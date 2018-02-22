package com.liezh.dao;

import com.liezh.domain.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface TagDao {

    List<Tag> queryTag(Tag tag);

    Tag queryTagById(Long tagId);

    Integer insertTag(Tag tag);

    Integer updateTag(Tag tag);

    Integer deleteTag(Long tagId);

    Tag queryTagByName(String name);

}
