package com.gmail.elnora.fet.finalcourseproject.data;

public class Recipe {
    private DishTypeEnum type;
    private int image;
    private String name;
    private String description;

    public DishTypeEnum getType() {
        return type;
    }

    public void setType(DishTypeEnum type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe(DishTypeEnum type, int image, String name, String description) {
        this.type = type;
        this.image = image;
        this.name = name;
        this.description = description;
    }
}
