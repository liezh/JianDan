package com.liezh.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.domain.entity.Tag;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Test
    public void queryTag() throws Exception {
        PageHelper.startPage(1, 20);
        Tag tag = new Tag();
        tag.setName("便当");
        List<Tag> tags = tagDao.queryTag(tag);
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        assert pageInfo != null && pageInfo.getTotal() > 0;
    }

    @Test
    public void queryTagById() throws Exception {
        Tag tag = tagDao.queryTagById(1L);
        assert tag != null;
    }

    @Test
    public void insertTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("便当");
        tag.setQueryCount(0);
        int count = tagDao.insertTag(tag);
        assert count > 0;
    }

    @Test
    public void updateTag() throws Exception {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setQueryCount(10);
        int count = tagDao.updateTag(tag);
        assert count > 0;
    }

    @Test
    public void deleteTag() throws Exception {
        int count = tagDao.deleteTag(2L);
        assert count > 0;
    }

}