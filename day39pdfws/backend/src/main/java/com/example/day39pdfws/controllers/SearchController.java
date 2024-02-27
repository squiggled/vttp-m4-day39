package com.example.day39pdfws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.day39pdfws.models.Comment;
import com.example.day39pdfws.models.SearchResult;
import com.example.day39pdfws.services.SearchService;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SearchController {
    
    @Autowired
    private SearchService searchSvc;

    @GetMapping(path="/search")
    public ResponseEntity<?> getCharacters(@RequestParam String phrase, @RequestParam("offset") int offset ){
        System.out.println("phrase in SB controller: " + phrase);
        List<SearchResult> response = searchSvc.getGifs(phrase, offset);
        // System.out.println("response " + response);
        if (null==response){
            return ResponseEntity.badRequest().body("error");
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping(path="/searchredis/{id}")
    public ResponseEntity<?> getGif(@PathVariable String id){
        System.out.println("name in SB controller: " + id);
        SearchResult response = searchSvc.getFromRedis(id);
        // System.out.println("response " + response);
        if (null==response){
            return ResponseEntity.badRequest().body("Error - Not Found");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/searchmongo/{id}")
    public ResponseEntity<?> getComments(@PathVariable String id){
        System.out.println("id in SB mongo controller: " + id);
        List<Comment> results = searchSvc.getComments(id);
        // System.out.println("response " + response);
        if (null==results){
            System.out.println("no comment found in controller");
            return ResponseEntity.badRequest().body("No comments found");
        }
        // JsonArrayBuilder res = Json.createArrayBuilder();
        // for (Comment updComment : results){
        //     res.add(updComment.toJson());
        // }
        // res.build();
        System.out.println("returning in getComments in controller" + results);
        return ResponseEntity.ok(results);
    }

    @PostMapping(path="/addmongo")
    public ResponseEntity<?> addComment(@RequestBody Comment comment){
        List<Comment> updatedCommentList = searchSvc.addComment(comment);
        if (null==updatedCommentList){
            return ResponseEntity.badRequest().body("Error - Not Found");
        }
        JsonArrayBuilder res = Json.createArrayBuilder();
        for (Comment updComment : updatedCommentList){
            res.add(updComment.toJson());
        }
        //THIS IS WRONG
        // res.build();
        //return ResponseEntity.ok().body(res.toString());
        //becuase we nvr assign a variable to res.build(), and hence are calling toString on res(), the obj builder
        //we need to call toString() on the JsonArray object returned by build(), not the JsonArrayBuilder itself
        
        String jsonString = res.build().toString(); 
        return ResponseEntity.ok().body(jsonString);
    }
}
