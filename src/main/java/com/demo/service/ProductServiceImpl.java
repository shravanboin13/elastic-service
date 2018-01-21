package com.demo.service;

import com.demo.dao.ProductRepository;
import com.demo.model.Product;
import com.demo.utils.ReadWriteUsingPOI;
import org.elasticsearch.search.aggregations.Aggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final String filePath = "D:\\shravan\\files\\checkin.xlsx";
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ReadWriteUsingPOI readWriteUsingPOI;
    @Override
    public Product save(Product produt) {
       return  productRepository.save(produt);
       }

    @Override
    public Product findOne(String id) {
        return productRepository.findOne(id);
        }

    @Override
    public void delete(String id) {
      //  productRepository.delete(id);
        productRepository.deleteAll();
    }

    @Override
    public List<Product> findAll() {
       return (List<Product>) StreamSupport.stream(productRepository.findAll().spliterator(), false)
               .collect(Collectors.toList());
        }

    @Override
    public Page<Product> findByName(String name, PageRequest pageRequest) {
        return productRepository.findByName(name,pageRequest);
    }

    @Override
    public Page<Product> findByDescription(String description, PageRequest pageRequest) {
        return productRepository.findByDescription(description,pageRequest);
    }

    @Override
    public List<Product> findByContent(String content) {
       return productRepository.getAllMatchedProducts(content);

       // return null;
    }
    @Override
    public void saveProducts() throws Exception{
            List<Product> alProducts = readWriteUsingPOI.readExcel(filePath);
            for(Product product :alProducts){
                productRepository.save(product);

            }
           }


}
