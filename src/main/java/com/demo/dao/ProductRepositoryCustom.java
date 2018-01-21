package com.demo.dao;

import com.demo.model.Product;
import org.elasticsearch.search.aggregations.Aggregation;

import java.util.List;

public interface ProductRepositoryCustom {
    public List<Product> getAllMatchedProducts(String content);

}
