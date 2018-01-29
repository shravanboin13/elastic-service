package com.demo.service;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product save(Product produt);
    Product findOne(String id);
    void delete(String id);

    List<Product> findAll();
    Page<Product> findByName(String name, PageRequest pageRequest);

    void saveProducts() throws Exception;

    ProductDTO findByCriteria(Map searchDTO);

    ProductDTO findByCriteriaWithList(SearchQueryDTO searchQueryDTO);

    List<String> getAllProductTypes();
}
