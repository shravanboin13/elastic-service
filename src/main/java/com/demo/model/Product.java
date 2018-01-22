package com.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;

@Document(indexName="products",type="products")
public class Product {
    @Id
    private String id;
    @Field(type = FieldType.String)
    private String name;
    private String description;
    //private String place;
    private String category;
    private String subCategory;
    private double price;
    private String code;
    private String brand;
   /* @Field(type=FieldType.Object, index= FieldIndex.not_analyzed)
    Map<String,String> attributes;
*/


    public Product() {

    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Product(String id, String name, String description, String category, String subCategory, float price, String code, String brand, String size, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.code = code;
        this.brand = brand;
        this.size = size;
        this.color = color;
    }

    private String size;
   private String color;
}
