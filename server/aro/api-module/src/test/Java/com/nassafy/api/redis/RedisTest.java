package com.nassafy.api.redis;

import com.nassafy.core.respository.RedisDAO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisDAO redisDAO;

    @Test
    @DisplayName("redis get, set, delete 테스트")
    public void redisTest (){
        redisDAO.setString("test", "testtest!!", 1L, TimeUnit.HOURS);

        Assertions.assertThat(redisDAO.getString("test")).isEqualTo("testtest!!");
        Assertions.assertThat(redisDAO.deleteString("test")).isEqualTo("testtest!!");
        org.junit.jupiter.api.Assertions.assertNull(redisDAO.getString("test"));
        Assertions.assertThat(redisDAO.deleteString("test")).isEqualTo(null);
    }
}
