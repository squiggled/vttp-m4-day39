package com.example.day38ws.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Upload {
    private String title;
    private String description;
    private String id;
    private String url;
    private Long likes = 0l;
    private Long dislikes=0l;
    
    public Upload() {
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    //POJO to json
    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("title", title)
            .add("description", description)
            .add("url", url)
            .add("likes", likes)
            .add("dislikes", dislikes)
            .build();
    }

    //document to JSON
     public static Upload create(Document d){
        
        Upload upload = new Upload();
        upload.setId(d.getString("_id"));
        upload.setTitle(d.getString("title"));
        upload.setDescription(d.getString("description"));
        
        upload.setUrl(d.getString("url"));
        upload.setLikes(d.getLong("likes"));
        upload.setDislikes(d.getLong("dislikes"));
        return upload;
    }

    //POJO to document
    public static Document toDocument(Upload upload) {
        Document uploadDocument = new Document();
        uploadDocument.append("_id", upload.getId());
        uploadDocument.append("title", upload.getTitle());
        uploadDocument.append("description", upload.getDescription());
        uploadDocument.append("url", upload.getUrl());
        uploadDocument.append("likes", upload.getLikes());
        uploadDocument.append("dislikes", upload.getDislikes());
        return uploadDocument;
    }

    @Override
    public String toString() {
        return "Upload [title=" + title + ", description=" + description + ", id=" + id + "]";
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Long getLikes() {
        return likes;
    }
    public void setLikes(Long likes) {
        this.likes = likes;
    }
    public Long getDislikes() {
        return dislikes;
    }
    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }


}
