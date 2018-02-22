package com.liezh.dao;

import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.entity.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface RecipeDao {

    List<RecipeInfoDto> queryRecipe(Recipe recipe);

    RecipeInfoDto queryRecipeById(Long recipeId);

    Integer insertRecipe(Recipe recipe);

    Integer updateRecipe(Recipe recipe);

    Integer deleteRecipe(Long recipeId);

    Integer countRecipeById(Long recipeId);


}
