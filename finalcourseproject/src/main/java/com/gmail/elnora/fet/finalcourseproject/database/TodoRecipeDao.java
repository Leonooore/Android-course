package com.gmail.elnora.fet.finalcourseproject.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoRecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TodoRecipeEntity recipe);

    @Delete
    void delete(TodoRecipeEntity recipe);

    @Query("SELECT * FROM recipe_table")
    LiveData<List<TodoRecipeEntity>> getRecipes();

}
