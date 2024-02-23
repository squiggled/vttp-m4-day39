package com.example.day38ws.models;

import org.bson.Document;

public class StompPost {
    private String user;
    private String description;
    private String url;
    private byte[] image;
    private String id;
    
    public StompPost() {
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public static Document toDocument(StompPost post) {
        Document uploadDocument = new Document();
        uploadDocument.append("_id", post.getId());
        uploadDocument.append("user", post.getUser());
        uploadDocument.append("description", post.getDescription());
        uploadDocument.append("url", post.getUrl());
        return uploadDocument;
    }

    
}
