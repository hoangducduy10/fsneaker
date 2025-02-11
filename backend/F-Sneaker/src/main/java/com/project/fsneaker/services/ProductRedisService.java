package com.project.fsneaker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fsneaker.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductRedisService implements IProductRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getKeyFrom(String keyword, Long categoryId, PageRequest pageRequest){
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        Sort.Order order = sort.getOrderFor("id");
        String sortDirection = (order != null && order.getDirection() == Sort.Direction.ASC) ? "ASC" : "DESC";

        return String.format("all_products:%d:%d:%s", pageNumber, pageSize, sortDirection);
    }

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys("all_products:*");

        // Kiểm tra nếu keys != null thì mới xóa
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }


    @Override
    public List<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) throws JsonProcessingException {
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ? objectMapper.readValue(json, new TypeReference<List<ProductResponse>>() {}) : null;
    }

    @Override
    public void saveAllProducts(List<ProductResponse> productResponses, String keyword, Long categoryId, PageRequest pageRequest) throws JsonProcessingException {
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = objectMapper.writeValueAsString(productResponses);
        redisTemplate.opsForValue().set(key, json);
    }

}
