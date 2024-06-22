package org.example.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "items")
public class MongoItem {
    @Id
    private String id; // MongoDB中通常使用String类型的ID
    private String name;
    private int number;
    private int price;
    private String image;
    private String info;
    private LocalDateTime modifiedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "MongoItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", info='" + info + '\'' +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
