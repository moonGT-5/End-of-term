package org.example.backend.Service;

import org.example.backend.entity.Item;
import org.example.backend.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;
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
}
