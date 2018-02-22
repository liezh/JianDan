package com.liezh.dao;

import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.entity.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface SubjectDao {

    List<SubjectInfoDto> querySubject(Subject subject);

    SubjectInfoDto querySubjectById(Long subjectId);

    Integer insertSubject(Subject subject);

    Integer updateSubject(Subject subject);

    Integer deleteSubject(Long subjectId);

    Integer countSubjectBySIdAndCid(@Param("subjectId") Long subjectId, @Param("creatorId") Long creatorId);

    Integer countSubjectRecipeById(@Param("subjectId") Long subjectId,
                                   @Param("recipeId") Long recipeId, @Param("status") Integer status);

    List<RecipeInfoDto> querySubjectRecipe(@Param("subjectId") Long subjectId, @Param("status") Integer status);

    Integer insertSubjectRecipe(@Param("subjectId") Long subjectId, @Param("recipeId") Long recipeId);

    Integer updateSubjectRecipeStatus(@Param("subjectId") Long subjectId, @Param("recipeId") Long recipeId, @Param("status") Integer status);

    List<SubjectInfoDto> getSubjectByRecipeId(Long recipeId);

    // 收藏相关

    Integer countFavoriteSubject(@Param("userId") Long userId,@Param("subjectId") Long subjectId);

    Set<Long> getAllFavoriteSubjectIdSet(Long userId);

}
