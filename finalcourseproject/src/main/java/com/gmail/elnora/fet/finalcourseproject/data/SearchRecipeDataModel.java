package com.gmail.elnora.fet.finalcourseproject.data;

public class SearchRecipeDataModel {
    private int id;
    private String title;
    private String urlToImage;
    private String sourceUrl;
    private String summary;
    private String dishTypes;

    public SearchRecipeDataModel(int id, String title, String urlToImage, String sourceUrl, String summary, String dishTypes) {
        this.id = id;
        this.title = title;
        this.urlToImage = urlToImage;
        this.sourceUrl = sourceUrl;
        this.summary = summary;
        this.dishTypes = dishTypes;
    }

    /*public SearchRecipeDataModel(int id, String title, String urlToImage, String summary) {
        this.id = id;
        this.title = title;
        this.urlToImage = urlToImage;
        this.sourceUrl = summary;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(String dishTypes) {
        this.dishTypes = dishTypes;
    }
}
