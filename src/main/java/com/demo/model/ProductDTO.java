package com.demo.model;

import java.util.List;
import java.util.Map;

public class ProductDTO {
    public ProductDTO() {
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    List<Product> products;

    public Map<String, Map<String, Long>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Map<String, Long>> attributes) {
        this.attributes = attributes;
    }

    Map<String,Map<String,Long>> attributes;

}
