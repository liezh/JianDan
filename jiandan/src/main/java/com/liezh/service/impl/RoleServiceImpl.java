package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.dao.RoleDao;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.role.RoleQueryDto;
import com.liezh.domain.entity.Role;
import com.liezh.service.IRoleService;
import com.liezh.utils.JsonUtil;
import com.liezh.utils.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/2/17.
 */
@Service
public class RoleServiceImpl implements IRoleService {

    private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleDao roleDao;

    public ServerResponse<PageInfo> queryRole(Role role, Integer pageNum, Integer pageSize) {
        if (role == null) {
            role = new Role();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleDao.queryRole(role);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<Role> queryRoleById(Long roleId) {
        if (roleId == null) {
            logger.error("角色id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Role role = roleDao.queryRoleById(roleId);
        return ServerResponse.createBySuccess(role);
    }

    public ServerResponse<Long> insertRole(Role role) {
        if (role == null || StringUtils.isEmpty(role.getName())) {
            logger.error("缺少角色 name！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (role.getStatus() == null || role.getStatus() != GlobalConstants.STATUS_ENABLE) {
            role.setStatus(GlobalConstants.STATUS_DISABLE);
        }
        // 判断角色是否存在，角色名是否重复， 不重复添加
        Role roleOrig = roleDao.queryRoleByName(role.getName());
        if (roleOrig == null || roleOrig.getId() == null) {
            logger.warn("角色信息已存在！ role: {}", JsonUtil.toJson(roleOrig));
            return ServerResponse.createBySuccess(roleOrig.getId());
        }

        int resultCount = roleDao.insertRole(role);
        if (resultCount > 0) {
            logger.info("角色添加成功！ role: {}", JsonUtil.toJson(role));
            return ServerResponse.createBySuccess(role.getId());
        }
        logger.error("角色添加失败！ role: {}", role);
        return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
    }

    public ServerResponse<Integer> updateRole(Role role) {
        if (role == null || StringUtils.isEmpty(role.getName())) {
            logger.error("缺少角色 name！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (role.getStatus() != null && role.getStatus() != GlobalConstants.STATUS_ENABLE) {
            role.setStatus(GlobalConstants.STATUS_DISABLE);
        }
        // 判断角色是否存在，角色名是否重复
        Role roleOrig = roleDao.queryRoleById(role.getId());
        if (roleOrig == null || roleOrig.getId() == null) {
            logger.error("角色信息不存在！ role: {}", JsonUtil.toJson(role));
            return ServerResponse.createByResponseEnum(ResponseEnum.ROLE_NOT_FOUND);
        }

        // 判断角色名是否存在
        if (StringUtils.isNotBlank(roleOrig.getName())
                && !StringUtils.equals(roleOrig.getName(), role.getName())) {
            Role temp = roleDao.queryRoleByName(role.getName());
            if (temp != null) {
                logger.error("角色名已存在！ role: {}", JsonUtil.toJson(role));
                return ServerResponse.createByResponseEnum(ResponseEnum.ROLE_NAME_EXIST);
            }
        }

        int resultCount = roleDao.updateRole(role);
        if (resultCount > 0) {
            logger.info("角色信息修改成功！ role: {}", JsonUtil.toJson(role));
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("角色信息修改失败！ role: {}", JsonUtil.toJson(role));
        return ServerResponse.createByResponseEnum(ResponseEnum.UPDATE_FAILURE);
    }

    public ServerResponse<Integer> deleteRole(Long roleId) {
        if (roleId == null) {
            logger.error("角色id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = roleDao.deleteRole(roleId);
        if (resultCount > 0) {
            logger.info("角色删除成功！ roleId: {}", roleId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("角色删除失败！ roleId: {}", roleId);
        return ServerResponse.createByResponseEnum(ResponseEnum.DELETE_FAILURE);
    }


}
