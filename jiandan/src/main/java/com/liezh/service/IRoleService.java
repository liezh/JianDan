package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Role;

/**
 * Created by Administrator on 2018/2/17.
 */
public interface IRoleService {

    ServerResponse<PageInfo> queryRole(Role role, Integer pageNum, Integer pageSize);

    ServerResponse<Role> queryRoleById(Long roleId);

    ServerResponse<Long> insertRole(Role role);

    ServerResponse<Integer> updateRole(Role role);

    ServerResponse<Integer> deleteRole(Long roleId);
}
