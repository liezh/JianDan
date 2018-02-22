package com.liezh.dao;

import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.entity.User;
import com.liezh.utils.MD5Util;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void queryUser() throws Exception {
        User user = new User();
        user.setUsername("yo");
        List<UserInfoDto> users = userDao.queryUser(user);
        assert users != null && users.size() > 0;
    }

    @Test
    public void queryUserById() throws Exception {
        UserInfoDto user = userDao.queryUserById(1L);
        assert user != null;
    }

    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setAccount("yoyo");
        user.setUsername("yoyo");
        user.setMobile("13556024285");
        user.setEmail("1234567890@163.com");
        user.setSalt(UUID.randomUUID().toString().replace("-", ""));
        user.setPassword(MD5Util.MD5EncodeUtf8("123456", user.getSalt()));
        user.setQuestion("最爱的动物");
        user.setAnswer("喵");
        int count = userDao.insertUser(user);
        assert count > 0 && user.getId() != null;
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setAnswer("喵，汪，啾");
        int count = userDao.updateUser(user);
        assert count > 0;
    }

    @Test
    public void deleteUser() throws Exception {
        int count = userDao.deleteUser(2L);
        assert count > 0;
    }

}