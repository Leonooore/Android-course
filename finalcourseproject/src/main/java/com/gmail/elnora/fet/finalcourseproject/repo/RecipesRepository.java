package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.JokeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.StepDataModel;

import java.util.List;

import io.reactivex.Single;

public interface RecipesRepository {
    Single<List<RecipeDataModel>> getRecipesByType(String type);
    Single<List<IngredientDataModel>> getIngredientsByRecipeId(int recipeId);
    Single<List<StepDataModel>> getStepsByRecipeId(int recipeId);
    Single<JokeDataModel> getJoke();
    Single<List<SearchRecipeDataModel>> getSearchRecipes(String searchQuery);
}
