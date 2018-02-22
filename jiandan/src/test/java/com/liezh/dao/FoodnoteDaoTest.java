package com.liezh.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.foodnote.FoodnoteInfoDto;
import com.liezh.domain.entity.Foodnote;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class FoodnoteDaoTest {

    @Autowired
    private FoodnoteDao foodnoteDao;

    @Test
    public void queryFoodnote() throws Exception {
        PageHelper.startPage(1, 20);
        Foodnote foodnote = new Foodnote();
        List<FoodnoteInfoDto> foodnotes = foodnoteDao.queryFoodnote(foodnote);
        PageInfo<FoodnoteInfoDto> pageInfo = new PageInfo<>(foodnotes);
        assert pageInfo != null && pageInfo.getTotal() > 0;
    }

    @Test
    public void queryFoodnoteById() throws Exception {
        FoodnoteInfoDto foodnote = foodnoteDao.queryFoodnoteById(1L);
        assert foodnote != null;
    }

    @Test
    public void insertFoodnote() throws Exception {
        Foodnote foodnote = new Foodnote();
        foodnote.setTitle("馄饨");
        foodnote.setAuthorId(1L);
        foodnote.setContent("今天在北京路吃的馄饨可好吃了～～～～");
        foodnote.setGoodCount(1);
        foodnote.setReadCount(3);
        foodnote.setReleaseTime(new Date());
        int count = foodnoteDao.insertFoodnote(foodnote);
        assert count > 0;
    }

    @Test
    public void updateFoodnote() throws Exception {
        Foodnote foodnote = new Foodnote();
        foodnote.setId(1L);
        foodnote.setTitle("馄饨" + System.currentTimeMillis());
        int count = foodnoteDao.updateFoodnote(foodnote);
        assert count > 0;
    }

    @Test
    public void deleteFoodnote() throws Exception {
        int count = foodnoteDao.deleteFoodnote(2L);
        assert count > 0;
    }

}