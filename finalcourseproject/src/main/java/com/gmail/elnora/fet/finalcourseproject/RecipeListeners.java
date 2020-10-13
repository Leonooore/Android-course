package com.gmail.elnora.fet.finalcourseproject;

import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;

public interface RecipeListeners {
    void onDishTypeClick(DishTypeEnum dishType);
    void onRecipeClick(RecipeDataModel recipeDataModel);
    void onFabTodoCookClick(int recipeId, String title);
    void onFabAddTodoListClick(TodoRecipeEntity recipe);
    void onTodoItemClick(RecipeDataModel recipeDataModel);
    void onRemoveRecipeClick(TodoRecipeEntity recipe);
    void onSearchedRecipeClick(SearchRecipeDataModel searchRecipeDataModel);
}
