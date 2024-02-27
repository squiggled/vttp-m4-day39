package com.example.day39pdfws.repos;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.day39pdfws.models.SearchResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class RedisRepo {
    @Autowired
    @Qualifier("myredis")
    // RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    RedisTemplate<String, Object> redisTemplate;

    public void storeSearch(List<SearchResult> results){
        for (SearchResult result : results){
            // JsonObject body = result.searchToJson();
            // redisTemplate.opsForValue().set(result.getId(), body, 60, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(result.getId(), result, 60, TimeUnit.MINUTES);
        }
    }

    public void storeGif(SearchResult result){
        // redisTemplate.opsForValue().set(result.getId(), result.searchToJson(), 60, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(result.getId(), result, 60, TimeUnit.MINUTES);
    }


    public SearchResult findGif(String id) {
        // Object result =  redisTemplate.opsForValue().get(id);
        // if (null==result){
        //     return null;
        // } 
        // JsonReader reader = Json.createReader(new StringReader((String) result));
		// JsonObject object = reader.readObject();
        // String objId = object.getString("id");
        // String objName = object.getString("name");
        // String objUrl = object.getString("url");
        // System.out.println("obj id" + objId);
        // return new SearchResult(objId, objName, objUrl);
        SearchResult result =  (SearchResult) redisTemplate.opsForValue().get(id);
        if (null==result){
            return null;
        } 
        return result;
    }
}
