package com.demo.model;

import java.util.List;
import java.util.Map;

public class ProductDTO {
    public ProductDTO(List<Product> result, Map<String, List<String>> attributes) {
        this.result = result;
        this.attributes = attributes;
    }

    public ProductDTO() {
    }

    public List<Product> getResult() {
        return result;
    }

    public void setResult(List<Product> result) {
        this.result = result;
    }

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }

    List<Product> result;
    Map<String,List<String>> attributes;

}
