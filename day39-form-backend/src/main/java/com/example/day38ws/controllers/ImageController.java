package com.example.day38ws.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.day38ws.models.StompPost;
import com.example.day38ws.models.Upload;
import com.example.day38ws.repos.MongoRepository;
import com.example.day38ws.repos.RedisRepository;
import com.example.day38ws.repos.S3Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin
public class ImageController {
    @Autowired
    S3Repository s3Repo;
    @Autowired
    MongoRepository mongoRepo;
    @Autowired
    RedisRepository redisRepo;

    @PostMapping(path="/stomp", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> uploadStomp(@RequestPart MultipartFile file, @RequestPart String user, @RequestPart String description,
    RedirectAttributes redirectAttributes, Model model){
        //variables in parameter must match the names set by formdata in the FE
        //all types must be string cos data is passed as json
        System.out.println("got to controller in backend");
        try {
            System.out.println("user: " + user);
            System.out.println("file: " + file);
            InputStream is = file.getInputStream();

            String id = UUID.randomUUID().toString().substring(0, 8);
            String mediaType = file.getContentType();

            StompPost post = new StompPost();
            post.setUser(user);
            post.setDescription(description);
            post.setImage(file.getBytes());
            post.setId(id);
            
            //order of params: inputstream, mediatype, filesize, id
            String uploadUrl = s3Repo.saveToS3Stomp(is, mediaType, file.getSize(), id);
            System.out.println("uploadURL in controller "+ uploadUrl);

            post.setUrl(uploadUrl); //update URL
            //Then save to mongo
            Document uploadDoc =mongoRepo.saveToMongoStomp(post);
            System.out.println("document in controller saved to mongo: "+uploadDoc);
            //save to redis
            // redisRepo.saveToRedis(id);

            // model.addAttribute("post", post);
            Map<String, String> response = new HashMap<>();
            response.put("url", uploadUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e){
            e.printStackTrace();
            JsonObject obj = Json.createObjectBuilder().add("BAD REQUEST", "BAD").build();
            return ResponseEntity.badRequest().body(obj);
        }
        
    }

        //to make article,
        // make the url part of the article model
        //make the save to s3 return the article url, then patch the model's url field in the ocntroller, then redirect
        //s3 has a get url utility. give it the bucket name and key, ad it'll return u a url 
        //chuck's returns just localhost:8080/article
        //add thumbs up / down for redis
   
}
