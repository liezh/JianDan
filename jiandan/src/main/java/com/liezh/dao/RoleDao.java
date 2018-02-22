package com.liezh.dao;

import com.liezh.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/2/14.
 */
@Repository
public interface RoleDao {

    /**
     * 列出角色列表
     *
     * @return roleList
     */
    List<Role> queryRole(Role role);

    Role queryRoleById(Long roleId);

    Integer insertRole(Role role);

    Integer updateRole(Role role);

    Integer deleteRole(Long roleId);

    Role queryRoleByName(String name);

}
