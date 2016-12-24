package com.partymaker.mvc.model.business;

/**
 * Created by anton on 30/11/16.
 */
public class ImageMessage {

    private String path;

    public ImageMessage() {
    }

    public ImageMessage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
