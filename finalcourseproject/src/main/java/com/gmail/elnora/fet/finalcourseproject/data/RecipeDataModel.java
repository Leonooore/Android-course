package com.gmail.elnora.fet.finalcourseproject.data;

public class RecipeDataModel {
    private int id;
    private String dishTypes;
    private String title;
    private String urlToImage;
    private String summary;

    public RecipeDataModel(int id, String dishTypes, String title, String urlToImage, String summary) {
        this.id = id;
        this.dishTypes = dishTypes;
        this.title = title;
        this.urlToImage = urlToImage;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(String dishTypes) {
        this.dishTypes = dishTypes;
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

}
