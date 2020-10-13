package com.gmail.elnora.fet.finalcourseproject.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class TodoRecipeEntity {
    @PrimaryKey
    private int recipeId;
    private String title;
    private String urlToImage;
    private String summary;
    private String dishTypes;

    public TodoRecipeEntity(int recipeId, String title, String urlToImage, String summary, String dishTypes) {
        this.recipeId = recipeId;
        this.title = title;
        this.urlToImage = urlToImage;
        this.summary = summary;
        this.dishTypes = dishTypes;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(String dishTypes) {
        this.dishTypes = dishTypes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
