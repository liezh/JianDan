package com.liezh.service.impl;

import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.service.ISubjectService;
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
 * Created by Administrator on 2018/2/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class SubjectServiceImplTest {

    @Autowired
    private ISubjectService subjectService;

    @Test
    public void querySubject() throws Exception {
        ServerResponse sp = subjectService.querySubject(3L, null, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void querySubjectById() throws Exception {
        ServerResponse sp = subjectService.querySubjectById(1L, 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertSubject() throws Exception {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.setTitle("干炒牛河");
        subjectInfoDto.setSynopsis("粤式小炒的经典之作");
        subjectInfoDto.setCreatorId(1L);
        subjectInfoDto.setCover("https://tva4.sinaimg.cn/crop.0.0.150.150.50/006dCke7jw8exc29553vsj3046046mxn.jpg");
        ServerResponse sp = subjectService.insertSubject(subjectInfoDto);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void updateSubject() throws Exception {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.setId(3L);
        subjectInfoDto.setTitle("法式鹅肝");
        subjectInfoDto.setSynopsis("法式料理的经典之作");
        subjectInfoDto.setCreatorId(1L);
        ServerResponse sp = subjectService.updateSubject(subjectInfoDto);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void deleteSubject() throws Exception {
        ServerResponse sp = subjectService.deleteSubject(1L, 6L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void contribute() throws Exception {
        ServerResponse sp = subjectService.contribute(1L, 3L, 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void getPassRecipeBySubjectId() throws Exception {
        ServerResponse sp = subjectService.getPassRecipeBySubjectId(1L, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void getToCheckRecipeBySubjectId() throws Exception {
        ServerResponse sp = subjectService.getToCheckRecipeBySubjectId(1L, 1L, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void pass() throws Exception {
        ServerResponse sp = subjectService.pass(1L, 3L, 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void reject() throws Exception {
        ServerResponse sp = subjectService.reject(1L, 1L, 3L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void getSubjectByRecipeId() throws Exception {
        ServerResponse sp = subjectService.getSubjectByRecipeId(1L, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

}