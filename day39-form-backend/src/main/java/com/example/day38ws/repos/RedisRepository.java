package com.example.day38ws.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    
    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    public void saveToRedis(String id){
        HashOperations<String, Object, Object> hashOps = template.opsForHash();
        hashOps.put(id, "likes", 0);
        hashOps.put(id, "dislikes", 0);
    }

    public Long incrementLikes(String id){
        HashOperations<String, Object, Object> hashOps = template.opsForHash();
        return hashOps.increment(id, "likes", 1);
    }

    public Long incrementDislikes(String id){
        HashOperations<String, Object, Object> hashOps = template.opsForHash();
        return hashOps.increment(id, "dislikes", 1);
    }
}
