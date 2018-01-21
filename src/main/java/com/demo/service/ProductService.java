package com.demo.service;

import com.demo.model.Product;
import org.elasticsearch.search.aggregations.Aggregation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {
    Product save(Product produt);
    Product findOne(String id);
    void delete(String id);

    List<Product> findAll();
    Page<Product> findByName(String name, PageRequest pageRequest);
    Page<Product> findByDescription(String description, PageRequest pageRequest);

    List<Product> findByContent(String content);
    void saveProducts() throws Exception;
}
