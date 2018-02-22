package com.liezh.service.impl;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.dto.user.UserQueryDto;
import com.liezh.service.IUserService;
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
 * Created by Administrator on 2018/2/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    public void queryUser() throws Exception {
        ServerResponse<PageInfo> sp = userService.queryUser(1L, null, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void queryUserById() throws Exception {
        ServerResponse<UserInfoDto> sp = userService.queryUserById(1L, 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertUser() throws Exception {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setAccount("liezh");
        userInfoDto.setUsername("yoyo嘿");
        userInfoDto.setPassword("123456");
        userInfoDto.setMobile("13556024287");
        userInfoDto.setEmail("13556024287@163.com");
        userInfoDto.setQuestion("我最爱的动物？");
        userInfoDto.setAnswer("喵");
        userInfoDto.setSynopsis("TO THE MOON");
        ServerResponse<Long> sp = userService.insertUser(userInfoDto);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess() ;
    }

    @Test
    public void updateUser() throws Exception {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(3L);
        userInfoDto.setAccount("9527");
        userInfoDto.setUsername("华安");
        userInfoDto.setPassword("123456");
        userInfoDto.setMobile("13556024286");
        userInfoDto.setEmail("13556024286@163.com");
        userInfoDto.setQuestion("我最爱的动物？");
        userInfoDto.setAnswer("喵");
        userInfoDto.setSynopsis("TO THE MOON");
        ServerResponse<Integer> sp = userService.updateUser(userInfoDto);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess() ;
    }

    @Test
    public void deleteUser() throws Exception {
        ServerResponse sp = userService.deleteUser(5L);
        assert sp.isSuccess();
    }

    @Test
    public void addRoleMapping() throws Exception {
        ServerResponse sp = userService.addRoleMapping(1L, 1L);
        assert sp.isSuccess();
    }

    @Test
    public void queryUserByAccountOrMobile() throws Exception {
        ServerResponse sp = userService.queryUserByAccountOrMobile("yoyo");
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void findQuestionByAccount() throws Exception {
        ServerResponse sp = userService.findQuestionByAccount("yoyo");
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void checkAnswer() throws Exception {
        ServerResponse sp = userService.checkAnswer("yoyo", "最爱的动物", "喵，汪，啾");
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void forgetResetPassword() throws Exception {
        ServerResponse sp = userService.checkAnswer("yoyo", "最爱的动物", "喵，汪，啾");
        System.out.println(JsonUtil.toJson(sp));
        ServerResponse sp1 = userService.forgetResetPassword("yoyo", "987654321", sp.getData().toString());
        System.out.println(JsonUtil.toJson(sp1));
        assert sp.isSuccess() && sp1.isSuccess();
    }

    @Test
    public void resetPassword() throws Exception {
        ServerResponse sp = userService.resetPassword("987654321", "123456789", 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void follow() throws Exception {
        ServerResponse sp = userService.follow(1L, 3L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void unfollow() throws Exception {
        ServerResponse sp = userService.unfollow(1L, 3L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void getAllIdol() throws Exception {
        ServerResponse sp = userService.getAllIdol(1L, 1, 30);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void getAllFans() throws Exception {
        ServerResponse sp = userService.getAllFans(3L, 1, 30);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

}