package com.gmail.elnora.fet.finalcourseproject.data;

public class IngredientDataModel {
    private int recipeId;
    private String name;
    private String image;
    private String amount;

    public IngredientDataModel(String name, String image, String amount) {
        this.name = name;
        this.image = image;
        this.amount = amount;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
