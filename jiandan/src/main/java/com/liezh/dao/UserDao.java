package com.liezh.dao;

import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface UserDao {

    List<UserInfoDto> queryUser(User user);

    UserInfoDto queryUserById(Long userId);

    Integer insertUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(Long userId);

    Integer countUser(User user);

    UserInfoDto queryUserByAccountOrMobile(@Param("accountOrMobile") String accountOrMobile);

    Integer countIdolById(@Param("fansId") Long fansId, @Param("idolId") Long idolId);

    Integer follow(@Param("fansId") Long fansId, @Param("idolId") Long idolId);

    Integer unfollow(@Param("fansId") Long fansId, @Param("idolId") Long idolId);

    List<UserInfoDto> getAllIdolById(Long fansId);

    List<UserInfoDto> getAllFansById(Long myId);

    Set<Long> getAllIdolIdSet(Long fansId);

    Integer checkAnswer(@Param("account") String account,@Param("question") String question, @Param("answer") String answer);

    Integer userRoleMapping(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
