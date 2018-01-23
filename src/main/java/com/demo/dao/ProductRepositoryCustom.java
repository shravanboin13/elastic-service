package com.demo.dao;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import org.elasticsearch.search.aggregations.Aggregation;

import java.util.List;

public interface ProductRepositoryCustom {
    public ProductDTO getAllMatchedProducts(String content);
    public ProductDTO getAllProductsByCriteria(SearchQueryDTO searchQueryDTO);


}
