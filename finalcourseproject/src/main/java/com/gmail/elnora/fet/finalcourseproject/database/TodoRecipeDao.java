package com.gmail.elnora.fet.finalcourseproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoRecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TodoRecipeEntity todoRecipeEntity);

    @Query("DELETE FROM todo_recipe_table")
    void deleteAll();

    @Query("SELECT * from todo_recipe_table")
    List<TodoRecipeEntity> getTodoRecipes();

}
