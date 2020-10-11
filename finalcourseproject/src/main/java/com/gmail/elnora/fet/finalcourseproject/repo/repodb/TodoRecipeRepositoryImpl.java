package com.gmail.elnora.fet.finalcourseproject.repo.repodb;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeDao;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeDatabase;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;

import java.util.List;

public class TodoRecipeRepositoryImpl implements TodoRecipeRepository {

    private TodoRecipeDao recipeDao;
    private LiveData<List<TodoRecipeEntity>> recipeList;

    public TodoRecipeRepositoryImpl(Context context) {
        TodoRecipeDatabase db = TodoRecipeDatabase.getDatabase(context);
        recipeDao = db.getRecipeDao();
        recipeList = recipeDao.getRecipes();
    }

    @Override
    public LiveData<List<TodoRecipeEntity>> getAllRecipes() {
        return recipeList;
    }

    @Override
    public void insert(TodoRecipeEntity recipe) {
        TodoRecipeDatabase.databaseExecutor.execute(() -> recipeDao.insert(recipe));
    }

    @Override
    public void delete(TodoRecipeEntity recipe) {
        TodoRecipeDatabase.databaseExecutor.execute(() -> recipeDao.delete(recipe));
    }

}
