package com.gmail.elnora.fet.finalcourseproject.repo.repodb;

import androidx.lifecycle.LiveData;

import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;

import java.util.List;

public interface TodoRecipeRepository {
    LiveData<List<TodoRecipeEntity>> getAllRecipes();
    void insert(TodoRecipeEntity recipe);
    void delete(TodoRecipeEntity recipe);
}
