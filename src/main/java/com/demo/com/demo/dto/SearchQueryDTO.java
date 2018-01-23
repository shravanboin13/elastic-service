package com.demo.com.demo.dto;

import java.util.List;

public class SearchQueryDTO {
    private String content;


   /* private List<String> alColor;
    private  List<String>alSize;
    private List<String> alBrand;
   */
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

    public SearchQueryDTO() {
    }


   }
