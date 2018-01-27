package com.demo.com.demo.dto;

import java.util.List;
import java.util.Map;

public class SearchQueryDTO {
   /* private String content;


   *//* private List<String> alColor;
    private  List<String>alSize;
    private List<String> alBrand;
   *//*
    public void setColor(String color) {
        this.color = color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSize(String size) {
        this.size = size;
    }
    private String color;

    private String brand;
    private String size;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

   */ public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String productType;

    public SearchQueryDTO() {
    }

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }

    private Map<String,List<String>> attributes;

   }
