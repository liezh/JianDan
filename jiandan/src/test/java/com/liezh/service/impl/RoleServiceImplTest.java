package com.liezh.service.impl;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Role;
import com.liezh.service.IRoleService;
import com.liezh.utils.JsonUtil;
import org.junit.After;
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
public class RoleServiceImplTest {

    @Autowired
    private IRoleService roleService;

    @Test
    public void queryRole() throws Exception {
        ServerResponse sp = roleService.queryRole(null, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void queryRoleById() throws Exception {
        ServerResponse sp = roleService.queryRoleById(1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertRole() throws Exception {
        Role role = new Role();
        role.setName("钻石vip");
        role.setStatus(GlobalConstants.STATUS_ENABLE);
        ServerResponse sp = roleService.insertRole(role);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void updateRole() throws Exception {
        Role role = new Role();
        role.setId(6L);
        role.setName("大会员");
        ServerResponse sp = roleService.updateRole(role);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void deleteRole() throws Exception {
        ServerResponse sp = roleService.deleteRole(5L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

}