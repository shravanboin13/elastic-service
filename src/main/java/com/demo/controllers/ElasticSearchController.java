package com.demo.controllers;

import com.demo.com.demo.dto.SearchQueryDTO;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import com.demo.service.ProductService;
import com.demo.utils.CommonUtils;
import org.elasticsearch.search.aggregations.Aggregation;
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
    @Autowired
    CommonUtils commonUtils;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveCustomElements(@RequestBody Product product) throws Exception {

        Product product1 = productService.save(product);
        return new ResponseEntity<>(product1, HttpStatus.OK);

    }
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateCustomElements(@RequestBody Product product) throws Exception {

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
     @RequestMapping(method = RequestMethod.POST, value = "/upload",produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveProductsBulkFromExcel() throws Exception {

        productService.saveProducts();

    }
    @RequestMapping(method = RequestMethod.GET, value = "/productTypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getProductTypes() throws Exception {

      return  productService.getAllProductTypes();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/partial/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO getItemSearch(@RequestParam Map queryMap) throws Exception {
        SearchQueryDTO searchDTO= commonUtils.createSearchQueryDTO(queryMap);
        ProductDTO productDTO  = productService.findByCriteria(queryMap);
        return productDTO;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/partial",produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO doPartialSearch(@RequestBody SearchQueryDTO searchQueryDTO) throws Exception {
       // SearchQueryDTO searchDTO= commonUtils.createSearchQueryDTO(queryMap);
        ProductDTO productDTO  = productService.findByCriteriaWithList(searchQueryDTO);
        return productDTO;
    }



}

