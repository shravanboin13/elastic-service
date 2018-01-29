package com.demo.service;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.dao.ProductRepository;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import com.demo.utils.ReadWriteUsingPOI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final String filePath = "D:\\shravan\\files\\checkin.xlsx";

    @Override
    public ProductDTO findByCriteria(Map searchDTO) {
      //  List<Product> searchResponse = productRepository.findByCategory(searchDTO.getContent());
         return productRepository.getAllProductsByCriteria(searchDTO);
       }

    @Override
    public ProductDTO findByCriteriaWithList(SearchQueryDTO searchQueryDTO) {
        return productRepository.getAllProductsByCriteria(searchQueryDTO);

    }

    @Override
    public List<String> getAllProductTypes() {
        return productRepository.getAllProductTypes();
    }

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ReadWriteUsingPOI readWriteUsingPOI;
    @Override
    public Product save(Product produt) {
       return  productRepository.saveProduct(produt);
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
        //return productRepository.findByName(name,pageRequest);
        return null;
    }


    @Override
    public void saveProducts() throws Exception{
            List<Product> alProducts = readWriteUsingPOI.readExcel(filePath);
            for(Product product :alProducts){
                productRepository.save(product);

            }
           }


}
