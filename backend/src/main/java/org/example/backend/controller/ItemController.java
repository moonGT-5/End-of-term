package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.service.ItemService;
import org.example.backend.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItemService itemService;

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
    public ResponseEntity<List<Item>> purchase() {
        List<Object> purchaseObjects = redisTemplate.opsForList().range("updatedItems", 0, -1);
        List<Item> purchases = new ArrayList<>();
        for (Object outerObj : purchaseObjects) {
            if (outerObj instanceof ArrayList) {
                @SuppressWarnings("unchecked")
                ArrayList<Object> innerList = (ArrayList<Object>) outerObj;
                for (Object innerObj : innerList) {
                    if (innerObj instanceof String) {
                        String json = (String) innerObj;
                        try {
                            Item item = objectMapper.readValue(json, Item.class);
                            purchases.add(item);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("内部列表类型出错: " + innerObj.getClass().getName());
                    }
                }
            } else {
                System.out.println("外部列表类型出错: " + outerObj.getClass().getName());
            }
        }
        return ResponseEntity.ok(purchases);
    }

}

