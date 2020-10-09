package com.gmail.elnora.fet.finalcourseproject.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_recipe_table")
public class TodoRecipeEntity {
    @PrimaryKey
    @NonNull
    private int recipeId;
    private String title;
    private String urlToImage;
    private String summary;
    private String ingredients;
    private String step;
    private String timeLength;

    public TodoRecipeEntity(int recipeId, String title, String urlToImage, String summary, String ingredients, String step, String timeLength) {
        this.recipeId = recipeId;
        this.title = title;
        this.urlToImage = urlToImage;
        this.summary = summary;
        this.ingredients = ingredients;
        this.step = step;
        this.timeLength = timeLength;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }
}
