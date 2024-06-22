package org.example.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.entity.Item;
import org.example.backend.entity.MongoItem;
import org.example.backend.mapper.ItemMapper;
import org.example.backend.mapper.MongoItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoItemRepository mongoItemRepository;

    public List<Item> findAllItems() {
        return itemMapper.findAllItems();
    }

    public Item findItemById(int id) {
        return itemMapper.findItemById(id);
    }

    public Item findItemByName(String name) {
        return itemMapper.findItemByName(name);
    }

    public boolean deleteItemById(int id) {
        int affectedRows = itemMapper.deleteItemById(id);
        return affectedRows > 0;
    }

    public Item insertItem(Item item) {
        itemMapper.insertItem(item);
        return item;
    }

    public List<Item> updateItemsById(List<Item> items) {
        items.forEach(item -> itemMapper.updateItemById(item));
        List<Item> updatedItems = items.stream()
                .map(item -> itemMapper.findItemById(item.getId()))
                .collect(Collectors.toList());

        updatedItems.forEach(item -> {
            MongoItem mongoItem = new MongoItem();
            BeanUtils.copyProperties(item, mongoItem);
            mongoItem.setNumber(1);
            mongoItem.setModifiedTime(LocalDateTime.now()); // 设置修改时间为当前时间
            mongoItemRepository.save(mongoItem);
        });
        List<String> jsonItems = updatedItems.stream()
                .map(item -> {
                    Item itemWithPurchaseQuantity = new Item();
                    itemWithPurchaseQuantity.setId(item.getId());
                    itemWithPurchaseQuantity.setName(item.getName());
                    itemWithPurchaseQuantity.setPrice(item.getPrice());
                    itemWithPurchaseQuantity.setImage(item.getImage());
                    itemWithPurchaseQuantity.setInfo(item.getInfo());
                    itemWithPurchaseQuantity.setNumber(1); // 假设购买数量为1
                    try {
                        return objectMapper.writeValueAsString(itemWithPurchaseQuantity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error serializing item with purchase quantity to JSON", e);
                    }
                })
                .collect(Collectors.toList());
        redisTemplate.opsForList().rightPushAll("updatedItems", jsonItems);
        return updatedItems;
    }

}
