package com.liezh.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.entity.Recipe;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class RecipeDaoTest {

    @Autowired
    private RecipeDao recipeDao;

    @Test
    public void queryRecipe() throws Exception {
        PageHelper.startPage(1, 20);
        Recipe recipe = new Recipe();
        List<RecipeInfoDto> recipes = recipeDao.queryRecipe(recipe);
        PageInfo<RecipeInfoDto> pageInfo = new PageInfo<>(recipes);
        assert pageInfo != null && pageInfo.getTotal() > 0;
    }

    @Test
    public void queryRecipeById() throws Exception {
        RecipeInfoDto recipe = recipeDao.queryRecipeById(1L);
        assert recipe != null;
    }

    @Test
    public void insertRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setTitle("煎蛋");
        recipe.setContent("把鸡蛋煎熟");
        recipe.setCover("https://upload.jianshu.io/users/upload_avatars/1835526/6ac158c7-6af2-415a-886e-2868dd256783.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        recipe.setGoodCount(0);
        recipe.setStatus(GlobalConstants.STATUS_DRAFT);
        recipe.setAuthorId(1L);
        recipe.setProcess("[]");
        recipe.setMaterials("[]");
        int count = recipeDao.insertRecipe(recipe);
        assert count > 0 && recipe.getId() != null;
    }

    @Test
    public void updateRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("煎蛋");
        recipe.setContent("把鸡蛋煎熟");
        recipe.setCover("https://upload.jianshu.io/users/upload_avatars/1835526/6ac158c7-6af2-415a-886e-2868dd256783.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96");
        recipe.setGoodCount(0);
        recipe.setStatus(GlobalConstants.STATUS_DRAFT);
        recipe.setAuthorId(1L);
        recipe.setProcess("[]");
        recipe.setMaterials("[]");
        int count = recipeDao.updateRecipe(recipe);
        assert count > 0 && recipe.getId() != null;
    }

    @Test
    public void deleteRecipe() throws Exception {
        int count = recipeDao.deleteRecipe(2L);
        assert count > 0;
    }

}