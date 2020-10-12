package com.gmail.elnora.fet.finalcourseproject.data;

public class SearchRecipeDataModel {
    private int id;
    private String title;
    private String urlToImage;
    private String sourceUrl;

    public SearchRecipeDataModel(int id, String title, String urlToImage, String summary) {
        this.id = id;
        this.title = title;
        this.urlToImage = urlToImage;
        this.sourceUrl = summary;
    }

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

}
