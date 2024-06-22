package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.entity.MongoItem;
import org.example.backend.mapper.MongoItemRepository;
import org.example.backend.service.ItemService;
import org.example.backend.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private MongoItemRepository mongoItemRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> findAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/{id:[0-9]+}")
    public ResponseEntity<Item> findItemById(@PathVariable int id) {
        Item item = itemService.findItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/items/{name:[A-Za-z]+|[\\u4e00-\\u9fa5]+}")
    public ResponseEntity<Item> findItemByName(@PathVariable String name) {
        Item item = itemService.findItemByName(name);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/items/{id:[0-9]+}")
    public ResponseEntity<Void> deleteItemById(@PathVariable int id) {
        boolean deleted = itemService.deleteItemById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/items/insert")
    public ResponseEntity<Item> insertItem(@RequestBody Item item) {
        Item insertItem = itemService.insertItem(item);
        return ResponseEntity.ok(insertItem);
    }

    @PostMapping("/items/update")
    public ResponseEntity<List<Item>> updateItems(@RequestBody List<Item> items) {
        List<Item> updatedItems = itemService.updateItemsById(items);
        if (!updatedItems.isEmpty()) {
            return ResponseEntity.ok(updatedItems);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

@GetMapping("/items/purchase")
public ResponseEntity<List<MongoItem>> purchase() {
    List<MongoItem> mongoItems = new ArrayList<>();
    Set<String> keys = redisTemplate.keys("mongoItem:*");
    if (keys != null && !keys.isEmpty()) {
        for (String key : keys) {
            try {
                String mongoItemJson = redisTemplate.opsForValue().get(key);
                MongoItem mongoItem = objectMapper.readValue(mongoItemJson, MongoItem.class);
                mongoItems.add(mongoItem);
            } catch (Exception e) {
                throw new RuntimeException("序列化出错", e);
            }
        }
    } else {
        mongoItems = mongoItemRepository.findAll();
    }
    return ResponseEntity.ok(mongoItems);
}

}

