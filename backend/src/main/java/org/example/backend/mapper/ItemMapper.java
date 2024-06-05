package org.example.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.Item;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> findAllItems();

    Item findItemById(int id);

    Item findItemByName(String name);

    int deleteItemById(int id);

    void insertItem(Item item);
}
