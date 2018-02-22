package com.liezh.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.domain.constant.CommentTargetTypeEnum;
import com.liezh.domain.dto.comment.CommentInfoDto;
import com.liezh.domain.entity.Comment;
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
 * Created by Administrator on 2018/2/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void queryComment() throws Exception {
        PageHelper.startPage(1, 20);
        Comment comment = new Comment();
        List<CommentInfoDto> comments = commentDao.queryComment(comment);
        PageInfo<CommentInfoDto> pageInfo = new PageInfo<>(comments);
        assert pageInfo != null && pageInfo.getTotal() > 0;
    }

    @Test
    public void queryCommentById() throws Exception {
        CommentInfoDto comment = commentDao.queryCommentById(1L);
        assert comment != null;
    }

    @Test
    public void insertComment() throws Exception {
        Comment comment = new Comment();
        comment.setTargetType(CommentTargetTypeEnum.TARGET_RECIPE.getCode());
        comment.setTargetId(1L);
        comment.setPublisherId(1L);
        comment.setContent("666666666");
        comment.setGoodCount(2);
        int count = commentDao.insertComment(comment);
        assert count > 0 && comment.getId() != null;
    }

    @Test
    public void updateComment() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setTargetType(CommentTargetTypeEnum.TARGET_RECIPE.getCode());
        comment.setGoodCount(10);
        int count = commentDao.updateComment(comment);
        assert count > 0;
    }

    @Test
    public void deleteComment() throws Exception {
        int count = commentDao.deleteComment(3L);
        assert count > 0;
    }

}