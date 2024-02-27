package com.example.day39pdfws.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.day39pdfws.models.Comment;
import com.example.day39pdfws.models.SearchResult;
import com.example.day39pdfws.repos.MongoRepo;
import com.example.day39pdfws.repos.RedisRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class SearchService {

    RestTemplate template = new RestTemplate();
    @Autowired
    private RedisRepo redisRepo;
    @Autowired
    private MongoRepo mongoRepo;

    @Value("${giphy.key}")
    private String giphyKey;

    //cannot put the giphy key here. spring initialises this string before injecting the value from app_props, 
    //so if you put it here, it will be null
    //solution: initialise the URL in the method itself
    private static final String GIPHY_URL = "https://api.giphy.com/v1/gifs/search?api_key=";
    //sample api: https://api.giphy.com/v1/gifs/search?api_key=9PeqpmULdoNCZgK28gYLlJZUcTKBWDYf&q=mushroom

    public List<SearchResult> getGifs(String phrase, int offset){
        System.out.println("giphy key" + giphyKey);
        String url = GIPHY_URL + giphyKey + "&q="+phrase+"&limit=20&offset="+offset;
        System.out.println("url " + url);
        String response = template.getForObject(url, String.class);
        if (response==null){
            System.out.println("null");
            return null;
        }
        Reader reader = new StringReader(response);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject obj = jsonReader.readObject();
        JsonArray gifArray = obj.getJsonArray("data");
        List<SearchResult> results = new ArrayList<>();
        for(JsonObject gifObj : gifArray.getValuesAs(JsonObject.class)){
            // System.out.println(gifObj.getString("title"));
            SearchResult res = new SearchResult();
            res.setName(gifObj.getString("title"));
            res.setId(gifObj.getString("id"));
            // System.out.println("id " + res.getId());
            // System.out.println("name in service " + res.getName());
            JsonObject imageObj = gifObj.getJsonObject("images");
            JsonObject originalObj = imageObj.getJsonObject("original");
            res.setUrl(originalObj.getString("url", ""));
            // System.out.println("url in service " + res.getUrl());
            
            results.add(res);
        }
        redisRepo.storeSearch(results); //store to redis
        return results;
    }

    public SearchResult getFromRedis(String id) {
        SearchResult result = redisRepo.findGif(id);
        if (null==result){
            System.out.println("🔴 result with " + id + "NOT found in redis");
            //https://api.giphy.com/v1/gifs/QuUhCHPQ6fY3IpFqt3?api_key=9PeqpmULdoNCZgK28gYLlJZUcTKBWDYf
            String response = template.getForObject("https://api.giphy.com/v1/gifs/" + id + "?api_key="+giphyKey, String.class);
            if (null==response) return null;
            Reader reader = new StringReader(response);
            JsonReader jsonReader = Json.createReader(reader);
            JsonObject obj = jsonReader.readObject();
            JsonObject dataObj = obj.getJsonObject("data");
            SearchResult res = new SearchResult();
            res.setName(dataObj.getString("title"));
            res.setId(dataObj.getString("id"));
            // System.out.println("name in service " + res.getName());
            JsonObject imageObj = dataObj.getJsonObject("images");
            JsonObject originalObj = imageObj.getJsonObject("original");
            res.setUrl(originalObj.getString("url", ""));
            return res;
        }
        System.out.println("🟢 result with " + id + "found in redis");
        return result;

    }
    public List<Comment> getComments(String id){
        List<Comment> results = mongoRepo.getComments(id);
        System.out.println("Results in search service " + results);
        if (results.isEmpty()) return null;
        return results;
    }

    public List<Comment> addComment(Comment comment) {
        List<Comment> updatedList = mongoRepo.postComment(comment);
        if (updatedList.isEmpty()) return null;
        return updatedList;
    }
    
}
