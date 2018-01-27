package com.demo.dao;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.model.Product;
import com.demo.model.ProductDTO;

import java.util.Map;

public interface ProductRepositoryCustom {

    public ProductDTO getAllProductsByCriteria(Map searchQueryDTO);
    ProductDTO getAllProductsByCriteria(SearchQueryDTO searchQueryDTO);
    Product saveProduct(Product product);
  }


