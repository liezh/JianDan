package com.liezh.service.impl;

import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Tag;
import com.liezh.service.ITagService;
import com.liezh.utils.JsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class TagServiceImplTest {

    @Autowired
    private ITagService tagService;

    @Test
    public void queryTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("便当");
        ServerResponse sp = tagService.queryTag(tag, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void queryTagById() throws Exception {
        ServerResponse sp = tagService.queryTagById(1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("蛋糕");
        ServerResponse sp = tagService.insertTag(tag);
        assert sp.isSuccess();
    }

    @Test
    public void deleteTag() throws Exception {
        ServerResponse sp = tagService.deleteTag(3L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

}