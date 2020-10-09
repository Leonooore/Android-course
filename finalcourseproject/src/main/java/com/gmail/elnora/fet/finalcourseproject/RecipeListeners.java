package com.gmail.elnora.fet.finalcourseproject;

import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;

public interface RecipeListeners {
    void onDishTypeClick(DishTypeEnum dishType);
    void onRecipeClick(RecipeDataModel recipeDataModel);
    void onFabTodoCookClick(int recipeId);
    void onFabAddTodoListClick();
    void onTodoItemClick(int recipeId);
}
