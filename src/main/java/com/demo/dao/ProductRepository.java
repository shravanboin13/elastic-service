package com.demo.dao;

import com.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



public interface ProductRepository
        extends ElasticsearchRepository<Product,String>,ProductRepositoryCustom{
    Page<Product> findByName(String name, Pageable page);
    Page<Product> findByDescription(String description, Pageable page);
}
