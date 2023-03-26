package com.nassafy.core.respository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisDAO {

    private final RedisTemplate<String, String> redisTemplate; // serialize, deserialize 방식으로 string 방식 선택

    /*
    * key: value 형태로 값 저장
    * expiration: 해당 값들의 유효기간 설정
    * timeUnit: expiration의 단위 설정
     */
    public void setString(String key, String value, Long expiration, TimeUnit timeUnit) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set(key, value, expiration, timeUnit);
    }

    /*
    * return: 해당 키에 저장된 value값 리턴, 해당 키가 존재하지 않을 경우 null 반환
     */
    public String getString(String key) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        return stringValueOperations.get(key);
    }

    /*
    * return: 해당 키에 저장된 value를 삭제 후 해당 값 반환, 해당 키가 존재하지 않으면 null반환
     */
    public String deleteString(String key) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        return stringValueOperations.getAndDelete(key);
    }
}
