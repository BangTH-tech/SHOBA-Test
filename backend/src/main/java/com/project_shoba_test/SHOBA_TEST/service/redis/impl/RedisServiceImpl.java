package com.project_shoba_test.SHOBA_TEST.service.redis.impl;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;
import com.project_shoba_test.SHOBA_TEST.service.redis.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Product getProduct(Long productId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object json = redisTemplate.opsForValue().get(String.valueOf(productId));
            return json != null ? objectMapper.readValue(json.toString(), Product.class) : null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Async("asyncTaskExecutor")
    @Override
    public void saveProduct(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(product);
            redisTemplate.opsForValue().set(
                    String.valueOf(product.getId()), json,
                    Duration.ofDays(1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
