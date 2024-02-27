package com.example.day39pdfws.models;

import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String gifId;
    private String comment;
    private Date timestamp;
  
    public Comment() {
    }
    
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
   
    public static Comment documentToComment(Document d){
        Comment c = new Comment();
        String gifid = d.getString("gifId");
        String comment = d.getString("comment");
        Date time = d.getDate("timestamp");
        c.setGifId(gifid);
        c.setComment(comment);
        c.setTimestamp(time);
        return c;
    }

    public static Document commentToDocument(Comment c){
        Document d = new Document();
        d.append("gifId", c.getGifId())
            .append("comment", c.getComment())
            .append("timestamp", c.getTimestamp());
        return d;
    }
  
    public String getGifId() {
        return gifId;
    }
    public void setGifId(String gifId) {
        this.gifId = gifId;
    }

    
public JsonObject toJson(){
	return Json.createObjectBuilder()
	.add("gifId", getGifId())
	.add("comment", getComment())
	.add("timestamp", getTimestamp().toString())
	.build();
}
public Comment(String gifId, String comment, Date timestamp) {
	this.gifId = gifId;
	this.comment = comment;
	this.timestamp = timestamp;
}

@Override
public String toString() {
	JsonObject json = this.toJson();
    return json.toString();
}
    
}
