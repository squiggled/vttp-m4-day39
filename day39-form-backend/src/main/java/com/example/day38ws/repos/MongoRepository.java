package com.example.day38ws.repos;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.example.day38ws.models.StompPost;
import com.example.day38ws.models.Upload;

@Repository
public class MongoRepository {

    @Autowired
	private MongoTemplate template;

    /* 
     db.images.insert({
        title: title,
        description: text here
        id: id
     })
    */
    public Document saveToMongo(Upload upload) {
        Document uploadDoc = Upload.toDocument(upload);
        return template.save(uploadDoc, "day38images");
    }
    public Document saveToMongoStomp(StompPost post) {
        System.out.println("got to here in mongo repo");
        System.out.println("user object: " + post.getUser());
        Document uploadDoc = StompPost.toDocument(post);
        System.out.println("user doc: " + uploadDoc.getString("user"));
        return template.save(uploadDoc, "day38images");
    }

    
}
