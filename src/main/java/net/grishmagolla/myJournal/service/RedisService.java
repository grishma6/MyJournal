package net.grishmagolla.myJournal.service;

import lombok.extern.slf4j.Slf4j;
import net.grishmagolla.myJournal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        }catch(Exception e){
            log.error("Exception: ", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl ){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, o.toString(), ttl, TimeUnit.SECONDS);
        }catch(Exception e){
            log.error("Exception: ", e);
        }
    }

}
