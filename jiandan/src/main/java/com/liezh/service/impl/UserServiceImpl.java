package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.dao.RoleDao;
import com.liezh.dao.UserDao;
import com.liezh.domain.common.TokenCache;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.constant.RoleNameEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.dto.user.UserQueryDto;
import com.liezh.domain.entity.Role;
import com.liezh.domain.entity.User;
import com.liezh.exception.ServiceException;
import com.liezh.service.IUserService;
import com.liezh.utils.JsonUtil;
import com.liezh.utils.LoggerUtil;
import com.liezh.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Administrator on 2018/2/17.
 */
@Service
public class UserServiceImpl implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public ServerResponse<PageInfo> queryUser(Long myId, UserQueryDto userQueryDto, Integer pageNum, Integer pageSize) {
        if (userQueryDto == null) {
            userQueryDto = new UserQueryDto();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        User query = new User();
        query.setAccount(userQueryDto.getAccount());
        query.setUsername(userQueryDto.getUsername());
        query.setMobile(userQueryDto.getMobile());
        query.setEmail(userQueryDto.getEmail());
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfoDto> users = null;
        try {
            users = userDao.queryUser(query);
            setHasFollow(myId, users);
        } catch (Exception e) {
            logger.error("用户列表查询失败！ params：{}， msg：{}", JsonUtil.toJson(query), e.getMessage());
            ServerResponse.createByResponseEnum(ResponseEnum.QUERY_FAILURE);
        }
        PageInfo<UserInfoDto> pageInfo = new PageInfo<>(users);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private void setHasFollow(Long myId, List<UserInfoDto> list) {
        Set<Long> idolIdSet = userDao.getAllIdolIdSet(myId);
        for (UserInfoDto item : list) {
            boolean hasFallow = idolIdSet.contains(item.getId());
            item.setHasFollow(hasFallow);
        }
    }

    @Override
    public ServerResponse<UserInfoDto> queryUserById(Long myId, Long userId) {
        if (userId == null) {
            logger.error("用户id为空！");
            ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        UserInfoDto userInfoDto = userDao.queryUserById(userId);
        int count = userDao.countIdolById(myId, userId);
        if (count > 0) {
            userInfoDto.setHasFollow(true);
        } else {
            userInfoDto.setHasFollow(false);
        }
        userInfoDto.setSalt(null);
        userInfoDto.setPassword(null);

        return ServerResponse.createBySuccess(userInfoDto);
    }

    @Transactional
    @Override
    public ServerResponse<Long> insertUser(UserInfoDto userInfoDto) {
        if (userInfoDto == null || StringUtils.isBlank(userInfoDto.getAccount())
                || StringUtils.isBlank(userInfoDto.getPassword()) || StringUtils.isBlank(userInfoDto.getUsername())) {
            logger.error("缺少 account | password | username ！");
            ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }

        // 判断账号名是否存在
        if (StringUtils.isNotBlank(userInfoDto.getAccount())) {
            User temp = new User();
            temp.setAccount(userInfoDto.getAccount());
            if (userDao.countUser(temp) > 0) {
                logger.error("用户账号名已经注册！ account：{}", userInfoDto.getAccount());
                return ServerResponse.createByResponseEnum(ResponseEnum.USER_ACCOUNT_EXIST);
            }
        }
        // 判断手机号是否已注册
        if (StringUtils.isNotBlank(userInfoDto.getMobile())) {
            User temp = new User();
            temp.setMobile(userInfoDto.getMobile());
            if (userDao.countUser(temp) > 0) {
                logger.error("用户手机号已经注册！ mobile：{}", userInfoDto.getMobile());
                return ServerResponse.createByResponseEnum(ResponseEnum.USER_MOBILE_EXIST);
            }
        }
        // 判断邮箱是否已注册
        if (StringUtils.isNotBlank(userInfoDto.getEmail())) {
            User temp = new User();
            temp.setEmail(userInfoDto.getEmail());
            if (userDao.countUser(temp) > 0) {
                logger.error("用户邮箱已注册！ email：{}", userInfoDto.getEmail());
                return ServerResponse.createByResponseEnum(ResponseEnum.USER_EMAIL_EXIST);
            }
        }

        // initRole    初始化用户角色
        Role role = null;
        try {
            role = initRole();
        } catch (ServiceException e) {
            logger.error("用户添加失败！ msg:{}, user: {}", e.getMessage(), JsonUtil.toJson(userInfoDto));
            return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
        }

        User user = new User();
        user.setAccount(userInfoDto.getAccount());
        user.setUsername(userInfoDto.getUsername());
        String salt = UUID.randomUUID().toString().replace("-", "");
        user.setSalt(salt);
        String md5 = MD5Util.MD5EncodeUtf8(userInfoDto.getPassword(), salt);
        user.setPassword(md5);
        user.setSynopsis(userInfoDto.getSynopsis());
        user.setMobile(userInfoDto.getMobile());
        user.setEmail(userInfoDto.getEmail());
        user.setQuestion(userInfoDto.getQuestion());
        user.setAnswer(userInfoDto.getAnswer());
        int resultCount = userDao.insertUser(user);
        if (resultCount > 0) {
            // 添加角色
            resultCount = userDao.userRoleMapping(user.getId(), role.getId());
            if (resultCount > 0) {
                logger.info("用户添加成功！ user: {}, user.roles: {}", user, role);
                return ServerResponse.createBySuccess(user.getId());
            }
        }
        logger.error("用户添加失败！ user: {}", userInfoDto);
        return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
    }

    private Role initRole() throws ServiceException {
        // 初始化角色为 USER
        Role role = roleDao.queryRoleByName(RoleNameEnum.USER.getName());
        if (role == null || role.getId() == null) {
            role = new Role();
            role.setName(RoleNameEnum.USER.getName());
            role.setStatus(GlobalConstants.STATUS_ENABLE);
            int resultCount = roleDao.insertRole(role);
            if (resultCount <= 0) {
                logger.error("初始化角色失败！");
                throw new ServiceException("初始化角色失败！");
            }
            logger.info("初始化角色成功！ role: {}", JsonUtil.toJson(role));
        }
        return role;
    }

    @Transactional
    @Override
    public ServerResponse<Integer> updateUser(UserInfoDto userInfoDto) {
        if (userInfoDto == null || userInfoDto.getId() == null) {
            logger.error("缺少 user id ！");
            ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 判断用户是否存在
        UserInfoDto userOrig = userDao.queryUserById(userInfoDto.getId());
        if (userOrig == null && userOrig.getId() == null) {
            logger.error("用户不存在！ uid：{}", userInfoDto.getId());
            ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
        }
        // 判断账号名是否存在
        if (StringUtils.isNotBlank(userInfoDto.getAccount())
                && !StringUtils.equals(userInfoDto.getAccount(), userOrig.getAccount())) {
            User temp = new User();
            temp.setAccount(userInfoDto.getAccount());
            if (userDao.countUser(temp) > 0) {
                logger.error("用户账号名已经注册！ account：{}", userInfoDto.getAccount());
                return ServerResponse.createByResponseEnum(ResponseEnum.USER_ACCOUNT_EXIST);
            }
        }
        // 判断手机号是否已注册
        if (StringUtils.isNotBlank(userInfoDto.getMobile())
                && !StringUtils.equals(userInfoDto.getMobile(), userOrig.getMobile())) {
            User temp = new User();
            temp.setMobile(userInfoDto.getMobile());
            if (userDao.countUser(temp) > 0) {
                logger.error("用户手机号已经注册！ mobile：{}", userInfoDto.getMobile());
                return ServerResponse.createByResponseEnum(ResponseEnum.USER_MOBILE_EXIST);
            }
        }
        // 判断邮箱是否已注册
        if (StringUtils.isNotBlank(userInfoDto.getEmail())
                && !StringUtils.equals(userInfoDto.getEmail(), userOrig.getEmail())) {
            User temp = new User();
            temp.setEmail(userInfoDto.getEmail());
            if (userDao.countUser(temp) > 0) {
                logger.error("用户邮箱已注册！ email：{}", userInfoDto.getEmail());
                return ServerResponse.createByResponseEnum(ResponseEnum.USER_EMAIL_EXIST);
            }
        }

        User user = new User();
        user.setId(userInfoDto.getId());
        user.setAccount(userInfoDto.getAccount());
        user.setUsername(userInfoDto.getUsername());
        String salt = userOrig.getSalt();
        String md5 = MD5Util.MD5EncodeUtf8(userInfoDto.getPassword(), salt);
        // 判断密码是否更改
        if (!StringUtils.equals(md5, userOrig.getPassword())) {
            user.setSalt(salt);
            user.setPassword(md5);
        }
        user.setSynopsis(userInfoDto.getSynopsis());
        user.setMobile(userInfoDto.getMobile());
        user.setEmail(userInfoDto.getEmail());
        user.setQuestion(userInfoDto.getQuestion());
        user.setAnswer(userInfoDto.getAnswer());
        int resultCount = userDao.updateUser(user);
        if (resultCount > 0) {
            logger.error("用户信息更新成功！ user:{}", JsonUtil.toJson(userInfoDto));
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("用户信息更新失败！ user:{}", JsonUtil.toJson(userInfoDto));
        return ServerResponse.createByResponseEnum(ResponseEnum.UPDATE_FAILURE);
    }

    @Override
    public ServerResponse<Integer> deleteUser(Long userId) {
        User user = new User();
        user.setId(userId);
        if (userDao.countUser(user) <= 0) {
            logger.warn("用户户不存在！ id：{}", userId);
            return ServerResponse.createBySuccess(0);
        }
        int resultCount = userDao.deleteUser(userId);
        if (resultCount > 0) {
            logger.info("用户删除成功！ userId: {}", userId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("用户删除失败！ userId: {}", userId);
        return ServerResponse.createByResponseEnum(ResponseEnum.DELETE_FAILURE);
    }

    @Override
    public ServerResponse<Integer> addRoleMapping(Long userId, Long roleId) {
        if (userId == null || roleId == null) {
            logger.error("登录用户的id或需要添加的角色id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = userDao.userRoleMapping(userId, roleId);
        if (resultCount > 0) {
            logger.info("添加角色成功！ userId: {}, roleId: {}", userId, roleId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("添加角色失败！ userId: {}, roleId: {}", userId, roleId);
        return ServerResponse.createByResponseEnum(ResponseEnum.USER_ROLE_MAPPING_FAILURE);

    }

    @Override
    public ServerResponse<UserInfoDto> queryUserByAccountOrMobile(String accountOrMobile) {
        if (StringUtils.isBlank(accountOrMobile)) {
            logger.error("用户账号名/手机号皆为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        UserInfoDto userInfoDto = userDao.queryUserByAccountOrMobile(accountOrMobile);

        userInfoDto.setPassword(null);
        userInfoDto.setSalt(null);
        return ServerResponse.createBySuccess(userInfoDto);
    }

    @Override
    public ServerResponse<String> findQuestionByAccount(String account) {
        if (StringUtils.isBlank(account)) {
            logger.error("账号名为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        UserInfoDto userOrig = userDao.queryUserByAccountOrMobile(account);
        if(userOrig == null || userOrig.getId() == null) {
            // 用户不存在
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_TARGET_NOT_FOUND);
        }
        String question = userOrig.getQuestion();
        if(StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题为空");
    }

//    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userDao.checkAnswer(username, question, answer);
        if(resultCount > 0) {
            // 说明问题是属于这个用户的且答案正确
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.FORGET_TOKEN_PREFIX  + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("答案错误");
    }


    @Override
    public ServerResponse<String> forgetResetPassword(String account, String passwordNew, String forgetToken) {
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，需要传递token");
        }
        UserInfoDto userOrig = userDao.queryUserByAccountOrMobile(account);
        if(userOrig == null || userOrig.getId() == null) {
            // 用户不存在
            logger.error("用户不存在！ account:{}", account);
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_TARGET_NOT_FOUND);
        }
        String token = TokenCache.getKey(TokenCache.FORGET_TOKEN_PREFIX + account);
        if(StringUtils.isBlank(token)) {
            logger.error("密码token过期！account:{}, passwordNew:{}, forgetToken:{}", account, passwordNew, forgetToken);
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_TOKEN_TIMEOUT);
        }
        if(StringUtils.equals(forgetToken, token)) {
            // 更新密码
            String md5password = MD5Util.MD5EncodeUtf8(passwordNew, userOrig.getSalt());
            User user = new User();
            user.setId(userOrig.getId());
            user.setPassword(md5password);
            int resultCount = userDao.updateUser(user);
            if(resultCount > 0) {
                return ServerResponse.createBySuccessMessage("密码更改成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("token 错误，请重新获取token");
        }
        return ServerResponse.createByErrorMessage("密码修改失败");
    }

    @Override
    public ServerResponse<Integer> resetPassword(String passwordOld, String passwordNew, Long userId) {
        if (StringUtils.isBlank(passwordOld) && StringUtils.isBlank(passwordNew)) {
            logger.error("新密码或旧密码为空！");
            ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 防止横向越权,要校验这个旧密码是属于这个用户所有
        UserInfoDto userOrig = userDao.queryUserById(userId);
        if(userOrig == null || StringUtils.isBlank(userOrig.getAccount())) {
            logger.error("用户不存在！");
            return ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
        }
        String oldMD5 = MD5Util.MD5EncodeUtf8(passwordOld, userOrig.getSalt());
        // 输入的旧密码与旧密码不匹配
        if (!StringUtils.equals(oldMD5, userOrig.getPassword())) {
            logger.error("密码匹配失败！");
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_PASSWORD_ILLEGAL);
        }

        String salt = userOrig.getSalt();
        User user = new User();
        user.setId(userId);
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew, salt));
        int resultCount = userDao.updateUser(user);
        if (resultCount > 0) {
            logger.info("用户密码修改成功！ 新密码：{}, 旧密码：{}, 用户：{}", passwordNew, passwordOld, JsonUtil.toJson(userOrig));
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("密码更新失败！ 新密码：{}, 旧密码：{}, 用户：{}", passwordNew, passwordOld, JsonUtil.toJson(userOrig));
        return ServerResponse.createBySuccessMessage("密码更新失败");
    }

    @Override
    public ServerResponse<Integer> follow(Long myId, Long idolId) {
        if (myId == null || idolId == null) {
            logger.error("缺少登录用户id或关注用户id");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        UserInfoDto idolUser = userDao.queryUserById(idolId);
        if(idolUser == null) {
            logger.error("关注目标不存在！ idolId: {}", idolId);
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_TARGET_NOT_FOUND);
        }
        if(idolUser.getId().equals(myId)) {
            logger.error("不能关注自己！fansId:{}, idolId:{}", myId, idolId);
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_TARGET_IS_SELF);
        }
        if(userDao.countIdolById(myId, idolId) > 0) {
            logger.error("用户已关注！fansId:{}, idolId:{}", myId, idolId);
            return ServerResponse.createByResponseEnum(ResponseEnum.USER_TARGET_EXIST);
        }
        int resultCount = userDao.follow(myId, idolId);
        if(resultCount > 0) {
            logger.info("关注成功！ fansId:{}, idolId:{}", myId, idolId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("关注失败！ fansId:{}, idolId:{}", myId, idolId);
        return ServerResponse.createByResponseEnum(ResponseEnum.USER_FOLLOW_FAILURE);
    }

    @Override
    public ServerResponse<Integer> unfollow(Long myId, Long idolId) {
        if (myId == null || idolId == null) {
            logger.error("缺少登录用户id或关注用户id");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = userDao.unfollow(myId, idolId);
        if (resultCount > 0) {
            logger.info("取消关注成功！ fansId:{}, idolId:{}", myId, idolId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.info("取消关注失败！ fansId:{}, idolId:{}", myId, idolId);
        return ServerResponse.createByResponseEnum(ResponseEnum.USER_UNFOLLOW_FAILURE);
    }

    @Override
    public ServerResponse<PageInfo> getAllIdol(Long myId, Integer pageNum, Integer pageSize) {
        if (myId == null) {
            logger.error("登录用户的id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfoDto> userInfoDtos = userDao.getAllIdolById(myId);
        for (UserInfoDto item : userInfoDtos) {
            item.setHasFollow(true);
        }
        PageInfo<UserInfoDto> pageInfo = new PageInfo<>(userInfoDtos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getAllFans(Long myId, Integer pageNum, Integer pageSize) {
        if (myId == null) {
            logger.error("登录用户的id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfoDto> userInfoDtos = userDao.getAllFansById(myId);
        // 设置关注标记
        setHasFollow(myId, userInfoDtos);
        PageInfo<UserInfoDto> pageInfo = new PageInfo<>(userInfoDtos);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
