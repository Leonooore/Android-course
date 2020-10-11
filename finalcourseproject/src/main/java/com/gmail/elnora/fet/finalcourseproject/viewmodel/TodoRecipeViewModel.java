package com.gmail.elnora.fet.finalcourseproject.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;
import com.gmail.elnora.fet.finalcourseproject.repo.repodb.TodoRecipeRepositoryImpl;

import java.util.List;

public class TodoRecipeViewModel {

    private TodoRecipeRepositoryImpl recipeRepository;
    private LiveData<List<TodoRecipeEntity>> recipes;

    public TodoRecipeViewModel(@NonNull Context context) {
        recipeRepository = new TodoRecipeRepositoryImpl(context);
        recipes = recipeRepository.getAllRecipes();
    }

    public LiveData<List<TodoRecipeEntity>> getAllRecipes() {
        return recipes;
    }

    public void insert(TodoRecipeEntity recipe) {
        recipeRepository.insert(recipe);
    }

    public void delete(TodoRecipeEntity recipe) {
        recipeRepository.delete(recipe);
    }

}
