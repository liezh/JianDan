package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.dto.user.UserQueryDto;
import com.liezh.domain.entity.User;

/**
 * Created by Administrator on 2018/2/17.
 */
public interface IUserService {

    ServerResponse<PageInfo> queryUser(Long myId, UserQueryDto userQueryDto, Integer pageNum, Integer pageSize);

    ServerResponse<UserInfoDto> queryUserById(Long myId, Long userId);

    ServerResponse<Long> insertUser(UserInfoDto userInfoDto);

    ServerResponse<Integer> updateUser(UserInfoDto userInfoDto);

    ServerResponse<Integer> deleteUser(Long userInfoDto);

    ServerResponse<Integer> addRoleMapping(Long userId, Long roleId);

    ServerResponse<UserInfoDto> queryUserByAccountOrMobile(String accountOrMobile);

    ServerResponse<String> findQuestionByAccount(String account);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String account, String passwordNew, String forgetToken);

    ServerResponse<Integer> resetPassword(String passwordOld, String passwordNew, Long userId);

    ServerResponse<Integer> follow(Long myId, Long idolId);

    ServerResponse<Integer> unfollow(Long myId, Long idolId);

    ServerResponse<PageInfo> getAllIdol(Long myId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> getAllFans(Long myId, Integer pageNum, Integer pageSize);




}
