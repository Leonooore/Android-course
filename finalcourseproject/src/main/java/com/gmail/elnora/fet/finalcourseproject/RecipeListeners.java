package com.gmail.elnora.fet.finalcourseproject;

import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;

public interface RecipeListeners {
    void onDishTypeClick(DishTypeEnum dishType);
    void onRecipeClick(RecipeDataModel recipeDataModel);
    void onFabTodoCookClick(int recipeId);
    void onFabAddTodoListClick(TodoRecipeEntity recipe);
    void onTodoItemClick(int recipeId);
    void onRemoveRecipeClick(TodoRecipeEntity recipe);
}
