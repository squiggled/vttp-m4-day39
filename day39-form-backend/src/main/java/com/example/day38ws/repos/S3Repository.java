package com.example.day38ws.repos;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Repository
public class S3Repository {

    @Autowired
    private AmazonS3 s3;

    private String BUCKET_NAME = "squiggled-csf";

    public String saveToS3Stomp(InputStream is, String mediaType, long size, String id) {
        ObjectMetadata metadata = new ObjectMetadata();
        Map<String, String> data = new HashMap<>();
        data.put("mongoRef", id);
        metadata.setContentType(mediaType);
        metadata.setContentLength(size);
        metadata.setUserMetadata(data);

        PutObjectRequest putReq = new PutObjectRequest(
            BUCKET_NAME,
            id,
            is, metadata);
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead); 

            PutObjectResult result = s3.putObject(putReq);
            String urlToReturn = s3.getUrl(BUCKET_NAME, id).toExternalForm();
            System.out.println("url to return in SB "+ urlToReturn);
            return urlToReturn;

    }

    public String saveToS3(InputStream is, String mediaType, long size, String id) {
        ObjectMetadata metadata = new ObjectMetadata();
        Map<String, String> data = new HashMap<>();
        data.put("mongoRef", id);
        metadata.setContentType(mediaType);
        metadata.setContentLength(size);
        metadata.setUserMetadata(data);

        PutObjectRequest putReq = new PutObjectRequest(
            BUCKET_NAME, 
            id, 
            is, metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead); //sets the access level to public read

        //upload to s3 bucket
        PutObjectResult result = s3.putObject(putReq); //putobjectresult is s3's response which contains info about the operation outcome
        System.out.println("putobjectresult in s3 repo " + result);
        //.getUrl method returns the url of the image
        //to external form converts the url to a string
        String urlToReturn= s3.getUrl(BUCKET_NAME, id).toExternalForm();
        System.out.println("url to return from s3 repo: " + urlToReturn);
        return urlToReturn;
    } 

    //retrieval 
    public void getFromS3(String key) {

      GetObjectRequest getReq = new GetObjectRequest(BUCKET_NAME, key);

      S3Object obj = s3.getObject(getReq);
      S3ObjectInputStream is = obj.getObjectContent();

   }

    
}
