package com.demo.dao;

import com.demo.model.Product;
import com.demo.model.ProductDTO;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface ProductRepository
        extends ElasticsearchRepository<Product,String>,ProductRepositoryCustom{
   // Page<Product> findByName(String name, Pageable page);
   /* @Query("\"query\": {\"match\" : {\"category\" : \"?0\"}}")
    List<Product> findByCategory(String category);
*/
}
