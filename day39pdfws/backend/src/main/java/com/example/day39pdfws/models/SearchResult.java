package com.example.day39pdfws.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class SearchResult {
    private String id;
    private String name;
    private String url;
    
    public SearchResult() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public JsonObject searchToJson(){
        return Json.createObjectBuilder()
            .add("id", getId())
            .add("name", getName())
            .add("url", getUrl())
            .build();
}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public SearchResult(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
    
}
