package com.liezh.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.entity.Subject;
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
 * Created by Administrator on 2018/2/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class SubjectDaoTest {

    @Autowired
    private SubjectDao subjectDao;

    @Test
    public void querySubject() throws Exception {
        PageHelper.startPage(1, 20);
        Subject subject = new Subject();
        subject.setTitle("蛋炒饭");
        List<SubjectInfoDto> subjects = subjectDao.querySubject(subject);
        PageInfo<SubjectInfoDto> pageInfo = new PageInfo<>(subjects);
        assert pageInfo != null && pageInfo.getTotal() > 0;
    }

    @Test
    public void querySubjectById() throws Exception {
        SubjectInfoDto subject = subjectDao.querySubjectById(1L);
        assert subject != null;

    }

    @Test
    public void insertSubject() throws Exception {
        Subject subject = new Subject();
        subject.setTitle("美味甜点");
        subject.setCover("http://p2.so.qhimgs1.com/bdr/_240_/t018fcd37e7f2402d62.jpg");
        subject.setSynopsis("蛋糕，甜品");
        int count = subjectDao.insertSubject(subject);
        assert count > 0 && subject.getId() != null;
    }

    @Test
    public void updateSubject() throws Exception {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setTitle("马卡龙");
        subject.setSynopsis("点心神品");
        int count = subjectDao.updateSubject(subject);
        assert count > 0 ;
    }

    @Test
    public void deleteSubject() throws Exception {
        int count = subjectDao.deleteSubject(2L);
        assert count > 0 ;
    }

}