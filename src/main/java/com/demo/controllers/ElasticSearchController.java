package com.demo.controllers;

import com.demo.model.Product;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="/products")
public class ElasticSearchController {
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveCustomElements(@RequestBody Product product) throws Exception {

        Product product1 = productService.save(product);
        return new ResponseEntity<>(product1, HttpStatus.OK);

    }
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getCustomElements() throws Exception {

        List<Product> product1 = productService.findAll();
        return product1;
    }
    @RequestMapping(method = RequestMethod.DELETE, value="/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") String id) throws Exception {
            productService.delete(id);
        }
    @RequestMapping(method = RequestMethod.GET, value = "/partial/{content}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getCustomElements(@PathVariable("content") String content) throws Exception {

        List<Product> product1 = productService.findByContent();
        return product1;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/upload",produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveProductsBulkFromExcel() throws Exception {

        productService.saveProducts();

    }
}

