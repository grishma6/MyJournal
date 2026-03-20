package net.grishmagolla.myJournal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSendEmail(){
        redisTemplate.opsForValue().set("email", "grishmagolla@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a = 1;

    }
}
