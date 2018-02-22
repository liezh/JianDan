package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.dao.RecipeDao;
import com.liezh.dao.SubjectDao;
import com.liezh.dao.UserDao;
import com.liezh.domain.constant.ContributeStatus;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.dto.subject.SubjectQueryDto;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.entity.Subject;
import com.liezh.service.ISubjectService;
import com.liezh.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/20.
 */
@Service
public class SubjectServiceImpl implements ISubjectService {

    private static Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private RecipeDao recipeDao;

    public ServerResponse<PageInfo> querySubject(Long myId, SubjectQueryDto subjectQueryDto, Integer pageNum, Integer pageSize) {
        if (subjectQueryDto == null) {
            subjectQueryDto = new SubjectQueryDto();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Subject subject = new Subject();
        subject.setTitle(subjectQueryDto.getTitle());
        subject.setCreatorId(subjectQueryDto.getCreatorId());
        PageHelper.startPage(pageNum, pageSize);
        List<SubjectInfoDto> subjects = subjectDao.querySubject(subject);
        // TODO 设置是否关注了该主题
        setHasCollect(myId, subjects);
        // 设置是否关注了创建者
        setHasFollow(myId, subjects);
        PageInfo<SubjectInfoDto> pageInfo = new PageInfo<>(subjects);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private void setHasFollow(Long myId, List<SubjectInfoDto> list) {
        if (myId == null || list == null || list.size() <= 0) {
            logger.error("登录用户或主题菜单列表为空！");
            return;
        }
        Set<Long> idolIdSet = userDao.getAllIdolIdSet(myId);
        for (SubjectInfoDto item : list) {
            boolean hasFallow = idolIdSet.contains(item.getId());
            item.setCreatorHasFollow(hasFallow);
        }
    }

    private void setHasCollect(Long myId, List<SubjectInfoDto> list) {
        if (myId == null || list == null || list.size() <= 0) {
            logger.error("登录用户或主题菜单列表为空！");
            return;
        }
        Set<Long> subjectIdSet = subjectDao.getAllFavoriteSubjectIdSet(myId);
        for (SubjectInfoDto item : list) {
            boolean hasCollect = subjectIdSet.contains(item.getId());
            item.setHasCollect(hasCollect);
        }
    }

    public ServerResponse<SubjectInfoDto> querySubjectById(Long myId, Long subjectId) {
        if (myId == null || subjectId == null) {
            logger.error("登录用户id或主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        SubjectInfoDto subjectInfoDto = subjectDao.querySubjectById(subjectId);
        if (subjectInfoDto != null || subjectInfoDto.getId() != null) {
            // TODO 设置是否收藏该主题
            int resultCount = subjectDao.countFavoriteSubject(myId, subjectId);
            subjectInfoDto.setHasCollect(resultCount > 0);

            // 设置是否关注了主题菜单创建者
            resultCount = userDao.countIdolById(myId, subjectInfoDto.getCreatorId());
            subjectInfoDto.setCreatorHasFollow(resultCount > 0);

            logger.info("主题菜单详情查询成功！");
            return ServerResponse.createBySuccess(subjectInfoDto);
        }
        logger.error("主题菜单不存在！ subjectId: {}", subjectId);
        return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_NOT_FOUND);
    }

    public ServerResponse<Long> insertSubject(SubjectInfoDto subjectInfoDto) {
        if (subjectInfoDto == null || StringUtils.isBlank(subjectInfoDto.getTitle())) {
            logger.error("主题菜单标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Subject subject = new Subject();
        subject.setTitle(subjectInfoDto.getTitle());
        subject.setSynopsis(subjectInfoDto.getSynopsis());
        subject.setCover(subjectInfoDto.getCover());
        subject.setCreatorId(subjectInfoDto.getCreatorId());
        int resultCount = subjectDao.insertSubject(subject);
        if (resultCount > 0) {
            logger.info("主题菜单添加成功！ subject: {}", JsonUtil.toJson(subject));
            return ServerResponse.createBySuccess(subject.getId());
        }
        logger.error("主题菜单添加失败！ subject: {}", JsonUtil.toJson(subject));
        return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
    }

    /**
     *  createId 必须从controller设置，以保证更新者是否为该主题的所有者
     * @param subjectInfoDto
     * @return
     */
    public ServerResponse<Integer> updateSubject(SubjectInfoDto subjectInfoDto) {
        if (subjectInfoDto == null || subjectInfoDto.getId() == null || subjectInfoDto.getCreatorId() == null) {
            logger.error("主题菜单id或创建者id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 判断主题是否已存在
        int resultCount = subjectDao.countSubjectBySIdAndCid(subjectInfoDto.getId(), subjectInfoDto.getCreatorId());
        if (resultCount <= 0) {
            logger.error("主题菜单不存在！ subject: {}", JsonUtil.toJson(subjectInfoDto));
            return ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
        }
        Subject subject = new Subject();
        subject.setId(subjectInfoDto.getId());
        subject.setTitle(subjectInfoDto.getTitle());
        subject.setSynopsis(subjectInfoDto.getSynopsis());
        subject.setCover(subjectInfoDto.getCover());
        subject.setCreatorId(subjectInfoDto.getCreatorId());
        resultCount = subjectDao.updateSubject(subject);
        if (resultCount > 0) {
            logger.info("主题菜单修改成功！ subject: {}", JsonUtil.toJson(subject));
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("主题菜单修改失败！ subject: {}", subject);
        return ServerResponse.createByResponseEnum(ResponseEnum.UPDATE_FAILURE);
    }

    public ServerResponse<Integer> deleteSubject(Long myId, Long subjectId) {
        if (myId == null || subjectId == null) {
            logger.error("登录者id主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = subjectDao.countSubjectBySIdAndCid(subjectId, myId);
        if (resultCount <= 0) {
            logger.warn("主题菜单不存在！ subjectId: {}", subjectId);
            return ServerResponse.createBySuccess(0);
        }
        resultCount = subjectDao.deleteSubject(subjectId);
        if (resultCount > 0) {
            logger.info("主题菜单删除成功！ subjectId: {}", subjectId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("主题菜单删除失败！ subjectId: {}", subjectId);
        return ServerResponse.createByResponseEnum(ResponseEnum.DELETE_FAILURE);
    }


    /**
     *  投稿操作
     * @param myId
     * @param recipeId   菜谱id
     * @param subjectId   主题id
     * @return         提醒
     */
    @Transactional
    @Override
    public ServerResponse<Integer> contribute(Long myId, Long subjectId, Long recipeId) {
        if (myId == null || subjectId == null || recipeId == null) {
            logger.error("登录用户id主题菜单id或投稿菜谱id为空");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 主题菜单是否存在
        int scount = subjectDao.countSubjectBySIdAndCid(subjectId, myId);
        if (scount <= 0) {
            logger.error("投稿失败！主题菜单不存在！ subjectId: {}, recipeId: {}",subjectId, recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_NOT_FOUND);
        }
        // 菜谱是否存在，只能是发布的菜谱才可投稿
        RecipeInfoDto recipeOrig = recipeDao.queryRecipeById(recipeId);
        if (recipeOrig == null || recipeOrig.getId() == null) {
            logger.error("投稿失败！菜谱不存在！ subjectId: {}, recipeId: {}", subjectId, recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_NOT_FOUND);
        }
        if (recipeOrig.getStatus() != GlobalConstants.STATUS_RELEASE) {
            logger.error("投稿失败！投稿菜谱尚未发表！ subjectId: {}, recipe: {}", subjectId, JsonUtil.toJson(recipeOrig));
            return ServerResponse.createByResponseEnum(ResponseEnum.OPERATION_DISABLE);
        }
        // 判断当前的菜谱是否是当前用户的，如果不是则不可以把别人的菜谱投稿
        if (recipeOrig.getAuthorId() == null || recipeOrig.getAuthorId() != myId) {
            logger.error("投稿失败！菜谱作者不是当前帐号！ myId: {}, subjectId: {}, recipe: {}",
                    myId, subjectId, JsonUtil.toJson(recipeOrig));
            return ServerResponse.createByResponseEnum(ResponseEnum.PERMISSION_DENIED);
        }

        // 当菜谱已发表在主题菜单中不可重复投稿
        int resultCount = subjectDao.countSubjectRecipeById(subjectId, recipeId, ContributeStatus.PASS.getCode());
        if (resultCount > 0) {
            logger.warn("菜谱已收录！ subjectId: {}, recipeId: {}", subjectId, recipeId);
            return ServerResponse.createBySuccess(0);
        }
        resultCount = subjectDao.countSubjectRecipeById(subjectId, recipeId, ContributeStatus.TO_CHECK.getCode());
        if (resultCount > 0) {
            logger.warn("菜谱已投稿过！ subjectId: {}, recipeId: {}", subjectId, recipeId);
            return ServerResponse.createBySuccess(0);
        }
        resultCount = subjectDao.countSubjectRecipeById(subjectId, recipeId, ContributeStatus.REJECT.getCode());
        if (resultCount >= GlobalConstants.MAX_CONTRIBUTE_COUNT) {
            logger.error("菜谱已达到最大投稿次数-{}-次！", GlobalConstants.MAX_CONTRIBUTE_COUNT);
            return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_MAX_CONTRIBUTE);
        }

        // 添加映射
        resultCount = subjectDao.insertSubjectRecipe(subjectId, recipeId);
        if (resultCount > 0) {
            logger.info("投稿成功！ subjectId: {}, recipeId: {}", subjectId, recipeId);
            return ServerResponse.createBySuccess(resultCount);
        }

        logger.error("投稿失败！ subjectId: {}, recipeId: {}", subjectId, recipeId);
        return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_CONTRIBUTE_FAILURE);
    }

    /**
     *  获取该主题下所有已通过的文章, 任何人可看
     * @param subjectId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getPassRecipeBySubjectId(Long subjectId, Integer pageNum, Integer pageSize) {
        if (subjectId == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<RecipeInfoDto> recipeInfoDtos = subjectDao.querySubjectRecipe(subjectId, ContributeStatus.PASS.getCode());
        PageInfo<RecipeInfoDto> pageInfo = new PageInfo<>(recipeInfoDtos);

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     *  获取该主题下所有的待审核的文章, 只有创建者能查看
     * @param myId
     * @param subjectId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getToCheckRecipeBySubjectId(Long myId, Long subjectId, Integer pageNum, Integer pageSize) {
        if (myId == null || subjectId == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        // 判断该主题是否存在，并且属于登录者 TODO
        SubjectInfoDto subjectOrig = subjectDao.querySubjectById(subjectId);
        if (subjectOrig == null || subjectOrig.getId() == null) {
            logger.error("主题菜单不存在！ subjectId: {}", subjectId);
            return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_NOT_FOUND);
        }
        // 判断主题创建者是否是登录人
        if (subjectOrig.getCreatorId() == null || subjectOrig.getCreatorId() != myId) {
            logger.error("主题菜单创建者不是登录者！不能查看投稿箱！ myId: {}, subject: {}", myId, JsonUtil.toJson(subjectOrig));
            return ServerResponse.createByResponseEnum(ResponseEnum.PERMISSION_DENIED);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<RecipeInfoDto> recipeInfoDtos = subjectDao.querySubjectRecipe(subjectId, ContributeStatus.TO_CHECK.getCode());
        PageInfo<RecipeInfoDto> pageInfo = new PageInfo<>(recipeInfoDtos);

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     *  通过投稿箱中的菜谱
     *  @param myId
     * @param subjectId
     * @param recipeId
     * @return
     */
    @Transactional
    @Override
    public ServerResponse<Integer> pass(Long myId, Long subjectId, Long recipeId) {
        // 重构 TODO
        ServerResponse serverResponse = this.checkSubjectAndRecipeExits(myId, subjectId, recipeId);
        if (!serverResponse.isSuccess()) {
            logger.error("主题菜谱拒绝失败！ 原因：{}, ", JsonUtil.toJson(serverResponse));
            return serverResponse;
        }

        // 修改status 为 PASS
        int updateCount = subjectDao.updateSubjectRecipeStatus(subjectId, recipeId, ContributeStatus.PASS.getCode());
        if (updateCount > 0) {
            logger.info("投稿审核通过成功！ subjectId: {}, recipeId: {}",subjectId, recipeId);
            return ServerResponse.createBySuccess(updateCount);
        }
        logger.error("投稿审核通过失败！ subjectId: {}, recipeId: {}", subjectId, recipeId);
        return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_CONTRIBUTE_PASS_FAILURE);
    }

    /**
     *  拒绝投稿箱中的菜谱
     *  @param myId
     * @param subjectId
     * @param recipeId
     * @return
     */
    public ServerResponse<Integer> reject(Long myId, Long subjectId, Long recipeId) {
        // 重构 TODO
        ServerResponse serverResponse = this.checkSubjectAndRecipeExits(myId, subjectId, recipeId);
        if (!serverResponse.isSuccess()) {
            logger.error("主题菜谱拒绝失败！ 原因：{}, ", JsonUtil.toJson(serverResponse));
            return serverResponse;
        }
        // 修改status 为 REJECT
        int updateCount = subjectDao.updateSubjectRecipeStatus(subjectId, recipeId, ContributeStatus.REJECT.getCode());
        if (updateCount > 0) {
            logger.info("投稿审核拒绝成功！ subjectId: {}, recipeId: {}",subjectId, recipeId);
            return ServerResponse.createBySuccess(updateCount);
        }
        logger.error("投稿审核拒绝失败！ subjectId: {}, recipeId: {}", subjectId, recipeId);
        return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_CONTRIBUTE_REJECT_FAILURE);

    }

    /**
     *  校验主题菜单与菜谱的信息
     * @param myId
     * @param subjectId
     * @param recipeId
     * @return
     */
    private ServerResponse<Integer> checkSubjectAndRecipeExits(Long myId, Long subjectId, Long recipeId) {
        if (myId== null || subjectId == null || recipeId == null) {
            logger.error("主题菜单id或菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 判断是否已经投过稿，并且状态为待审
        int resultCount = subjectDao.countSubjectRecipeById(subjectId, recipeId, ContributeStatus.TO_CHECK.getCode());
        if (resultCount <= 0) {
            logger.error("菜谱没有待审状态的投稿记录！ myId: {}, subjectId: {}, recipeId: {}", myId, subjectId, recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_CONTRIBUTE_RECORD_NOT_FOUND);
        }

        // 主题菜单是否存在
        SubjectInfoDto subjectOrig  = subjectDao.querySubjectById(subjectId);
        if (subjectOrig == null || subjectOrig.getId() == null) {
            logger.error("主题菜单不存在！ subjectId: {}, recipeId: {}", subjectId, recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.SUBJECT_NOT_FOUND);
        }
        // 判断主题菜单是否属于登录者, 当创建者为空， 登录人id不相等时，返回异常错误
        if (subjectOrig.getCreatorId() == null || myId != subjectOrig.getCreatorId()) {
            logger.error("当前登录人不是主题菜单创建者！ myId: {}, subject: {}", myId, JsonUtil.toJson(subjectOrig));
            return ServerResponse.createByResponseEnum(ResponseEnum.PERMISSION_DENIED);
        }
        // 菜谱是否存在
        int rcount = recipeDao.countRecipeById(recipeId);
        if (rcount <= 0) {
            logger.error("菜谱不存在！ subject: {}, recipeId: {}", subjectId, recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_NOT_FOUND);
        }
        logger.info("主题菜单与菜单状态正常！ myId: {}, subjectId: {}, recipeId: {}", myId, subjectId, recipeId);
        return ServerResponse.createBySuccess();

    }

    /**
     *  获取某菜谱，所有投稿并发布了的主题菜单列表
     * @param recipeId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getSubjectByRecipeId(Long recipeId, Integer pageNum, Integer pageSize) {
        if (recipeId == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        // 菜谱是否存在
        int rcount = recipeDao.countRecipeById(recipeId);
        if (rcount <= 0) {
            logger.error("菜谱不存在！ recipeId: {}", recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_NOT_FOUND);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SubjectInfoDto> subjects = subjectDao.getSubjectByRecipeId(recipeId);
        PageInfo<SubjectInfoDto> pageInfo = new PageInfo<>(subjects);
        logger.info("菜谱成功投稿的主题！ recipeId: {}, subjectList: {}", recipeId, JsonUtil.toJson(pageInfo));
        return ServerResponse.createBySuccess(pageInfo);
    }

    // 关注主题

    // 获取已关注的主题

    // private 设置关注标记



}
