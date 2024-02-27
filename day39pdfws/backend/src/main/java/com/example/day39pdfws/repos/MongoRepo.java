package com.example.day39pdfws.repos;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.example.day39pdfws.models.Comment;

@Repository
public class MongoRepo {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Comment> getComments(String id){
        Query q = new Query();
        q.addCriteria(Criteria.where("gifId").is(id));
        q.with(Sort.by(Sort.Direction.DESC, "timestamp"));
        q.limit(10);
       List<Document> result = mongoTemplate.find(q, Document.class, "comments");
       if (null==result) return null;
       List<Comment> toReturn = new ArrayList<>();
       for (Document doc : result){
        Comment comment = Comment.documentToComment(doc);
        toReturn.add(comment);
       }
       System.out.println("toreturn in repo " + toReturn);
       return toReturn;
    }

    public List<Comment> postComment(Comment comment){
        // Query query = new Query(Criteria.where("gifId").is(comment.getGifId()));
        Document d = Comment.commentToDocument(comment);
        mongoTemplate.insert(d, "comments");
        return getComments(comment.getGifId());

    }
}
