package com.gmail.elnora.fet.finalcourseproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TodoRecipeEntity.class}, version = 1, exportSchema = false)
public abstract class TodoRecipeDatabase extends RoomDatabase {

    public abstract TodoRecipeDao getRecipeDao();

    private static volatile TodoRecipeDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodoRecipeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRecipeDatabase.class, "recipe_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
