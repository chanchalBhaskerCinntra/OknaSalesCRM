package com.cinntra.okana.model;

public class ImageModel {

    String title;
    String path;

    public ImageModel(String name, String path) {
        this.title = name;
        this.path = path;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
