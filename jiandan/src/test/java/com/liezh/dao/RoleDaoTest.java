package com.liezh.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.domain.entity.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2018/2/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void queryRole() throws Exception {
        PageHelper.startPage(1, 20);
        Role role = new Role();
        List<Role> roles = roleDao.queryRole(role);
        Page<Role> page = ((Page) roles);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        System.out.println("Total: " + ((Page) roles).getTotal());
        System.out.println(roles);
        assert pageInfo != null;
    }

    @Test
    public void queryRoleById() throws Exception {
        Role result = roleDao.queryRoleById(1L);
        assert result != null;
    }

    @Test
    public void insertRole() throws Exception {
        Role role = new Role();
        role.setName("yoyo");
        int count = roleDao.insertRole(role);
        assert count > 0;
    }

    @Test
    public void updateArea() throws Exception {
        Role role = new Role();
        role.setId(3L);
        role.setName("ADMIN");
        int count = roleDao.updateRole(role);
        assert count > 0;
    }

    @Test
    public void deleteRole() throws Exception {
        int count = roleDao.deleteRole(2L);
        assert count > 0;
    }




}